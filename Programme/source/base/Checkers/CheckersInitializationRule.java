package base.Checkers;

import core.*;

/**
 * Règle d'initialisation pour les cavaliers
 */
public class CheckersInitializationRule extends InitializationRule {
	public CheckersInitializationRule(int priority) {
		super(priority);
	}

	public boolean requestInitialization(Player player, Ghost ghost, String position) {
		if (!ghost.getType().equals("Dame"))
			return true;

		int size = Game.getCurrent().getBoard().getSize();
		
		return player.getGhosts("Dame").size() < size - 2
				&& player.getGhosts(ghost.isGood()).size() < size - 2;
	}
}
