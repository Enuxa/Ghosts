package base.Checkers;

import core.Extension;
import core.Game;

/**
 *	Extension pour les dames
 */
public class CheckersExtension extends Extension {

	public CheckersExtension() {
		super("Fantômes dames");
	}

	public void load() {
		Game.getCurrent().getRuleBook().addRule(new CheckersInitializationRule (0));
		Game.getCurrent().getRuleBook().addRule(new CheckersMovementRule (0));
		Game.getCurrent().getFactory().addGhostType("Dame");
	}
}