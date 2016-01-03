package core;

public class Ghost {
	private String type;
	private boolean isGood;
	private Player player;
	/**
	*	Contructeur <i>package-protected</i>
	*	@param	isGood	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon.
	*	@param type Le type de ce fantôme
	*	@param player Le joueur auquel appartient le fantôme.
	*/
	Ghost (boolean isGood, String type, Player player){
		this.isGood = isGood;
		this.player = player;
		this.type = type;
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
	 * Récupère le joueur auquel appartient ce fantôme.
	 * @return Le joueur possédant le fantôme, <code>null</code> s'il nappartient à personne (s'il s'agit d'un modèlde de fantôme par exemple).
	 */
	public Player getPlayer (){
		return this.player;
	}
	
	/**
	 * Récupère le type du fantôme
	 * @return Le type du fantôme
	 */
	public String getType (){
		return this.type;
	}
}
