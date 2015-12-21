package core;

public abstract class GameEndRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public GameEndRule(int priority) {
		super(priority);
	}
	/**
	*	Indique si la partie est terminée ou non.
	*	@return	<code>true</code> si la partie est finie, <code>false</code> sinon.
	*/
	public abstract boolean isGameOver ();
	/**
	 * Récupère le gagnant de la partie.
	 * @return Instance de <code>Player</code> correspondant au gagnant de la partie, <code>null</code> si la partie n'est pas finie ou s'il y a match nul.
	 */
	public abstract Player getWinner ();
}
