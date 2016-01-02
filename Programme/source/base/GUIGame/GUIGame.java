package base.GUIGame;

import core.*;

import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Classe correspondant au jeu utilisant une interface graphique.
 */
public class GUIGame extends Game {
	private Player currentPlayer;
	private Window window;
	private GameState currentState;
	private boolean hasPlayed, firstPlayerInitialized;
	
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
		this.firstPlayerInitialized = false;
		
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
	 * Assigne l'étape de jeu actuelle
	 * @param s L'étape de jeu
	 */
	public void setCurrentState (GameState s){
		this.currentState = s;
	}
	
	/**
	 * Passe au joueur suivant
	 */
	public void nextState (){
		//	On passe au joueur suivant
		if (this.currentPlayer == null)
			this.currentPlayer = this.getPlayer(0);
		else{
			this.currentPlayer = this.otherPlayer();
			if (this.currentState == GameState.playerInitialization && this.currentPlayer == this.getPlayer(0)){
				if (this.firstPlayerInitialized)
					this.firstPlayerInitialized = true;
				else
					this.currentState = GameState.inTurn;
			}
		}
		
		if (this.currentState == GameState.playersCreation)
			this.currentState = GameState.playerInitialization;
		else if (this.currentState == GameState.inTurn){
			this.updateExits();
			this.window.updateDisplay();
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
		String msg = "";
		switch (this.currentState){
			case playerInitialization :
				msg = "A " + this.currentPlayer + " de jouer !";
				break;
			case playersCreation :
				msg = "Veuillez vous identifier";
				break;
			case inTurn :
				msg = "A " + this.currentPlayer + " de placer ses pions.";
				break;
		}
		this.window.setMessage(msg);
		
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
