package base;

import core.Extension;
import core.Game;

/**
 * Extension pour un plateau de 10 x 10 cases très spécial
 */
public class SpecialBoardExtension extends Extension {
	public SpecialBoardExtension() {
		super("Plateau spécial");
	}

	public void load() {
		Game.getCurrent().getRuleBook().addRule(new SpecialBoardInitializationRule (1));
	}
}
