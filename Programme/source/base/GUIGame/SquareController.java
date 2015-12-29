package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.*;

/**
 *	Contrôleur de case
 */
public class SquareController implements ActionListener{
	public GUIGame game;
	public JPanel interactionPanel; 
	public Window window;
	/**
	 * @param guiGame Instance du jeu en cours
	 * @param interactionPanel Panneau d'interaction
	 * @param window Fenetre de jeu
	 */
	public SquareController (GUIGame guiGame, JPanel interactionPanel, Window window){
		this.game = guiGame;
		this.interactionPanel = interactionPanel;
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent arg) {
		Component[] tabComp = this.interactionPanel.getComponents();
		//	Si on a cliqué sur une case et qu'on avait deja un panneau de création de fantôme, on vide la panneau d'interaction
		if (tabComp.length >= 1 && tabComp[0] instanceof NewGhostPanel){
			this.interactionPanel.removeAll();
			this.interactionPanel.repaint();
		}
		
		JButton originButton = (JButton)arg.getSource();
		String coordinates = originButton.getName();
		Square square = this.game.getBoard().getSquare(coordinates);
		
		//	Si on est cours d'initialisation d'un joueur
		if (this.game.getCurrentState() == GameState.playerInitialization){
			//	Si le joueur actuel est autorisé à éventuellement placer un fantôme sur cette case...
			if (this.game.getRuleBook().requestInitialization(this.game.getCurrentPlayer(), coordinates)){
				//	... et s'il n'y a pas déjà de fantôme sur cette case
				if (square.getGhost() == null){
					//	Alors on ajoute un panneau de création de fantôme !
					JPanel ngp = new NewGhostPanel (coordinates, this.game, this.window, this.interactionPanel);
					this.interactionPanel.add(ngp);
					this.window.validate();
				}
			}
		//	Si on est en plein tour et que le joueur n'a pas encore joué, on lui offre la possibilité de déplacer le pion qu'il a cliqué (s'il existe)
		}else if (this.game.getCurrentState() == GameState.inTurn && !game.hasPlayed() && square.getGhost() != null){
			JButton[][] buttons = window.getSquareButtons();
			
			ActionListener al = this.getMovementActionListener(coordinates, buttons);

			//	On ajoute a toutes les cases l'ActionListener permettant de réagir à la demande de déplacement
			for (JButton[] tab : buttons)
				for (JButton b: tab)
					b.addActionListener(al);
		}
	}
	
	private ActionListener getMovementActionListener (final String coordinates, final JButton[][] buttons){
		return new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				Board board = game.getBoard();
				RuleBook rb = game.getRuleBook();
				Player p = game.getCurrentPlayer();
				JButton button = (JButton)arg0.getSource();
				String destination = button.getName();
				Square destSquare = board.getSquare(destination);
				
				//	Si le mouvement est autorisé par le livre de règles
				if (rb.requestMovement(p, coordinates, destination)){
					//	S'il y a un fantôme sur la case d'arrivée, il peut etre capturé car le mouvement est autorisé
					if (destSquare.getGhost() != null)
						board.capture(destSquare.getGhost());
					//	On efectue le déplacement
					board.getSquare(coordinates).getGhost().move(destination);

					game.setHasPlayed(true);	//	On indique que le joueur a joué
					window.updateDisplay();		//	On met a jour l'affichage
					window.nextPlayer();		//	On propose au prochain joueur de jouer
				}

				//	On n'a plus besoin de cet ActionListener pour le moment ; on l'enlève
				for (JButton[] tab : buttons)
					for (JButton b: tab)
						b.removeActionListener(this);
			}
		};
	}
}