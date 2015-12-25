package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import core.*;

@SuppressWarnings("serial")
public class NewGhostPanel extends JPanel {
	private JPanel interactionPanel;
	private JRadioButton good, bad;
	private JComboBox<String> ghostTypesBox;
	private String coordinates;
	private GUIGame game;
	private Window window;
	
	public NewGhostPanel (String coordinates, GUIGame game, Window window, JPanel interactionPanel){
		this.interactionPanel = interactionPanel;
		this.window = window;
		this.game = game;
		this.coordinates = coordinates;
		
		Collection<String> ghostTypes = this.game.getFactory().getTypes();
		ghostTypesBox = new JComboBox<String> (ghostTypes.toArray(new String[ghostTypes.size()]));
		ButtonGroup group = new ButtonGroup ();
		good = new JRadioButton ("Bon");
		bad = new JRadioButton ("Mauvais");
		JButton confirm = new JButton ("Confirmer");
		group.add(good);
		group.add(bad);
		group.setSelected(good.getModel(), true);
		
		confirm.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				NewGhostPanel ngp = (NewGhostPanel)b.getParent();
				Ghost ghost = ngp.getGhost();
				String position = ngp.getPosition();
				if (game.getRuleBook().requestInitialization(game.getCurrentPlayer (), ghost, position)){
					game.getCurrentPlayer ().addGhost(ghost);
					ghost.move(position);
					window.updateDisplay();
					interactionPanel.removeAll();
					interactionPanel.repaint();

					if (game.getRuleBook().isReady(game.getCurrentPlayer ())){
						interactionPanel.removeAll();
						
						interactionPanel.add(new NewPlayerPanel (game, window));
					}
				}
			}
		});
		
		this.add(new Label ("Quel fantôme souhaitez vous ajouter en " + coordinates + " ?"), BorderLayout.NORTH);
		this.add(ghostTypesBox, BorderLayout.NORTH);
		this.add(good, BorderLayout.NORTH);
		this.add(bad, BorderLayout.NORTH);
		this.add(confirm, BorderLayout.NORTH);
	}
	
	public Ghost getGhost (){
		GhostFactory factory = this.game.getFactory();
		String selected = (String)ghostTypesBox.getSelectedItem();
		return factory.createGhost(selected, good.isSelected(), this.game.getCurrentPlayer ());
	}
	
	public String getPosition (){
		return this.coordinates;
	}
}