package base;

import java.util.Arrays;

import core.*;

public class BaseInitializationRule extends InitializationRule {
	public BaseInitializationRule (int priority){
		super (priority);
	}
	@Override
	public boolean requestInitialization(Player player, String position) {
		if (this.isReady(player))	// I le joueur est déjà prêt à jouer, il ne doit pas placer plus de pions
			return false;
		
		int l = 1;	// Ligne la plus en bas autorisée pour ce joueur
		if (player != Game.getCurrent().getPlayer(0))
			l = 5;
		
		Square s = Game.getCurrent().getBoard().getSquare(position);
		
		//	S'il y a déjà un fantôme à cette position
		if (s == null || s.getGhost() != null)
			return false;
		
		char c = Board.toX(position);
		int i = Board.toY(position);
		
		return (c > 'A' && c < 'F') && (i == l || i == l+1);
	}

	@Override
	public boolean requestInitialization(Player player, Ghost ghost, String position) {
		if (!this.requestInitialization(player, position))
			return false;
		return player.getGhosts(ghost.isGood()).size() < 4;
	}

	@Override
	public Board getBoard() {
		return new Board (6, Arrays.asList(new String[]{"a6", "f6"}), Arrays.asList(new String[]{"a1", "f1"}));
	}

	@Override
	public boolean isReady(Player player) {
		return player.getGhosts().size() == 8;
	}
}