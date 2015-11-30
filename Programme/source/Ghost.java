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
	*/
	public void move (String coordinates){
		String position = Game.getCurrent().getBoard().getPosition(this);
		Game.getCurrent().getBoard().getSquare(position).removeGhost();
		Game.getCurrent().getBoard().getSquare(coordinates).putGhost(this);
	}
	/**
	*	Clone le fantôme (prévu pour cloner les modèles)
	*	@return	Copie du fantôme
	*/
	public Ghost clone (boolean isGood){
		return new Ghost (this.isGood);
	}
}
