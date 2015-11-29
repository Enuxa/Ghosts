import java.util.*;

/**
 * Classe correspondant au plateau
 */
public class Board{
	/**
	 * @param size Nombre de cases sur le côté.
	 */
	private int size;
	private Map<String, Square> squares;
	public Board (int size){
		this.size = size;
		this.squares = new HashMap <String, Square> ();
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				String coordinates = Character.toString ((char)('a' + i)) + (j + 1);
				this.squares.put (coordinates, new Square (coordinates));
			}
		}
	}
	/**
	 * Récupère une case
	 * @param coordinates Les coordonnées aphanumériques de la case souhaitée.
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
}
