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
		
		if (!super.requestInitialization(player, position))
			return false;
		
		int size = Game.getCurrent().getBoard().getSize();
		
		return player.getGhosts("Cavalier").size() < size/ 2 - 1
				&& player.getGhosts(ghost.isGood()).size() < size - 2;
	}
}
