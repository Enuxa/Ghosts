package base;

import core.*;
import java.util.*;

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
		Player op = g.getPlayer ((id + 1) % 2);
		Player p = g.getPlayer(id);

		Collection<Ghost> gBad = p.getGhosts(false);
		Collection<Ghost> gGood = p.getGhosts(true);
		Collection<Ghost> gEx = p.getExited();
		
		boolean b1 = gBad.size() == 0;						//	Si tous ses mauvais fant�mes ont �t� captur�s
		boolean b2 = op.getCaptured().containsAll(gGood);	//	Si tous les bons fant�mes adverses ont �t� captur�s
		boolean b3 = gEx.size() == 1;						//	Si un des (bons) fant�mes est sorti
		
		return b1 || b2 || b3;
	}

	@Override
	public Player getWinner() {
		Game g = Game.getCurrent();
		for (int i = 0; i < 2; i++){
			if (this.hasWon(i))
				return g.getPlayer(i);
		}
		return null;
	}
}