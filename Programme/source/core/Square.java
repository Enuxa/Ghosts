package core;
/**
 *	Classe correspondant � une case.
 */
public class Square{
	private String coordinates;
	private Ghost ghost;
	private Player playerExit;
	/**
	 * @param coordinates Coordonn�es de cette case.
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
	 * R�cup�re le fant�me pr�sent sur cette case
	 * @return Le fant�me sur cette case, ou <code>null</code> s'il n'y a aucun fant�me sur cette case.
	 */
	public Ghost getGhost (){
		return this.ghost;
	}
	/**
	 * Place un fant�me sur cette case.
	 * @param ghost Le fant�me � placer.
	 * @throws RuntimeException si un fant�me se trouve d�j� sur cette case.
	 */
	public void putGhost (Ghost ghost) throws RuntimeException{
		if (this.ghost != null)
			throw new RuntimeException ("Un fant�me se trouve d�j�� la position " + this);
		this.ghost = ghost;
	}
	/**
	 * Retire le fant�me pr�sent sur cette case.
	 */
	public void removeGhost (){
		this.ghost = null;
	}
	/**
	 * R�cup�re les coordonn�es de cette case.
	 */
	public String toString (){
		return this.coordinates;
	}
	/**
	 * R�cup�re la coordonn�e alphab�tique de la position
	 * @return La coordonn�e alphab�tique
	 */
	public char getX () {
		return Board.toX(this.coordinates);
	}
	/**
	 * R�cup�re la coordonn�e num�rique de la position
	 * @return La coordonn�e num�rique
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