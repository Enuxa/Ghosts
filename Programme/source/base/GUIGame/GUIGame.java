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
		
		this.window = new Window (this);
		
		this.currentState = GameState.playersCreation;
		this.currentPlayer = this.getPlayer(0);
		this.hasPlayed = false;
		
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
	public void nextPlayer (){
		//	Si on est en train de jouer, on met à jour les sorties (c'est à dire faire sortir les fantômes qui sont restés un tour sur une sortie)
		if (this.currentState == GameState.inTurn){
			this.updateExits(this.currentPlayer);
			this.window.updateDisplay();
			//	Si le jeu est fini
			if (this.isGameOver()){
				Player w = this.getRuleBook().getWinner();
				if (w != null)
					javax.swing.JOptionPane.showMessageDialog(this.window, w + " a gagné ! Bravo " + w + " !");
				else
					javax.swing.JOptionPane.showMessageDialog(this.window, "Match nul !");
				this.window.dispatchEvent(new WindowEvent (this.window, WindowEvent.WINDOW_CLOSING));
			}
		}
		//	On passe au joueur suivant
		this.currentPlayer = this.currentPlayer == this.getPlayer(0) ? this.getPlayer(1) : this.getPlayer(0);
		
		//	Si le nouveau joueur est automatique, on le fait jouer puis on passe au prochain joueur
		if (this.currentPlayer.isAuto()){
			this.currentPlayer.getAutoPlay().turn();
			this.window.updateDisplay();
			this.nextPlayer();
		}
		
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
}
