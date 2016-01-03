package base;

import core.*;

/**
 * Extension de base
 */
public class BaseExtension extends Extension {
	public BaseExtension (){
		super ("Extension de base");
	}

	public void load() {
		RuleBook r = Game.getCurrent().getRuleBook ();
		r.addRule(new BaseGameEndRule (0));
		r.addRule(new BaseInitializationRule (0));
		r.addRule(new BaseMovementRule (0));
		r.addRule(new BaseCaptureRule (0));
		r.addRule(new BaseBoardCreationRule (0));
		Game.getCurrent().getFactory().addGhostType("Basique");
	}
}