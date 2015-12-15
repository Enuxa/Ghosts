package base;

import core.*;

public class BaseMovementRule extends MovementRule {
	public BaseMovementRule (int priority){
		super (priority);
	}
	
	@Override
	public boolean requestMovement(Player player, String squareA, String squareB) {
		Board board = Game.getCurrent().getBoard();
		Square a = board.getSquare(squareA);
		Square b = board.getSquare(squareB);
		if (a == null || b == null)		// Si l'une des cases n'existe pas
			return false;
		if (!player.hasGhost(a.getGhost()))		// Si le fantôme de départ est inexistant ou s'il n'appartient pas au joueur
			return false;
		if (player.hasGhost(b.getGhost()))		// Si le fantôme sur la case d'arrivée (s'il existe) appartient à player
			return false;
		if (!(Math.abs(a.getX() - b.getX()) == 1 && a.getY() == b.getY())	// Si le déplacement est bien d'une case horizontale ou verticale
				&& !(Math.abs(a.getY() - b.getY()) == 1 && a.getX() == b.getX()))
			return false;
		if (b.getGhost() != null)	// S'il y a un fantôme sur la case d'arrivée (nécessairement à l'adversaire) et si sa capture est autorisée
			return this.checkCapture(player, squareB, a.getGhost());
		
		return true;
	}
}