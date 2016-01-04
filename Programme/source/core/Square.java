package core;
/**
 *	Classe correspondant à une case.
 */
public class Square{
	private String coordinates;
	private Ghost ghost;
	private Player playerExit;
	/**
	 * @param coordinates Coordonnées de cette case.
	 */
	public Square (String coordinates){
		this.coordinates = coordinates;
		this.ghost = null;
		this.playerExit = null;
	}
	/**
	 * @param coordinates Coordonnées de cette case
	 * @param playerExit Le joueurs pouvant faire sortir ses fantômes sur cette case
	 */
	public Square (String coordinates, Player playerExit){
		this (coordinates);
		this.playerExit = playerExit;
	}
	/**
	 * Récupère le fantôme présent sur cette case
	 * @return Le fantôme sur cette case, ou <code>null</code> s'il n'y a aucun.
	 */
	public Ghost getGhost (){
		return this.ghost;
	}
	/**
	 * Place un fantôme sur cette case.
	 * @param ghost Le fantôme à placer.
	 */
	public void putGhost (Ghost ghost){
		if (this.ghost != null)
			throw new RuntimeException ("Un fantôme se trouve déjà à la position " + this);
		this.ghost = ghost;
	}
	/**
	 * Retire le fantôme présent sur cette case.
	 */
	public void removeGhost (){
		this.ghost = null;
	}
	/**
	 * Récupère les coordonnées de cette case.
	 */
	public String toString (){
		return this.coordinates;
	}
	/**
	 * Récupère la coordonnée alphabétique de la position
	 * @return La coordonnée alphabétique
	 */
	public char getX () {
		return Board.toX(this.coordinates);
	}
	/**
	 * Récupère la coordonnée numérique de la position
	 * @return La coordonnée numérique
	 */
	public int getY () {
		return Board.toY(this.coordinates);
	}
	/**
	 * Récupère le joueur pouvant sortir par cette case
	 * @return Le joueur pouvant sortir par ici
	 */
	public Player getPlayerExit (){
		return this.playerExit;
	}
}