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
	private JPanel boardPanel, interactionPanel;
	private JButton[][] squareButtons;
	private Map<String, Icon> ghostIcons;
	private Icon hiddenGhostIcon;
	private GUIGame game;
	
	/**
	 * @param game L'instance du jeu en cours
	 */
	public Window (GUIGame game){
		this.game = game;

		JPanel pane = (JPanel)this.getContentPane();
		pane.setLayout(new GridLayout ());
		
		this.setTitle("Ghosts" + (this.game.isCheatModeEnabled () ? "[mode triche]" : ""));
		this.setPreferredSize(new Dimension (1000,500));
		
		this.loadIcons();
		this.initInteractionPanel();
		this.initBoardPanel();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//	Ajout des composants
		pane.add(this.boardPanel, BorderLayout.WEST);
		pane.add(this.interactionPanel, BorderLayout.EAST);
		
		this.pack();
	}
	
	/**
	 * Récupère la couleur de sortie pour un joueur donné
	 * @param id L'indice du joueur
	 * @return La couleur de sortie
	 */
	public static Color getExitColor (int id){
		if (id == 0)
			return new Color (255,200,200);
		return new Color (200,255,200);
		
	}
	
	/*
	 * Initialise le paneau contenant les cases
	 */
	private void initBoardPanel (){
		int size = this.game.getBoard().getSize();
		this.boardPanel = new JPanel ();
		this.boardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.boardPanel.setLayout(new GridLayout (size, size));
		this.initBoardSquareButtons();
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
					button.setBackground(getExitColor (square.getPlayerExit() == this.game.getPlayer(0) ? 0 : 1));
				else
					button.setBackground(Color.lightGray);
				
				button.addActionListener(sc);
				
				this.squareButtons[i][j] = button;	//	On mémorise dans le tableau ce bouton
				this.boardPanel.add(button);		//	Puis on l'ajoute au panneau
			}
		}
	}
	/**
	 * Initialise le panneau d'interaction.
	 * Il contient initialiement le panneau de création les joueurs
	 */
	private void initInteractionPanel (){
		this.interactionPanel = new JPanel ();
		this.interactionPanel.setLayout(new GridLayout ());
		this.interactionPanel.add(new PlayersCreationPanel (this.game.getPlayer(0), this.game.getPlayer(1), this.game, this.interactionPanel));
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

	/*
	 * Acyualise l'affichage du plateau
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
					
					//	Si ce fantôme n'apprtient pas au joueur courant ET si le mode triche est désactivé : on cache le fantôme
					if (ghost.getPlayer() != this.game.getCurrentPlayer() && !this.game.isCheatModeEnabled ()){
						button.setIcon(this.hiddenGhostIcon);
						button.setBackground(Color.lightGray);
					}
					//	Sinon on affiche le fantôme
					else{
						button.setIcon(this.ghostIcons.get(typeName));
						button.setBackground(ghost.isGood() ? Color.CYAN : new Color (180, 0, 0));
					}
				//	Si cette case ne possède pas de fantôme
				}else if (square.getPlayerExit() == null){
					button.setBackground(Color.lightGray);
					button.setIcon(null);
				}
			}
		}
	}
	
	/**
	 * Passer au joueur suivant
	 */
	public void nextPlayer (){
		this.interactionPanel.removeAll();
		
		JButton button = new JButton ("Je suis prêt !");
		
		//	Action à effectuer lorsque l'on clique sur le bouton "Je suis prêt !"
		button.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg0) {
				game.nextPlayer();
				//	Si pendant l'initialisation, on est revenu au premier joueur, c'est que chacun a placé ses pions et on peut jouer !
				if (game.getCurrentPlayer() == game.getPlayer(0) && game.getCurrentState() == GameState.playerInitialization)
					game.setCurrentState(GameState.inTurn);
				
				updateDisplay ();
				
				interactionPanel.removeAll();
				interactionPanel.repaint();
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