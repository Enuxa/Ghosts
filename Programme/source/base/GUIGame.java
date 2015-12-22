package base;

import core.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GUIGame extends Game {
	private Player currentPlayer;
	private Window window;
	private GameState currentState;
	
	public GUIGame (boolean cheatMode, Collection<Extension> extensions){
		super(cheatMode, extensions);
		
		this.window = new Window ();
	}
	
	public void run() {
		this.window.setVisible(true);

		this.currentState = GameState.playerInitialization;
		this.currentPlayer = this.getPlayer(0);
	}
	
	private class Window extends JFrame{
		private JPanel boardPanel, interactionPanel;
		private JButton[][] squareButtons;
		private Map<String, Icon> ghostIcons;
		private Icon exitIcon, hiddenGhostIcon;
		private JMenu newGhostMenu;
		
		public Window (){
			this.loadIcons();
			
			this.setTitle("Ghosts" + (isCheatModeEnabled () ? "[mode triche]" : ""));
			
			JPanel pane = (JPanel)this.getContentPane();
			pane.setLayout(new GridLayout ());
			
			this.setPreferredSize(new Dimension (1000,500));
			
			this.initBoardPanel();
			this.initInteractionPanel();
			
			//	Ajout des composants
			pane.add(this.boardPanel, BorderLayout.WEST);
			pane.add(this.interactionPanel, BorderLayout.EAST);
			this.pack();
		}
		private void initBoardPanel (){
			int size = getBoard().getSize();
			this.boardPanel = new JPanel ();
			this.boardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			this.boardPanel.setLayout(new GridLayout (size, size));
			this.initBoardSquareButtons();
		}
		private void initBoardSquareButtons (){
			Board board = getBoard();
			int size = board.getSize();
			this.squareButtons = new JButton[size][];
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
						if (square.getPlayerExit() == getPlayer(0))
							button.setBackground(new Color (255, 200, 200));
						else
							button.setBackground(new Color (200, 255, 200));
					
					button.addActionListener(new SquareController ());
					
					this.squareButtons[i][j] = button;
					this.boardPanel.add(button);
				}
			}
		}
		private void initInteractionPanel (){
			this.interactionPanel = new JPanel ();
			this.interactionPanel.setLayout(new GridLayout ());
		}
		private void loadIcons (){
			this.ghostIcons = new HashMap<String, Icon> ();
			for (String typeName : getFactory ().getTypes()){
				String[] bm = new String[]{"bon", "mauvais"};
				for (int k = 0; k < 2; k++){
					String typeNameBM = typeName + "_" + bm[k];
					URL path = getClass().getResource("../icons/" + typeNameBM);
					if (path == null)
						throw new RuntimeException ("L'icône pour " + typeName + " (" + bm[k] + ") n'existe pas.");
					this.ghostIcons.put(typeNameBM , new ImageIcon(path));
				}
			}
			URL path = getClass().getResource("../icons/exit");
			if (path == null)
				throw new RuntimeException ("L'icône de sortie n'existe pas.");
			else
				this.exitIcon = new ImageIcon (path);

			path = getClass().getResource("../icons/hidden");
			if (path == null)
				throw new RuntimeException ("L'icône de fantôme caché n'existe pas.");
			else
				this.hiddenGhostIcon = new ImageIcon (path);
		}

		private void updateDisplay (){
			Board board = getBoard();
			int size = board.getSize();
			for (int i = 0; i < size; i++){
				for (int j = 0; j < size; j++){
					JButton button = this.squareButtons[i][j];
					String position = Board.toCoordinates(j, i);
					Square square = board.getSquare(position);
					Ghost ghost = square.getGhost();
					if (ghost != null){
						String typeName = getFactory ().getType(ghost) + "_" + (ghost.isGood() ? "bon" : "mauvais" );
						if (ghost.getPlayer() != currentPlayer && !isCheatModeEnabled ())
							button.setIcon(this.hiddenGhostIcon);
						else
							button.setIcon(this.ghostIcons.get(typeName));
					}
				}
			}
		}
		
		private class SquareController implements ActionListener{
			public void actionPerformed(ActionEvent arg) {
				interactionPanel.removeAll();
				interactionPanel.repaint();
						
				String coordinates = ((JButton)arg.getSource()).getName();
				Square square = getBoard().getSquare(coordinates);
				if (currentState == GameState.playerInitialization && getRuleBook().requestInitialization(currentPlayer, coordinates)){
					if (square.getGhost() == null){
						JPanel ngp = new NewGhostPanel (coordinates);
						interactionPanel.add(ngp);
						validate();
					}
				}else if (getRuleBook ().isReady(currentPlayer))
					addNextPlayerButton ();
			}
		}
		private class NewGhostPanel extends JPanel {
			private JRadioButton good, bad;
			private JComboBox ghostTypesBox;
			private String coordinates;
			
			public NewGhostPanel (String coordinates){
				this.coordinates = coordinates;
				
				Collection<String> ghostTypes = getFactory().getTypes();
				ghostTypesBox = new JComboBox (ghostTypes.toArray(new String[ghostTypes.size()]));
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
						if (getRuleBook().requestInitialization(currentPlayer, ghost, position)){
							currentPlayer.addGhost(ghost);
							ghost.move(position);
							updateDisplay();
							interactionPanel.removeAll();
							interactionPanel.repaint();

							if (getRuleBook().isReady(currentPlayer))
								addNextPlayerButton ();
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
				return getFactory().createGhost((String)ghostTypesBox.getSelectedItem(), good.isSelected(), currentPlayer);
			}
			
			public String getPosition (){
				return this.coordinates;
			}
		}
		private void addNextPlayerButton (){
			JButton button = new JButton ("Je suis prêt !");
			button.addActionListener(new ActionListener (){
				public void actionPerformed(ActionEvent arg0) {
					if (currentPlayer == getPlayer (0))
						currentPlayer = getPlayer (1);
					else{
						currentPlayer = getPlayer (0);
						currentState = GameState.inTurn;
					}
					updateDisplay ();
					interactionPanel.removeAll();
					interactionPanel.repaint();
				}
			});
			interactionPanel.add(button);
		}
	}
	private enum GameState{
		playerInitialization,
		inTurn
	}
}
