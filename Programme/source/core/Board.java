package core;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe correspondant au plateau
 */
public class Board{
	private int size;
	private Map<String, Square> squares;
	/**
	 * @param size Nombre de cases sur le côté.
	 */
	public Board (int size){
		this.size = size;
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
	 * @param i L'abscisse.
	 * @param j L'ordonnée.
	 * @return Les coordonnées alphanumériques.
	 */
	public static String toCoordinates (int i, int j){
		return Character.toString ((char)('A' + i)) + (j + 1);
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
}
