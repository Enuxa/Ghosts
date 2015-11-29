public abstract class InitializationRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public InitializationRule(int priority) {
		super(priority);
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	ghost	Le fantôme à placer.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public abstract boolean requestInitialization (Player player, Ghost ghost, String position);
}