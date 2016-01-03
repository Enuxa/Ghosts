package base.Knights;

import core.*;

/**
 * Règle d'initialisation pour les cavaliers
 */
public class KnightsInitializationRule extends InitializationRule {
	
	public KnightsInitializationRule(int priority) {
		super(priority);
	}

	public boolean requestInitialization(Player player, Ghost ghost, String position) {
		if (!ghost.getType().equals("Cavalier"))
			return true;
		return player.getGhosts("Cavalier").size() < Game.getCurrent().getBoard().getSize()/ 2 - 1;
	}
}
