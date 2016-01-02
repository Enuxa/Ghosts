package core;
import java.util.*;

/**
 * Classe correspondant au plateau
 */
public class Board{
	private int size;
	private Map<String, Square> squares;
	private List<Collection<String>> exits;
	/**
	 * @param size Taille d'un côté du plateau
	 * @param exits0 Sortie pour le premier joueur.
	 * @param exits1 Sortie pour le second joueur.
	 */
	public Board (int size, Collection<String> exits0, Collection<String> exits1){
		this.size = size;
		this.exits = new ArrayList<Collection<String>> ();
		this.squares = new HashMap <String, Square> ();
		
		//	Initialisation des sorties
		for (int i = 0; i < 2; i++){
			Collection<String> e = i == 0 ? exits0 : exits1;
			Collection<String> c = new HashSet<String> ();
			for (String s : e)
				c.add(s.toUpperCase());		//	Mise en majuscules
			this.exits.add(c);
		}
		//	Initialisation des cases
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				String coordinates = toCoordinates (i, j);
				Player p = null;
				for (int k = 0; k < 2; k++)
					if (this.exits.get(k).contains(coordinates))
						p = Game.getCurrent().getPlayer(k);
				this.squares.put (coordinates, new Square (coordinates, p));
			}
		}
	}
	/**
	 * Transforme un couple d'entiers en coordonnées alphanumériques "informatiques" (c'est-à-dire en comptant à partir de 0).
	 * @param j La colonne.
	 * @param i La ligne.
	 * @return Les coordonnées alphanumériques.
	 */
	public static String toCoordinates (int j, int i){
		return Character.toString ((char)('A' + j)) + (i + 1);
	}
	/**
	 * Récupère une case
	 * @param coordinates Les coordonnées alphanumériques de la case souhaitée.
	 * @return La case demandée.
	 */
	public Square getSquare (String coordinates){
		return this.squares.get(coordinates);
	}
	/**
	 * Récupère le nombre de case sur le côté.
	 * @return La taille du côté du plateau.
	 */
	public int getSize (){
		return this.size;
	}
	/**
	 * Récupère la position d'un fantôme.
	 * @param ghost Le fantôme dont on veut la position.
	 * @return La position du fantôme passé en argument.
	 */
	public String getPosition (Ghost ghost){
		Collection<Square> squareCollection = this.squares.values();
		for (Square s : squareCollection)
			if (s.getGhost () == ghost)
				return s.toString();
		return null;
	}
	/**
	 * Récupère la coordonnée alphabétique d'une position
	 * @param position
	 * @return La coordonnée alphabétique
	 */
	public static char toX (String position) {
		return position.charAt(0);
	}
	/**
	 * Récupère la coordonnée numérique d'une position
	 * @param position
	 * @return La coordonnée numérique
	 */
	public static int toY (String position) {
		return Integer.parseInt(position.substring(1));
	}
	/**
	 * Indique si un fantôme peut sortir par une certaine position.
	 * @param position La position considérée
	 * @param ghost Le fantôme considéré
	 * @return <code>true</code> si cette position est celle d'une sortie, <code>false</code> sinon.
	 */
	public boolean canExit (String position, Ghost ghost){
		if (!ghost.isGood())
			return false;
		
		String coordinates = this.getPosition(ghost);
		Square square = this.getSquare(coordinates);
		
		//	Si un des joueurs possède ce fantôme ET si celui ci se trouve sur une case de sortie
		return ghost.getPlayer() != null && square.getPlayerExit() == ghost.getPlayer();
	}
	/**
	 * Fait sortir un fantôme
	 * @param ghost Le fantôme à faire sortir
	 */
	public void exit(Ghost ghost){
		Player p = ghost.getPlayer();
		if (p == null)
			throw new RuntimeException ("Le fantôme sortant en " + this.getPosition(ghost) + " n'appartient à aucun joueur.");
		p.exitGhost(ghost);
		this.removeGhost(ghost);
	}
	/**
	 * Capture un fantôme
	 * @param ghost Le fantôme à capturer
	 */
	public void capture(Ghost ghost){
		Player p = ghost.getPlayer();
		if (p == null)
			throw new RuntimeException ("Le fantôme supposé être capturé en " + this.getPosition(ghost) + " n'appartient à aucun joueur.");
		p.captureGhost(ghost);
		this.removeGhost(ghost);
	}
	/**
	 * Retire un fantôme du plateau
	 * @param ghost Le fantôme à retirer
	 */
	public void removeGhost (Ghost ghost){
		String position = this.getPosition(ghost);
		if (position == null)
			throw new RuntimeException ("Le fantôme " + ghost + " n'est sur aucune case !");
		this.getSquare(position).removeGhost();
	}
}
