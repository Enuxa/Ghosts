package base;

import core.*;

/**
 * Extension vous forçcant à faire sortir deux bons fantômes pour gagner
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
			int ex0 = Game.getCurrent().getPlayer(0).getExited().size();
			int ex1 = Game.getCurrent().getPlayer(1).getExited().size();
			
			return (ex0 == 2 || ex1 == 2) && super.isGameOver();
		}

		@Override
		public Player getWinner() {
			Player p0 = Game.getCurrent().getPlayer(0);
			Player p1 = Game.getCurrent().getPlayer(1);
			
			if (p0.getExited().size() == 2)
				return p0;
			else if (p1.getExited().size() == 2)
				return p1;
			return null;
		}
		
	}
}
