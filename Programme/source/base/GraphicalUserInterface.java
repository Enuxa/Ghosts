package base;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import core.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GraphicalUserInterface extends Interface {
	private Window window;
	private class Window extends JFrame {
		private JPanel boardPanel, interactionPanel;
		private JLabel messageLabel;
		private JButton messageButton;
		public Window (){
			//	Réglages du panneau principal
			JPanel pane = (JPanel)this.getContentPane();
			pane.setLayout(new GridLayout ());
			this.setPreferredSize(new Dimension (600, 600));
			this.setTitle("Ghosts");
			
			this.messageLabel = new JLabel ();
			
			//	Bouton affichant un message d'attente lors d'un message
			this.messageButton = new JButton ();
			this.messageButton.addActionListener(
					new  ActionListener (){
						public void actionPerformed(ActionEvent e) {
							if (messageButton.getParent() == interactionPanel){
								
							}
						}
					}
					);
			
			//	Création du paneau de plateau
			this.boardPanel = new JPanel ();
			this.boardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			
			//	Création du paneau de plateau
			this.interactionPanel = new JPanel ();
			this.interactionPanel.setLayout(new GridLayout ());
			
			//	Séparation des panneaux
			
			//	Ajout des composants
			pane.add(this.boardPanel,BorderLayout.WEST);
			pane.add(this.interactionPanel, BorderLayout.EAST);
			this.pack();
		}
		
		/**
		 * Supprime tous les composants présents sur le panneau d'interaction
		 */
		private void clearInteractionPanel (){
			while (this.getComponentCount() != 1)
				this.remove(1);
		}
		
		public void printText (String msg){
			this.clearInteractionPanel();
			this.messageLabel.setText(msg);
			this.interactionPanel.add(this.messageLabel);
		}
		
		public void printText (String msg1, String msg2){
			if (msg2 == null)
				msg2 = "OK";
			this.clearInteractionPanel();
			this.messageLabel.setText(msg1);
			this.messageButton.setText(msg2);
			this.interactionPanel.add(this.messageLabel, BorderLayout.NORTH);
			this.interactionPanel.add(this.messageButton, BorderLayout.NORTH);
			
		}
	}
	
	
	public GraphicalUserInterface (){
		this.window = new Window ();
	}
	
	public void activate (){
		this.window.setVisible(true);
	}
	
	@Override
	public String readText(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDisplay(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printText(String message) {
		this.window.printText(message);
	}

	@Override
	public void printText(String message1, String message2) {
		this.window.printText(message1, message2);
		if (this.window.)
	}

	@Override
	public String readPosition(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> readSelection(Collection<String> choice, String message, int min, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> readSelection(Collection<String> choice, int min, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readSelection(Collection<String> choice, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readSelection(Collection<String> choice) {
		// TODO Auto-generated method stub
		return null;
	}

}
