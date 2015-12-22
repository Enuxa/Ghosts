package core;

public class Ghost {
	private boolean isGood;
	private Player player;
	/**
	*	@param	isGood	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon.
	*	@param player Le joueur auquel appartient le fantôme.
	*/
	public Ghost (boolean isGood, Player player){
		this.isGood = isGood;
		this.player = player;
	}
	/**
	*	Indique si ce fantôme est gentil ou non
	*	@return	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon
	*/
	public boolean isGood (){
		return this.isGood;
	}
	/**
	*	Déplace ce fantôme
	*	@param	coordinates	Nouvelle position du fantôme
	*	@throws	RuntimeException si la position n'appartient pas au plateau.
	*/
	public void move (String coordinates) {
		String position = Game.getCurrent().getBoard().getPosition(this);
		Board board = Game.getCurrent().getBoard();
		Square square0 = board.getSquare(position);
		if (square0 != null)
			square0.removeGhost();
		Square square1 = board.getSquare(coordinates);
		if (square1 != null)
			board.getSquare(coordinates).putGhost(this);
		else
			throw new RuntimeException ("La position " + coordinates + " n'est pas sur le plateau.");
	}
	/**
	*	Clone le fantôme (prévu pour cloner les modèles)
	* @param isGood <code>true</code> si le fantôme est gentil, <code>false</code> sinon.
	*	@param player Le joueur auquel appartient le fantôme.
	*	@return	Copie du fantôme
	*/
	public Ghost clone (boolean isGood, Player player){
		return new Ghost (isGood, player);
	}
	/**
	 * Récupère le joueur auquel appartient ce fantôme.
	 * @return Le joueur possédant le fantôme, <code>null</code> s'il nappartient à personne (s'il s'agit d'un modèlde de fantôme par exemple).
	 */
	public Player getPlayer (){
		return this.player;
	}
}
