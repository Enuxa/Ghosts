package base;

import core.*;
import java.util.*;

/**
 * Règle de fin de jeu de base
 */
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
		Collection<Ghost> opGood = op.getGhosts(true);
		Collection<Ghost> gEx = p.getExited();
		
		boolean b1 = !gBad.isEmpty() && p.getCaptured().containsAll(gBad);		//	Si tous ses mauvais fantômes ont été capturés
		boolean b2 = !opGood.isEmpty() && op.getCaptured().containsAll(opGood);	//	Si tous les bons fantômes adverses ont été capturés
		boolean b3 = gEx.size() == 1;											//	Si un des (bons) fantômes est sorti
		
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