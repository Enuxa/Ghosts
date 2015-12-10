package base;

import core.*;

public class BaseGameEndRule extends GameEndRule {
	public BaseGameEndRule (int priority){
		super (priority);
	}
	@Override
	public boolean isGameOver() {
		return (this.hasWon(0) || this.hasWon(1));
	}
	private boolean hasWon (int id){
		Game g = Game.getCurrent();
		Player op = g.getPlayer (id + 1 % 2);
		Player p = g.getPlayer(id);
		return (p.getGhosts(false).size() == 0								//	Si tous ses mauvais fantômes ont été capturés
				|| op.getCaptured().containsAll(op.getGhosts(true))			//	Si tous les bons fantômes adverses ont été capturés
				|| p.getExited().size() != 1);								//	Si un des (bons) fantômes est sorti
	}

	@Override
	public Player getWinner() {
		Game g = Game.getCurrent();
		if (this.hasWon(0))
			return g.getPlayer(0);
		if (this.hasWon(1))
			return g.getPlayer(1);
		return null;
	}

}
