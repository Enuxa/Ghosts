package core;
public abstract class InitializationRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public InitializationRule(int priority) {
		super(priority);
	}
	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	ghost	Le fantôme à placer.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public abstract boolean requestInitialization (Player player, Ghost ghost, String position);
	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public abstract boolean requestInitialization (Player player, String position);
	/**
	* Peut renvoyer un plateau
	* @return Un plateau si elle est la règle de priorité maximale supposée générer le plateau, <code>null</code> sinon
	*/
	public abstract Board getBoard ();
	/**
	 * Indique si un joueur est prêt à jouer
	 * @return <code>true</code> si le joueur a une configuration correcte, <code>false</code> sinon.
	 * @param player Le joueur
	 */
	public abstract boolean isReady (Player player);
}
