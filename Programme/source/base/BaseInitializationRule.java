package base;

import core.*;

/**
 * Règle pour un plateau classique de m x n cases.
 * Chaque joueur doit avoir (n-2) bons fantômes et autant de mauvais fantômes. Il les dispose sur deux lignes.
 * Les sorties d'un joueur sont les deux coins du côté opposé.
 */
public class BaseInitializationRule extends InitializationRule {
	public BaseInitializationRule (int priority){
		super(priority);
	}
	@Override
	public boolean requestInitialization(Player player, String position) {
		if (this.isReady(player))	// Le joueur est déjà prêt à jouer, il ne doit pas placer plus de pions
			return false;
		
		int boardSize = Game.getCurrent().getBoard().getSize();
		
		int l = 1;	// Ligne la plus en bas autorisée pour ce joueur
		if (player != Game.getCurrent().getPlayer(0))
			l = boardSize - 1;
		
		Square s = Game.getCurrent().getBoard().getSquare(position);
		
		//	S'il y a déjà un fantôme à cette position
		if (s == null || s.getGhost() != null)
			return false;
		
		char c = Board.toX(position);
		int i = Board.toY(position);
		
		return (c > 'A' && c < (char)('A' + boardSize - 1) && (i == l || i == l + 1));
	}

	@Override
	public boolean requestInitialization(Player player, Ghost ghost, String position) {
		if (!this.requestInitialization(player, position))
			return false;
		if (!ghost.getType().equals("Basique"))
			return true;
		

		int boardSize = Game.getCurrent().getBoard().getSize();
		
		return player.getGhosts(ghost.isGood()).size() < boardSize - 2;
	}

	@Override
	public boolean isReady(Player player) {
		int boardSize = Game.getCurrent().getBoard().getSize();
		
		return player.getGhosts().size() == (boardSize - 2) * 2;
	}
}