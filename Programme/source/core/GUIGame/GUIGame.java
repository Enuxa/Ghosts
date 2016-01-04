package core.GUIGame;

import core.*;

import java.util.*;

/**
 * Classe correspondant au jeu utilisant une interface graphique.
 */
public class GUIGame extends Game {
	private Player currentPlayer;
	private Window window;
	private GameState currentState;
	private boolean hasPlayed;
	
	/**
	 * @param cheatMode <code>true</code> si on joue cette partie en mode triche
	 * @param extensions Les extensions choisies pour cette partie 
	 */
	public GUIGame (boolean cheatMode, Collection<Extension> extensions){
		super(cheatMode, extensions);
	}
	
	public void run() {
		this.load();
		
		this.currentState = GameState.playersCreation;
		this.currentPlayer = null;
		
		this.window = new Window (this);
		this.window.setVisible(true);
	}
	
	/**
	 * Récupère le joueur en cours
	 * @return Le joueur en train de jouer
	 */
	public Player getCurrentPlayer (){
		return this.currentPlayer;
	}
	
	/**
	 * Récupère l'étape actuelle de jeu
	 * @return L'étape courante de jeu
	 */
	public GameState getCurrentState (){
		return this.currentState;
	}
	
	/**
	 * Passe au joueur suivant
	 */
	private void nextPlayer (){
		//	Si on n'a aucun joueur actuel
		if (this.currentPlayer == null){
			this.currentPlayer = this.getPlayer(0);
		}else{
			this.currentPlayer = this.otherPlayer();//	On passe au joueur suivant
			//	Si on est en cours d'initialisation et que c'est au tour du premier joueur, c'est que tout le monde est prêt à jouer !
			if (this.currentState == GameState.playerInitialization && this.currentPlayer == this.getPlayer(0))
				this.currentState = GameState.inTurn;
		}
	}
	
	/**
	 * Récupère le message à afficher dans le panneau d'interaction
	 * @return Le message à afficher
	 */
	private String getMessage (){
		switch (this.currentState){
			case inTurn :
				return "A " + this.currentPlayer + " de jouer !";
			case playersCreation :
				return "Veuillez vous identifier";
			case playerInitialization :
				return "A " + this.currentPlayer + " de placer ses pions.";
			default :
				return "";
		}
	}
	
	/**
	 * Passe à l'étape de jeu suivante
	 */
	public void nextStep (){
		//	On passe au joueur suivant
		this.nextPlayer();
		
		//	Si on était en cours de création des joueurs, on passe à leur initialisation
		if (this.currentState == GameState.playersCreation)
			this.currentState = GameState.playerInitialization;
		//	Si on est cours de partie
		else if (this.currentState == GameState.inTurn){
			this.updateExits();				//	On met à jour les sorties
			this.window.updateDisplay();	//	On met à jour l'affichage
			//	Si le jeu est fini
			if (this.isGameOver()){
				Player w = this.getRuleBook().getWinner();
				if (w != null)
					javax.swing.JOptionPane.showMessageDialog(this.window, w + " a gagné ! Bravo " + w + " !");
				else
					javax.swing.JOptionPane.showMessageDialog(this.window, "Match nul !");
				System.exit(0);
			}
		}
		
		//	Si le joueur actuel est automatique
		if (this.currentPlayer.isAuto()){
			AutoPlay ap = this.currentPlayer.getAutoPlay();
			
			if (this.currentState == GameState.playerInitialization)
				ap.initialize();
			else if (this.currentState == GameState.inTurn){
				ap.turn();
				this.hasPlayed = true;
			}
			
			this.window.updateDisplay();
			this.window.nextPlayer();
			
			return;
		}else{
			if (this.currentState == GameState.playerInitialization)
				this.window.displayAvailableSquares();
		}
		
		//	Mise a jour du message d'interaction		
		this.window.setMessage(this.getMessage());
		
		//	Le nouveau joueur actuel n'a pas joué
		this.hasPlayed = false;
	}
	/**
	 * Indique au jeu si le joueur a joué
	 * @param b L'état du tour
	 */
	public void setHasPlayed (boolean b){
		this.hasPlayed = b;
	}
	/**
	 * Indique sile joueur actuel a joué
	 * @return <code>true</code> si le joueur actuel a joué.
	 */
	public boolean hasPlayed (){
		return this.hasPlayed;
	}
	
	/**
	 * Récupère le joueur qui n'est pas en trai de jouer
	 * @return Le joueur
	 */
	private Player otherPlayer (){
		return this.currentPlayer == this.getPlayer(0) ? this.getPlayer(1) : this.getPlayer(0);
	}
}
