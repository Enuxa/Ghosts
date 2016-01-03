package base;

import core.*;

public class BaseMovementRule extends MovementRule {
	public BaseMovementRule (int priority){
		super (priority);
	}
	
	@Override
	public boolean requestMovement(Player player, String squareA, String squareB) {
		if (!super.requestMovement(player, squareA, squareB))
			return false;
		
		Board board = Game.getCurrent().getBoard();
		Square a = board.getSquare(squareA);
		Square b = board.getSquare(squareB);
		
		if (a.getGhost().getType() != "Basique")
			return true;
		
		if (!(Math.abs(a.getX() - b.getX()) == 1 && a.getY() == b.getY())	// Si le déplacement est bien d'une case horizontale ou verticale
				&& !(Math.abs(a.getY() - b.getY()) == 1 && a.getX() == b.getX()))
			return false;
		if (b.getGhost() != null)	// S'il y a un fantôme sur la case d'arrivée (nécessairement à l'adversaire) et si sa capture est autorisée
			return Game.getCurrent().getRuleBook().requestCapture(player, squareB, a.getGhost());
		
		return true;
	}
}