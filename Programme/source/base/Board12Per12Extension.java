package base;

import core.*;

/**
 * Extension pour un plateau de 12 x 12 cases
 */
public class Board12Per12Extension extends Extension {
	public Board12Per12Extension() {
		super("Plateau 12 x 12");
	}

	public void load() {
		Game.getCurrent().getRuleBook().addRule(new base.BaseInitializationRule(0));
		Game.getCurrent().getRuleBook().addRule(new base.BaseBoardCreationRule(1, 12));
	}
}
