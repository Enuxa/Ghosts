package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;
import core.*;

public class SquareController implements ActionListener{
	public GUIGame game;
	public JPanel interactionPanel; 
	public Window window;
	public SquareController (GUIGame guiGame, JPanel interactionPanel, Window window){
		this.game = guiGame;
		this.interactionPanel = interactionPanel;
		this.window = window;
	}
	public void actionPerformed(ActionEvent arg) {
		Component[] tabComp = this.interactionPanel.getComponents();
		if (tabComp.length > 1 && tabComp[1] instanceof NewGhostPanel)
			this.interactionPanel.removeAll();
		this.interactionPanel.repaint();
		
		JButton originButton = (JButton)arg.getSource();
		
		String coordinates = originButton.getName();
		Square square = this.game.getBoard().getSquare(coordinates);
		if (this.game.getCurrentState()== GameState.playerInitialization){
			if (this.game.getRuleBook().requestInitialization(this.game.getCurrentPlayer(), coordinates)){
				if (square.getGhost() == null){
					JPanel ngp = new NewGhostPanel (coordinates, this.game, this.window, this.interactionPanel);
					this.interactionPanel.add(ngp);
					this.window.validate();
				}
			}else if (this.game.getRuleBook ().isReady(this.game.getCurrentPlayer ()))
				this.window.nextPlayer ();
		}else{
			JButton[][] buttons = window.getSquareButtons();
			
			ActionListener al = new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					Board board = game.getBoard();
					RuleBook rb = game.getRuleBook();
					Player p = game.getCurrentPlayer();
					JButton button = (JButton)arg0.getSource();
					String destination = button.getName();
					if (rb.requestMovement(p, coordinates, destination)){
						Square destSquare = board.getSquare(destination);
						if (destSquare.getGhost() != null)
							board.capture(destSquare.getGhost());
						board.getSquare(coordinates).getGhost().move(destination);
						originButton.setIcon(null);
						window.updateDisplay();
						window.nextPlayer();
					}

					//	On n'a plus besoin de cet ActionListener pour le moment ; on l'enlève
					for (JButton[] tab : buttons)
						for (JButton b: tab)
							b.removeActionListener(this);
				}
			};

			for (JButton[] tab : buttons)
				for (JButton b: tab)
					b.addActionListener(al);
		}
	}
}