
public abstract class MovementRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public MovementRule(int priority) {
		super(priority);
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Indique si un déplacement est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public abstract boolean requestMovement (Player player, String squareA, String squareB);
}
