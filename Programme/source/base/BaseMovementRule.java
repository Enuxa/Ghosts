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
		if (!player.hasGhost(a.getGhost()))		// Si le fant�me de d�part est inexistant ou s'il n'appartient pas au joueur
			return false;
		if (player.hasGhost(b.getGhost()))		// Si le fant�me sur la case d'arriv�e (s'il existe) appartient � player
			return false;
		if (!(Math.abs(a.getX() - b.getX()) == 1 && a.getY() == b.getY())	// Si le d�placement est bien d'une case horizontale ou verticale
				&& !(Math.abs(a.getY() - b.getY()) == 1 && a.getX() == b.getX()))
			return false;
		if (b.getGhost() != null)	// S'il y a un fant�me sur la case d'arriv�e (n�cessairement � l'adversaire) et si sa capture est autoris�e
			return this.checkCapture(player, squareB, a.getGhost());
		
		return true;
	}
}