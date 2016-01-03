package base.Knights;

import core.*;

/**
 * Règle de mouvement pour les cavaliers
 */
public class KnightsMovementRule extends MovementRule {
	public KnightsMovementRule(int priority) {
		super(priority);
	}

	public boolean requestMovement (Player player, String squareA, String squareB){
		if (!super.requestMovement(player, squareA, squareB))
			return false;
		
		Board board = Game.getCurrent().getBoard();
		Square a = board.getSquare(squareA);
		Square b = board.getSquare(squareB);
		Ghost ghost = a.getGhost();
		
		if (ghost.getType() != "Cavalier")
			return true;
		
		int x = Math.abs(a.getX() - b.getX());
		int y = Math.abs(a.getY() - b.getY());
		
		return (x == 2 && y == 1) || (x == 1 && y == 2);
	}
}
