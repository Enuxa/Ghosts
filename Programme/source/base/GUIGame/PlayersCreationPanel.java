package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import core.*;

/**
 * Paneau de création des joueurs
 */
@SuppressWarnings("serial")
public class PlayersCreationPanel extends JPanel {
	/**
	 * @param p0 Joueur 1
	 * @param p1 Joueur 2
	 * @param game L'instance du jeu en cours
	 * @param interactionPanel Le panneau d'interaction
	 */
	public PlayersCreationPanel (Player p0, Player p1, final GUIGame game, final JPanel interactionPanel, Window window){
		JButton button = new JButton ("Confirmer");
		final PlayerCreationPanel panel0 = new PlayerCreationPanel (p0, "Joueur 1");
		final PlayerCreationPanel panel1 = new PlayerCreationPanel (p1, "Joueur 2");
		
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				panel0.setPlayer();
				panel1.setPlayer();
				game.setCurrentState(GameState.playerInitialization);
				interactionPanel.removeAll();
				interactionPanel.repaint();
				window.displayAvailableSquares();
				window.setMessage("Au tour de " + game.getCurrentPlayer() + " de choisir ses fantômes");
			}
		});

		this.setLayout(new GridLayout (3,1));

		//	Ajout des composants
		this.add(panel0);
		this.add(panel1);
		this.add(button);
	}
	/**
	 * Panneau de création pour un joueur donné
	 */
	private class PlayerCreationPanel extends JPanel{
		private Player player;
		private JCheckBox autoCB;
		private JTextField nameField;
		private JButton selectFileButton;
		private File file;
		/**
		 * @param player Le joueur à créer
		 * @param title Titre de la bordure
		 */
		public PlayerCreationPanel (Player player, String title){
			this.player = player;
			this.autoCB = new JCheckBox ("Joueur automatique ");
			this.nameField = new JTextField ("Nom du joueur");
			this.selectFileButton = new JButton ("Fichier de jeu automatique");
			this.file = null;
			
			this.selectFileButton.setEnabled(false);
			this.setLayout(new GridLayout (3, 1));
			
			//	Lorsque l'on coche le joueur automatique, on déverrouille le bouton de sélection de fichier de jeu automatique
			this.autoCB.addActionListener(new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					selectFileButton.setEnabled(autoCB.isSelected());
				}
				
			});
			
			//	Si on clique sur le bouton de sélection du fichier, on ouvre une fenetre de dialogue
			this.selectFileButton.addActionListener(new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser ();
					fc.showOpenDialog(null);
					file = fc.getSelectedFile();
					if (file != null)
						selectFileButton.setText(file.getPath());
				}
			});
			
			//	Ajout des composants
			this.add(this.autoCB);
			this.add(this.nameField);
			this.add(this.selectFileButton);
			
			this.setBorder(BorderFactory.createTitledBorder(title));
		}
		
		/*
		 * Assigne les informations recueillies au joueur
		 */
		public void setPlayer (){
			this.player.setName(this.nameField.getText());
			if (this.autoCB.isSelected()){
				this.player.setAutoplay(new AutoPlay (this.file, this.player));
			}
		}
	}
}
