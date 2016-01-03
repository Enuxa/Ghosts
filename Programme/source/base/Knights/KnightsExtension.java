package base.Knights;

import core.Extension;
import core.Game;

public class KnightsExtension extends Extension {

	public KnightsExtension() {
		super("Fantômes cavaliers");
	}

	public void load() {
		Game.getCurrent().getRuleBook().addRule(new KnightsInitializationRule (0));
		Game.getCurrent().getRuleBook().addRule(new KnightsMovementRule (0));
		Game.getCurrent().getFactory().addGhostType("Cavalier");
	}

}
