package base;

import core.*;

/**
 * Extension vous forçant à faire sortir deux bons fantômes ou à capturer tous les fantômes adverses pour gagner
 */
public class HardcoreExtension extends Extension {
	public HardcoreExtension() {
		super("Extension hardcore");
	}

	public void load() {
		Game.getCurrent().getRuleBook().addRule(new HardcoreGameEndRule (1));
	}
	
	private class HardcoreGameEndRule extends BaseGameEndRule{
		public HardcoreGameEndRule(int priority) {
			super(priority);
		}
		public boolean isGameOver() {
			Player p0 = Game.getCurrent().getPlayer(0);
			Player p1 = Game.getCurrent().getPlayer(1);
			int ex0 = p0.getExited().size();
			int ex1 = p1.getExited().size();
			
			int n0 = p0.getPlayingGhosts().size();
			int n1 = p1.getPlayingGhosts().size();
			
			return (ex0 == 2 || ex1 == 2) || (n0 == 0 || n1 == 0);
		}

		@Override
		public Player getWinner() {
			Player p0 = Game.getCurrent().getPlayer(0);
			Player p1 = Game.getCurrent().getPlayer(1);
			
			if (p0.getExited().size() == 2 || p1.getPlayingGhosts().size() == 0)
				return p0;
			else if (p1.getExited().size() == 2 || p0.getPlayingGhosts().size() == 0)
				return p1;
			return null;
		}
	}
}
