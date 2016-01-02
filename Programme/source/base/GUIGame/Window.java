package base.GUIGame;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import core.*;

@SuppressWarnings("serial")
public class Window extends JFrame{
	private JPanel boardPanel, gamePanel, interactionPanel, outerInteractionPanel;
	private JLabel interactionMessage;
	private JButton[][] squareButtons;
	private Map<String, Icon> ghostIcons;
	private Icon hiddenGhostIcon;
	private GUIGame game;
	private Color defaultColor, goodColor, badColor, exit0Color, exit1Color;
	
	/**
	 * @param game L'instance du jeu en cours
	 */
	public Window (GUIGame game){
		this.game = game;

		//	Assignation des couleurs
		this.defaultColor = Color.lightGray;
		this.goodColor = Color.cyan;
		this.badColor = new Color (180, 0, 0);
		this.exit0Color = new Color (255, 200, 200);
		this.exit1Color = new Color (200, 255, 200);
		
		JPanel pane = (JPanel)this.getContentPane();
		pane.setLayout(new GridLayout ());
		
		this.setTitle("Ghosts" + (this.game.isCheatModeEnabled () ? "[mode triche]" : ""));
		this.setPreferredSize(new Dimension (1000,500));
		
		this.outerInteractionPanel = new JPanel ();
		this.interactionMessage = new JLabel ("", JLabel.CENTER);
		this.interactionPanel = new JPanel ();
		this.gamePanel = new JPanel ();
		this.boardPanel = new JPanel ();
		
		this.outerInteractionPanel.setLayout(new BorderLayout ());
		this.outerInteractionPanel.add(this.interactionMessage, BorderLayout.PAGE_START);
		this.outerInteractionPanel.add(this.interactionPanel);
		
		this.loadIcons();
		this.initGamePanel();
		this.initInteractionPanel();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//	Ajout des composants
		pane.add(this.gamePanel, BorderLayout.WEST);
		pane.add(outerInteractionPanel, BorderLayout.EAST);
		
		this.pack();
	}
	
	/*
	 * Initialise le paneau contenant les cases
	 */
	private void initGamePanel (){
		int size = this.game.getBoard().getSize();
		this.boardPanel = new JPanel ();
		
		this.gamePanel.setLayout(new BorderLayout ());
		
		this.initBoardSquareButtons();
		
		this.addCoordinatesPanel(0, size);
		this.gamePanel.add(this.boardPanel);
		this.addCoordinatesPanel(1, size);
		
		this.boardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.boardPanel.setLayout(new GridLayout (size, size));
	}
	
	/**
	 * Crée un panneau de coordonnées (Nombres verticalement, lettres horizontalement) et l'ajoute au panneau de jeu
	 * @param k <code>0</code> pour les nombres, <code>1</code> pour les lettres
	 * @param size La taille du plateau
	 */
	private void addCoordinatesPanel (int k, int size){
		//	Barre d'affichages des coordonées numériques
		JPanel coordinatesPanel = new JPanel ();
		if (k == 0){
			coordinatesPanel.setMaximumSize(new Dimension (40, this.getMaximumSize().height));
			coordinatesPanel.setLayout(new GridLayout (size, 1));
		}else{
			coordinatesPanel.setMaximumSize(new Dimension (this.getMaximumSize().width, 40));
			coordinatesPanel.setLayout(new GridLayout (1, size));
		}
		
		for (int i = 0; i < size; i++)
			coordinatesPanel.add(new JLabel (k == 0 ? Integer.toString(size - i) : Character.toString((char)('A' + i)), JLabel.CENTER));
		
		this.gamePanel.add(coordinatesPanel, k == 0 ? BorderLayout.LINE_START : BorderLayout.SOUTH);
	}
	
