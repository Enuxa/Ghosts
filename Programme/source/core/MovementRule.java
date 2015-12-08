package core;

import java.util.*;

public abstract class MovementRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public MovementRule(int priority) {
		super(priority);
	}
	/**
	*	Indique si un déplacement est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public abstract boolean requestMovement (Player player, String squareA, String squareB);
	/**
	 * Indique si une capture est autorisée
	 * @param player Le joueur qui souhaite capturer
	 * @param square La case où a lieu la capture
	 * @param ghost Le fantôme qui va capturer
	 * @return <code>true</code> si cette capture est autorisée, <code>false</code> sinon.
	 */
	protected boolean checkCapture (Player player, String square, Ghost ghost) {
		SortedSet<Rule> rules = Game.getCurrent().getRuleBook().getTopCaptureRules();
		for (Rule r : rules){
			if (!((CaptureRule)r).requestCapture(square, ghost, player))
				return false;
		}
		
		return true;
	}
}
