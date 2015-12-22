import java.util.*;

import base.*;
import core.*;

public class Main {

	public static void main(String[] args) {
		Collection<Extension> extensions = new ArrayList <Extension>();
		extensions.add(new BaseExtension ());
		
		Game game = new GUIGame (false, extensions);
		
		game.run();
	}

}
