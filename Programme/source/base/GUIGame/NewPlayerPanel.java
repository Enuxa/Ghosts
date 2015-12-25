package base.GUIGame;

import java.awt.event.*;

import javax.swing.*;

public class NewPlayerPanel extends JPanel {
	private GUIGame game;
	private Window window;
	private JTextField textBox;
	public NewPlayerPanel (GUIGame game, Window window){
		this.game = game;
		this.window = window;
		
		this.add(new JLabel ("Quel est votre nom ?"));
		this.textBox = new JTextField ("Votre nom");
		this.add(this.textBox);
		
		JButton confirm = new JButton ("Confirmer");
		confirm.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				String name = textBox.getText();
				if (name.equals(""))
					javax.swing.JOptionPane.showMessageDialog(window, "Votre nom est invalide !");
				else{
					game.getCurrentPlayer().setName(name);
					window.nextPlayer();
				}
			}
		});
		
		
		this.add(confirm);
	}

}
