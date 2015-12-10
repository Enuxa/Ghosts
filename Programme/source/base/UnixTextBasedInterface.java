package base;

import java.util.*;
import java.util.regex.Pattern;

import core.*;

public class UnixTextBasedInterface extends Interface {
	private boolean useColor;
	private Scanner sc;
	private String RED, BLUE, YELLOW, GREEN, PURPLE, DEFAULT;
	protected UnixTextBasedInterface (boolean useColor) {
		this.sc = new Scanner (System.in);
		this.RED = "\u001B[31m";
		this.BLUE = "\u001B[36m";
		this.YELLOW = "\u001B[33m";
		this.GREEN = "\u001B[32m";
		this.PURPLE = "\u001B[35m";
		this.DEFAULT = "\u001B[0m";
		this.useColor = useColor;
	}
	public UnixTextBasedInterface (){
		this (true);
	}
	
	@Override
	public String readText(String message) {
		System.out.print(message);
		return this.readText();
	}

	@Override
	public String readText() {
		String s = this.sc.nextLine();
		if (s == null || s.equals(""))
			return null;
		return s;
	}

	@Override
	public void updateDisplay(Player player) {
		Game game = Game.getCurrent();
		Collection<String> ghostTypes = game.getFactory().getTypes();
		int ghostTypesNumber = ghostTypes.size();
		Board board = game.getBoard();
		int size = board.getSize();
		for (int i = 0; i < size; i++) {
			System.out.print("| ");
			for (int j = 0; j < size; j++){
				Square s = board.getSquare(Board.toCoordinates(i, j));
				Ghost g = s.getGhost();
				this.displayGhost(ghostTypesNumber, game, g, player);
				
				System.out.print(" |");
				if (j < size - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
		
		//	Explications de la représentation des fantômes
		System.out.println("\nMinuscules : bon fantômes, majuscules : mauvais fantômes.");
		for (String type : ghostTypes)
			System.out.println(hashGhostType (type, ghostTypesNumber) + " : " + type);
	}

	/**
	 * Donne le caractère représentant un fantôme d'un type donné
	 * @param ghostType Le type du fantôme
	 * @param ghostTypesNumber Le nombre de type de fantômes.
	 * @return Le caractère représentant le fantôme.
	 */
	private char hashGhostType (String ghostType, int ghostTypesNumber){
		return (char)('a' + ghostType.hashCode() % ghostTypesNumber);
	}
	
	private char hashGhostType (Ghost ghost, int ghostTypesNumber){
		return hashGhostType (Game.getCurrent().getFactory().getType(ghost), ghostTypesNumber);
	}
	
	private void displayGhost (int ghostTypesNumber, Game game, Ghost ghost, Player player){
		if (ghost == null){
			System.out.print(" ");
			return;
		}
		
		String ghostType = Character.toString(this.hashGhostType(ghost, ghostTypesNumber));
		
		String colorCode = null;
		
		//	Vert minuscule : bon, rouge majuscule : mauvais
		if (!ghost.isGood()){
			ghostType = ghostType.toUpperCase();
			colorCode = this.RED;
		}else
			colorCode = this.GREEN;
		
		//	X majuscule jaune : joueur caché
		if (player != null && !player.hasGhost(ghost)){
			ghostType = "X";
			colorCode = this.YELLOW;
		}
		
		//	Si le on est en mode triche, [.] : joueur 1, (.) : joueur 2
		if (player == null){
			if (Game.getCurrent().getPlayer(0).hasGhost(ghost))
				ghostType = "[" + ghostType + "]";
			else
				ghostType = "(" + ghostType + ")";
		}
		String str =  ghostType;
		if (this.useColor)
			str = colorCode + str + this.DEFAULT;
		System.out.print(str);
	}
	@Override
	public void printText(String message) {
		System.out.println(message);
	}

	@Override
	public void printText(String message1, String message2) {
		this.printText(message1);
		if (message2 == null)
			message2 = "Appuyez sur Entrée pour continuer...";
		System.out.print(message2);
		this.sc.nextLine();
	}

	@Override
	public String readPosition(String message) {
		System.out.print(message);
		return this.readPosition();
	}

	@Override
	public String readPosition() {
		String s = this.readText();
		if (s == null)
			return null;
		if (Pattern.matches("^[a-zA-Z]\\d{1,}?$", s))
			return s.toUpperCase();
		return null;
	}

	@Override
	public Collection<String> readSelection(Collection<String> choice, String message, int min, int max) {
		this.printText(message);
		return this.readSelection(choice, min, max);
	}

	@Override
	public Collection<String> readSelection(Collection<String> c, int min, int max) {
		List<String> choice = new ArrayList<String> (c);
		for (int i = 0; i < choice.size(); i++)
			System.out.println((i+1) + " : " + choice.get(i));
		String msg = "";
		if (min == max && min != 1)
			msg = min + " réponses : ";
		if (min != max){
			if (max < min){
				int tmp = max;
				max = min;
				min = tmp;
			}
			msg = "Entre " + min + " et " + max + " réponses : ";	
		}
		String ans = this.readText(msg);
		if (ans == null)
			return null;
		String[] tab = ans.split(" ");
		Collection<String> answers = new ArrayList<String>();
		for (String s : tab){
			try{
				int n = Integer.parseInt(s);
				if (1 <= n && n <= choice.size() && answers.size() < max)
					answers.add(choice.get(n - 1));
			}catch (Exception e){}
		}
		
		if (answers.size() < min)
			return null;
		return answers;
	}

	@Override
	public String readSelection(Collection<String> choice, String message) {
		this.printText(message);
		return this.readSelection(choice);
	}

	@Override
	public String readSelection(Collection<String> choice) {
		Collection<String> c = this.readSelection(choice, 1, 1);

		if (c == null)
			return null;
		
		String a = null;
		for (String s : c)
			a = s;
		
		return a;
	}
}
