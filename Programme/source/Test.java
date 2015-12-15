import core.*;

import java.util.*;

import base.*;

public class Test {
	public static void main(String[] args) {
		Player p0 = new Player("Clara"), p1 = new Player("Pierre");

		Game game = new Game(new WindowsTextBasedInterface(), true,
				Arrays.asList(new Extension[] { new BaseExtension() }));

		game.initialize_test();

		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < 4; i++) {
				Ghost g = new Ghost(k == 0);
				g.move(Board.toCoordinates(1 + i, k));
			}
		}
		
		for (int k = 0; k < 2; k++){
			for (int i = 0; i < 4; i++){
				Ghost g = new Ghost (k == 0);
				if (i == 0 && k == 0){
					g.move("A5");
				}else
					g.move(Board.toCoordinates(1 + i, k));
				p0.addGhost(g);
				g = new Ghost(k == 0);
				g.move(Board.toCoordinates(1 + i, 4 + k));
				p1.addGhost(g);
			}
		}
		
		game.run_test(p0, p1);
	}
}