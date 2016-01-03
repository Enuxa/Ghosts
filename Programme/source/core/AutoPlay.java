package core;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class AutoPlay {
	private Map<String, Boolean> initializationPattern;
	private Iterator<String[]> movements;
	private Player player;
	private File file;
	private Scanner scanner;
	private int filePlayerId;
	public AutoPlay (String filePath, Player player) throws Exception{
		this.player = player;
		this.filePlayerId = Game.getCurrent().getPlayer(0) == player ? 1 : 2;
		this.initializationPattern = new HashMap <String, Boolean> ();
		this.file = new File (filePath);
		this.scanner = new Scanner (file);
		
		this.readInit();
		this.readMovements();
	}
	/**
	 * Positionne après la ligne possédant un certain motif
	 * @param pattern Le motif recherché
	 * @return <code>true</code> si la ligne a été trouvée et qu'il y a une autre ligne après
	 */
	private boolean goTo (String pattern){
		boolean b = true;
		while (b){
			if (this.scanner.hasNextLine()){
				String line = this.scanner.nextLine();
				if (Pattern.matches(pattern, line))
					b = false;
			}else
				b = false;
		}
		
		return this.scanner.hasNextLine();
	}
	
	/**
	 * Initialise les tableau de placements
	 * @throws Exception si la lecture a rencontré un problème
	 */
	private void readInit () throws Exception {
		String startLine = "# Player" + this.filePlayerId;
		if (!this.goTo(startLine))
			throw new RuntimeException ("Le fichier " + this.file + " ne contient pas la ligne " + startLine);

		//	Lecture du fantôme le plus en haut à gauche
		String baseSquare = this.scanner.nextLine();
		int x = Board.toX(baseSquare) - 'A';
		int y = Board.toY(baseSquare) - 1;
		
		//	Lecture et ajout des lignes entre startLine et la prochaine balise
		int i = 0;
		boolean b = true;
		while (b){
			if (!this.scanner.hasNextLine())
				b = false;
			else{
				String line = this.scanner.nextLine();
				//	On arrête de lire si cette ligne est vide
				if (line.trim().isEmpty())
					b = false;
				//	Sinon on ajoute la nouvelle ligne
				else
					this.addInitalizationLine(line, x, y - i);
				i++;
			}
		}
	}

	/**
	 * Ajoute une ligne au tableau associatif
	 * @param line La ligne à ajouter
	 * @param y La coordonnée verticale du fantôme le plus à gauche de cette ligne
	 * @param x La coordonnée horizontale des fantômes de cette ligne
	 */
	private void addInitalizationLine (String line, int x, int y){
		String[] tab = line.split(" ");
		for (int j = 0; j < tab.length; j++)
			this.initializationPattern.put(Board.toCoordinates(j + x, y), tab[j].equals("G"));
	}
	
	/**
	 * Lis la liste de déplacements
	 * @throws Exception si la lecture rencontre un problème
	 */
	private void readMovements () throws Exception{
		String startLine = "# Move" + this.filePlayerId;
		List<String[]> movementList = new ArrayList<String[]> ();
		if (!this.goTo(startLine))
			throw new RuntimeException ("Le fichier " + this.file + " ne contient pas la ligne " + startLine);
		
		String[] tab = this.scanner.nextLine().split(" ");
		String pattern = "[A-Z]\\d";
		for (String s : tab){
			String t[] = s.split(",");
			if (t.length != 2 || !Pattern.matches(pattern, t[0])  || !Pattern.matches(pattern, t[1]))
				throw new RuntimeException ("Un déplacement non-conforme a été rencontré : " + t);
			movementList.add(t);
		}
		
		this.movements = movementList.iterator();
	}
	
	public void turn (){
		Game game = Game.getCurrent();
		Board board = game.getBoard();
		if (!this.movements.hasNext())
			throw new RuntimeException ("Le joueur automatique " + this.player + " ne possède pas de prochain mouvement.");
		else{
			String[] m = this.movements.next();
			if (game.getRuleBook().requestMovement(this.player, m[0], m[1])){
				Square squareB = board.getSquare(m[1]);
				if (squareB.getGhost() != null)
					board.capture(squareB.getGhost());
				board.getSquare(m[0]).getGhost().move(m[1]);
			}else
				throw new RuntimeException ("Le joueur automatique " + this.player + " ne peut pas déplacer son fanôme de " + m[0] + " en " + m[1]);
		}
	}
	
	public void initialize (){
		Game game = Game.getCurrent();
		RuleBook book = game.getRuleBook();
		Board board = game.getBoard();
		GhostFactory factory = game.getFactory();
		for (String position : this.initializationPattern.keySet()){
			Ghost ghost = factory.createGhost("Basique", this.initializationPattern.get(position), this.player);
			if (book.requestInitialization(this.player, ghost, position)){
				board.getSquare(position).putGhost(ghost);
				this.player.addGhost(ghost);
			}else{
				throw new RuntimeException ("Le joueur automatique " + this.player + " n'a pas pu poser son fantôme à la position " + position + " car le livre de règles ne l'y autorise pas.");
			}
		}
		
		if (!book.isReady(this.player))
			throw new RuntimeException ("Le joueur auto " + this.player + " n'a pas pu être initialisé entièrement.");
	}
}
