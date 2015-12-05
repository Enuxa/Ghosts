package core;

public abstract class CaptureRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public CaptureRule(int priority) {
		super(priority);
	}
	/**
	 * Indique si une capture est autorisée
	 * @param square Case où <code>player</code> souhaite effectuer une capture
	 * @param ghost Le fantôme à déplacer
	 * @param player Le joueur souhaitant effectuer la capture
	 * @return <code>true</code> si la capture est autorisée, <code>false</code> sinon.
	 */
	public abstract boolean requestCapture (String square, Ghost ghost, Player player);
}
