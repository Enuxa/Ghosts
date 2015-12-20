import core.*;

import java.util.*;

import base.*;

public class Test {
	public static void main(String[] args) {
		Player[] players = new Player[] {new Player("Clara"), new Player("Pierre")};

		Game game = new Game(new WindowsTextBasedInterface(), false,
				Arrays.asList(new Extension[] { new BaseExtension() }));

		game.initialize_test();

		for (int p = 0; p < 2; p++){
			for (int k = 0; k < 2; k++) {
				for (int i = 0; i < 4; i++) {
					Ghost g = new Ghost(k == 0, players[p]);
					String pos = Board.toCoordinates(1 + i, k + p * 4);
					g.move(pos);
					System.out.println(pos);
					players[p].addGhost(g);
				}
			}
		}
		
		game.run_test(players[0], players[1]);
	}
}