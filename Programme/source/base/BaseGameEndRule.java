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
		Player p = g.getPlayer(id);
		return (p.getGhosts(false).size() == 0
				|| g.getPlayer(id + 1 % 2).getGhosts().size() == 0
				|| p.getGhostInitialGhostQuantity() == p.getExited().size());
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
