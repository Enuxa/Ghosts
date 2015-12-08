package base;
import core.*;

public class BaseCaptureRule extends CaptureRule {
	public BaseCaptureRule (int priority){
		super (priority);
	}
	
	@Override
	public boolean requestCapture(String square, Ghost ghost, Player player) {
		Square s = Game.getCurrent().getBoard().getSquare(square);
		
		return player.hasGhost(ghost) && s != null && s.getGhost() != null && !player.hasGhost(s.getGhost());
	}
}
