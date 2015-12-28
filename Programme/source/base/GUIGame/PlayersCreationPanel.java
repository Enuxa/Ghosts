package base.GUIGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import core.*;

public class PlayersCreationPanel extends JPanel {
	public PlayersCreationPanel (Player p0, Player p1, GUIGame game, JPanel interactionPanel){
		this.setLayout(new GridLayout (3,1));
		JButton button = new JButton ("Confirmer");
		
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				game.setCurrentState(GameState.playerInitialization);
				interactionPanel.removeAll();
				interactionPanel.repaint();
			}
		});

		this.add(new PlayerCreationPanel (p0, "Joueur 1"));
		this.add(new PlayerCreationPanel (p0, "Joueur 1"));
		this.add(button);
	}
	private class PlayerCreationPanel extends JPanel{
		private Player player;
		private JCheckBox autoCB;
		private JTextField nameField;
		private JButton selectFileButton;
		private File file;
		public PlayerCreationPanel (Player player, String title){
			this.player = player;
			this.autoCB = new JCheckBox ("Joueur automatique ");
			this.nameField = new JTextField ("Nom du joueur");
			this.selectFileButton = new JButton ("Fichier de jeu automatique");
			this.file = null;

			this.autoCB.addActionListener(new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					selectFileButton.setEnabled(autoCB.isSelected());
				}
				
			});
			
			this.selectFileButton.setEnabled(false);
			
			this.selectFileButton.addActionListener(new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser ();
					fc.showOpenDialog(null);
					file = fc.getSelectedFile();
					if (file != null)
						selectFileButton.setText(file.getPath());
				}
			});
			
			this.setLayout(new GridLayout ());
			
			this.add(this.autoCB);
			this.add(this.nameField);
			this.add(this.selectFileButton);
			
			this.setBorder(BorderFactory.createTitledBorder(title));
		}
		
		public void setPlayer (){
			this.player.setName(this.nameField.getText());
			if (this.autoCB.isSelected()){
				this.player.setAutoplay(new AutoPlay (this.file, this.player));
			}
		}
	}
}
