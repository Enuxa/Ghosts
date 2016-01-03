package base;

import java.util.*;
import core.*;

/**
 * Règle pour un plateau classique de m x n cases.
 * Chaque joueur doit avoir (n-2) bons fantômes et autant de mauvais fantômes. Il les dispose sur les 
 * Les sorties d'un joueur sont les deux coins du côté opposé.
 */
public class BaseInitializationRule extends InitializationRule {
	private int boardSize;
	public BaseInitializationRule (int priority){
		this (priority, 6);
	}
	/**
	 * @param priority La priorité de cette règle
	 * @param size Le nombre cases sur un coté du plateau
	 */
	public BaseInitializationRule (int priority, int size){
		super(priority);
		this.boardSize = size;
	}
	@Override
	public boolean requestInitialization(Player player, String position) {
		if (this.isReady(player))	// Le joueur est déjà prêt à jouer, il ne doit pas placer plus de pions
			return false;
		
		int l = 1;	// Ligne la plus en bas autorisée pour ce joueur
		if (player != Game.getCurrent().getPlayer(0))
			l = this.boardSize - 1;
		
		Square s = Game.getCurrent().getBoard().getSquare(position);
		
		//	S'il y a déjà un fantôme à cette position
		if (s == null || s.getGhost() != null)
			return false;
		
		char c = Board.toX(position);
		int i = Board.toY(position);
		
		return (c > 'A' && c < (char)('A' + this.boardSize - 1) && (i == l || i == l + 1));
	}

	@Override
	public boolean requestInitialization(Player player, Ghost ghost, String position) {
		if (!this.requestInitialization(player, position))
			return false;
		if (!ghost.getType().equals("Basique"))
			return true;
		return player.getGhosts(ghost.isGood()).size() < this.boardSize - 2;
	}

	@Override
	public Board getBoard() {
		String maxRow = Integer.toString(this.boardSize);
		String maxCol = Character.toString((char)('A' + this.boardSize - 1));
		return new Board (this.boardSize, Arrays.asList(new String[]{"A" + maxRow, maxCol + maxRow}),
				Arrays.asList(new String[]{"A1", maxCol + "1"}));
	}

	@Override
	public boolean isReady(Player player) {
		return player.getGhosts().size() == (this.boardSize - 2) * 2;
	}
}