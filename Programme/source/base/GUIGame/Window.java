package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import core.*;

public class Window extends JFrame{
	private JPanel boardPanel, interactionPanel;
	private JButton[][] squareButtons;
	private Map<String, Icon> ghostIcons;
	private Icon hiddenGhostIcon;
	private GUIGame game;
	
	public Window (GUIGame game){
		this.game = game;
		this.loadIcons();
		
		this.setTitle("Ghosts" + (this.game.isCheatModeEnabled () ? "[mode triche]" : ""));
		
		JPanel pane = (JPanel)this.getContentPane();
		pane.setLayout(new GridLayout ());
		
		this.setPreferredSize(new Dimension (1000,500));
		
		this.initInteractionPanel();
		this.initBoardPanel();
		
		//	Ajout des composants
		pane.add(this.boardPanel, BorderLayout.WEST);
		pane.add(this.interactionPanel, BorderLayout.EAST);
		
		this.pack();
	}
	
	public static Color getExitColor (int id){
		if (id == 0)
			return new Color (255,200,200);
		return new Color (200,255,200);
		
	}
	
	private void initBoardPanel (){
		int size = this.game.getBoard().getSize();
		this.boardPanel = new JPanel ();
		this.boardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.boardPanel.setLayout(new GridLayout (size, size));
		this.initBoardSquareButtons();
	}
	private void initBoardSquareButtons (){
		Board board = this.game.getBoard();
		int size = board.getSize();
		this.squareButtons = new JButton[size][];
		SquareController sc = new SquareController (this.game, this.interactionPanel, this);
		for (int i = size - 1; i >= 0; i--){
			this.squareButtons[i] = new JButton [size];
			for (int j = 0; j < size; j++){
				JButton button = new JButton ();
				String coordinates = Board.toCoordinates(j, i);
				Square square = board.getSquare(coordinates);
				
				button.setName(coordinates);
				if (square.getPlayerExit() == null)
					button.setBackground(Color.lightGray);
				else
					button.setBackground(getExitColor (square.getPlayerExit() == this.game.getPlayer(0) ? 0 : 1));
				
				button.addActionListener(sc);
				
				this.squareButtons[i][j] = button;
				this.boardPanel.add(button);
			}
		}
	}
	private void initInteractionPanel (){
		this.interactionPanel = new JPanel ();
		this.interactionPanel.setLayout(new GridLayout ());
		this.interactionPanel.add(new PlayersCreationPanel (this.game.getPlayer(0), this.game.getPlayer(1), this.game, this.interactionPanel));
	}
	private void loadIcons (){
		this.ghostIcons = new HashMap<String, Icon> ();
		for (String typeName : this.game.getFactory ().getTypes()){
			URL path = getClass().getResource("icons/" + typeName);
			if (path == null)
				throw new RuntimeException ("L'icône pour " + typeName + " n'existe pas.");
			this.ghostIcons.put(typeName , new ImageIcon(path));
		}

		URL path = getClass().getResource("icons/hidden");
		if (path == null)
			throw new RuntimeException ("L'icône de fantôme caché n'existe pas.");
		else
			this.hiddenGhostIcon = new ImageIcon (path);
	}

	public void updateDisplay (){
		Board board = this.game.getBoard();
		int size = board.getSize();
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				JButton button = this.squareButtons[i][j];
				String position = Board.toCoordinates(j, i);
				Square square = board.getSquare(position);
				Ghost ghost = square.getGhost();
				if (ghost != null){
					String typeName = this.game.getFactory ().getType(ghost);
					if (ghost.getPlayer() != this.game.getCurrentPlayer() && !this.game.isCheatModeEnabled ()){
						button.setIcon(this.hiddenGhostIcon);
						button.setBackground(Color.lightGray);
					}
					else{
						button.setIcon(this.ghostIcons.get(typeName));
						button.setBackground(ghost.isGood() ? Color.BLUE : Color.RED);
					}
				}else if (square.getPlayerExit() == null){
					button.setBackground(Color.lightGray);
					button.setIcon(null);
				}
			}
		}
	}
	
	public void nextPlayer (){
		this.interactionPanel.removeAll();
		JButton button = new JButton ("Je suis prêt !");
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				game.nextPlayer();
				//	Si on est revenu au premier joueur c'est que chacun a placé ses pions et on peut jouer !
				if (game.getCurrentPlayer() == game.getPlayer(0))
					game.setCurrentState(GameState.inTurn);
				
				updateDisplay ();
				interactionPanel.removeAll();
				interactionPanel.repaint();
			}
		});
		this.interactionPanel.add(button);
		this.interactionPanel.validate();
	}

	public JButton[][] getSquareButtons(){
		return this.squareButtons;
	}
}