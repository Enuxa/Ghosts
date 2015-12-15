package core;
import java.util.*;

/**
 * Classe correspondant au plateau
 */
public class Board{
	private int size;
	private Map<String, Square> squares;
	private Collection<String> exits0, exits1;
	/**
	 * @param size Taille d'un côté du plateau
	 * @param exits0 Sortie pour le premier joueur.
	 * @param exits1 Sortie pour le second joueur.
	 */
	public Board (int size, Collection<String> exits0, Collection<String> exits1){
		this.size = size;
		this.exits0 = new ArrayList<String> ();
			for (String s : exits0)
				this.exits0.add(s.toUpperCase());
		this.exits1 = new ArrayList<String> ();
		for (String s : exits1)
			this.exits1.add(s.toUpperCase());
		this.squares = new HashMap <String, Square> ();
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				String coordinates = toCoordinates (i, j);
				this.squares.put (coordinates, new Square (coordinates));
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
		return (Game.getCurrent().getPlayer(0).hasGhost(ghost) && this.exits0.contains(position))
				|| (Game.getCurrent().getPlayer(1).hasGhost(ghost) && this.exits1.contains(position));
	}
	/**
	 * Fait sortir un fantôme
	 * @param ghost Le fantôme à faire sortir
	 */
	public void exit(Ghost ghost){
		for (int i = 0; i < 2; i++){
			Player p = Game.getCurrent().getPlayer(i);
			if (p.hasGhost(ghost)){
				p.exitGhost(ghost);
				String position = this.getPosition(ghost);
				this.getSquare(position).removeGhost();
				return;
			}
		}
	}
	/**
	 * Capture un fantôme
	 * @param ghost Le fantôme à capturer
	 */
	public void capture(Ghost ghost){
		for (int i = 0; i < 2; i++){
			Player p = Game.getCurrent().getPlayer(i);
			if (p.hasGhost(ghost)){
				p.captureGhost(ghost);
				String position = this.getPosition(ghost);
				this.getSquare(position).removeGhost();
				return;
			}
		}
	}
}
