package core.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import core.*;

/**
 * Le panneau pour créer un fantôme
 */
@SuppressWarnings("serial")
public class NewGhostPanel extends JPanel {
	private JPanel interactionPanel;
	private JRadioButton good, bad;
	private JComboBox<String> ghostTypesBox;
	private String coordinates;
	private GUIGame game;
	private Window window;
	
	/**
	 * @param coordinates Les coordonnées de la case ou placer le fantôme
	 * @param game L'instance du jeu
	 * @param window La fenêtre de jeu
	 * @param interactionPanel Le panneau d'interaction de la fenêtre
	 */
	public NewGhostPanel (String coordinates, GUIGame game, Window window, JPanel interactionPanel){
		this.interactionPanel = interactionPanel;
		this.window = window;
		this.game = game;
		this.coordinates = coordinates;
		
		this.setLayout(new GridLayout (20, 2));
		
		// ComboBox proposant les différents types de fantomes disponible
		Collection<String> ghostTypes = this.game.getFactory().getTypes();
		ghostTypesBox = new JComboBox<String> (ghostTypes.toArray(new String[ghostTypes.size()]));
		
		//	Boutons radio pour définir si le fantôme est bon ou mauvais
		ButtonGroup group = new ButtonGroup ();
		good = new JRadioButton ("Bon");
		bad = new JRadioButton ("Mauvais");
		group.add(good);
		group.add(bad);
		group.setSelected(good.getModel(), true);

		//	Bouton de confirmation
		JButton confirm = new JButton ("Confirmer");
		confirm.addActionListener(this.newActionListener());
		
		//	Ajout des composants
		this.add(new Label ("Quel fantôme souhaitez vous ajouter en " + coordinates + " ?"), BorderLayout.PAGE_START);
		this.add(good);
		this.add(bad);
		this.add(ghostTypesBox);
		this.add(confirm);
	}
	
	/**
	 * Crée l'action listener pour le bouton de confirmation
	 * @return L'action listener
	 */
	private ActionListener newActionListener (){
		return new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				NewGhostPanel ngp = (NewGhostPanel)b.getParent();
				Ghost ghost = ngp.getGhost();
				if (game.getRuleBook().requestInitialization(game.getCurrentPlayer (), ghost, coordinates)){
					game.getCurrentPlayer ().addGhost(ghost);
					ghost.move(coordinates);
					window.updateDisplay();
					interactionPanel.removeAll();
					interactionPanel.repaint();

					if (game.getRuleBook().isReady(game.getCurrentPlayer ()))
						window.nextPlayer();
				}
			}
		};
	}
	
	/**
	 * Récupère le fantôme décrit par ce panneau
	 * @return Le fantôme
	 */
	public Ghost getGhost (){
		GhostFactory factory = this.game.getFactory();
		String selected = (String)ghostTypesBox.getSelectedItem();
		return factory.createGhost(selected, good.isSelected(), this.game.getCurrentPlayer ());
	}
}