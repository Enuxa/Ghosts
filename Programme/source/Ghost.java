public class Ghost {
	private boolean isGood;
	/**
	*	@param	isGood	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon.
	*/
	public Ghost (boolean isGood){
		this.isGood = isGood;
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
	public void move (String coordinates) throws RuntimeException{
		String position = Game.getCurrent().getBoard().getPosition(this);
		Board board = Game.getCurrent().getBoard();
		Square square0 = board.getSquare(position);
		if (square0 != null)
			square0.removeGhost();
		Square square1 = board.getSquare(coordinates);
		if (square1 != null)
			board.getSquare(coordinates).putGhost(this);
		else
			throw new RuntimeException ("La position " + coordinates + "n'est pas sur le plateau.");
	}
	/**
	*	Clone le fantôme (prévu pour cloner les modèles)
	*	@return	Copie du fantôme
	*/
	public Ghost clone (boolean isGood){
		return new Ghost (this.isGood);
	}
}
