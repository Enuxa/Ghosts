package core.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	 * @param window La fenêtre de jeu
	 */
	public PlayersCreationPanel (Player p0, Player p1, final GUIGame game, final JPanel interactionPanel, final Window window){
		JButton button = new JButton ("Confirmer");
		final PlayerCreationPanel panel0 = new PlayerCreationPanel (p0, "Joueur 1");
		final PlayerCreationPanel panel1 = new PlayerCreationPanel (p1, "Joueur 2");
		
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				try {
					panel0.setPlayer();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(window, "Impossible d'utiliser le fichier " + panel0.file + " :\n" + e.getMessage());
					return;
				}
				try {
					panel1.setPlayer();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(window, "Impossible d'utiliser le fichier " + panel1.file + " :\n" + e.getMessage());
					return;
				}
				interactionPanel.removeAll();
				interactionPanel.repaint();
				game.nextStep();
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
					fc.setFileFilter(new FileNameExtensionFilter ("Fichier de configuration Ghosts", "gf"));
					fc.setCurrentDirectory(Paths.get(".").toAbsolutePath().toFile());
					fc.showOpenDialog(null);
					File f = fc.getSelectedFile();
					if (f != null){
						file = f;
						selectFileButton.setText(file.getPath());
					}
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
		public void setPlayer () throws Exception{
			this.player.setName(this.nameField.getText());
			if (this.autoCB.isSelected()){
				AutoPlay ap = new AutoPlay (this.file.getAbsolutePath(), this.player);
				this.player.setAutoplay(ap);
			}
		}
	}
}