	/*
	 * Initialise les boutons associés aux cases
	 */
	private void initBoardSquareButtons (){
		Board board = this.game.getBoard();
		int size = board.getSize();
		this.squareButtons = new JButton[size][];
		SquareController sc = new SquareController (this.game, this.interactionPanel, this);

		/*	On ajoute les cases de gauche à droite, puis de haut en bas.
		 * Les boucles s'écrivent en sens inverse : d'abord la boucle verticale, puis la boucle horizontale.
		 * A noter que les cases de coordonnée numérique élevée sont la plus en haut dans la fenêtre, donc doivent être ajoutées en premier.
		 */
		for (int i = size - 1; i >= 0; i--){
			this.squareButtons[i] = new JButton [size];
			for (int j = 0; j < size; j++){
				JButton button = new JButton ();
				String coordinates = Board.toCoordinates(j, i);
				Square square = board.getSquare(coordinates);
				
				button.setName(coordinates);

				//	Si cette case est une sortie
				if (square.getPlayerExit() != null)
					button.setBackground(this.getColorExit(square.getPlayerExit()));
				else
					button.setBackground(this.defaultColor);
				
				button.addActionListener(sc);
				
				this.squareButtons[i][j] = button;	//	On mémorise dans le tableau ce bouton
				this.boardPanel.add(button);		//	Puis on l'ajoute au panneau
			}
		}
	}
	/**
	 * Récupère la couleur de la sortie associée à un joueur
	 * @param player Le joueur pouvant sortir
	 * @return La couleur
	 */
	private Color getColorExit (Player player){
		return player == this.game.getPlayer(0) ? this.exit0Color : this.exit1Color;
	}
	/**
	 * Initialise le panneau d'interaction.
	 * Il contient initialement le panneau de création des joueurs
	 */
	private void initInteractionPanel (){
		this.interactionPanel.setLayout(new GridLayout ());
		this.interactionPanel.add(new PlayersCreationPanel (this.game.getPlayer(0), this.game.getPlayer(1), this.game, this.interactionPanel, this));
	}
	/**
	 * Charge les icones se trouvant dans "./icons/" relativement au fichier Winodw.class
	 */
	private void loadIcons (){
		this.ghostIcons = new HashMap<String, Icon> ();
		
		//	Pour chaque type de fantôme existant
		for (String typeName : this.game.getFactory ().getTypes()){
			String pathString = "./icons/" + typeName;
			URL path = getClass().getResource(pathString);	//	On récupère le chemin de l'icone de ce fantôme
			//	Si ce fichier n'existe pas
			if (path == null)
				throw new RuntimeException ("L'icône pour " + typeName + " est introuvable à l'adresse \"" + pathString + "\" relativement à celle de la classe " + getClass().getName());
			this.ghostIcons.put(typeName , new ImageIcon(path));
		}

		//	Icône de fantôme caché
		URL path = getClass().getResource("icons/hidden");
		if (path == null)
			throw new RuntimeException ("L'icône de fantôme caché n'existe pas.");
		else
			this.hiddenGhostIcon = new ImageIcon (path);
	}

	/**
	 * Affiche en bleu les cases disponibles pour le joueur en cours
	 */
	public void displayAvailableSquares (){
		for (JButton[] tab : this.squareButtons){
			for (JButton b : tab){
				if (this.game.getRuleBook().requestInitialization(this.game.getCurrentPlayer(), b.getName()))
					b.setBackground(Color.BLUE);
			}
		}
	}
	
	/*
	 * Actualise l'affichage du plateau
	 */
	public void updateDisplay (){
		Board board = this.game.getBoard();
		int size = board.getSize();
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				JButton button = this.squareButtons[i][j];
				String position = Board.toCoordinates(j, i);
				Square square = board.getSquare(position);
				Ghost ghost = square.getGhost();
				
				//	Si cette case possède un fantôme
				if (ghost != null){
					String typeName = this.game.getFactory ().getType(ghost);
					
					//	Si ce fantôme n'appartient pas au joueur courant ET si le mode triche est désactivé : on cache le fantôme
					if (ghost.getPlayer() != this.game.getCurrentPlayer() && !this.game.isCheatModeEnabled ()){
						button.setIcon(this.hiddenGhostIcon);
						button.setBackground(this.defaultColor);
					}
					//	Sinon on affiche le fantôme
					else{
						button.setIcon(this.ghostIcons.get(typeName));
						button.setBackground(ghost.isGood() ? this.goodColor : this.badColor);
					}
				//	Si cette case ne possède pas de fantôme
				}else{
					button.setIcon(null);
					Player player = square.getPlayerExit();
					if (player == null)
						button.setBackground(this.defaultColor);
					else
						button.setBackground(this.getColorExit(player));
				}
			}
		}

		if (this.game.getCurrentState() == GameState.playerInitialization)
			this.displayAvailableSquares();
	}
	
	/**
	 * Assigne le message d'interaction
	 * @param msg Le message à afficher
	 */
	public void setMessage (String msg){
		interactionMessage.setText(msg);
	}
	
	/**
	 * Ajoute le bouton pour passer au joueur suivant
	 */
	public void nextPlayer (){
		this.interactionPanel.removeAll();
		
		JButton button = new JButton ("Je suis prêt !");
		
		//	Action à effectuer lorsque l'on clique sur le bouton "Je suis prêt !"
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				interactionPanel.removeAll();
				interactionPanel.repaint();
				game.nextState();
				updateDisplay ();
			}
		});
		
		this.interactionPanel.add(button);
		this.interactionPanel.validate();
	}

	/**
	 * Récupère les boutons associés au cases
	 * @return Les boutons
	 */
	public JButton[][] getSquareButtons(){
		return this.squareButtons;
	}
}