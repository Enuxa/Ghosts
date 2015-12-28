package base.GUIGame;

import core.*;
import java.util.*;

public class GUIGame extends Game {
	private Player currentPlayer;
	private Window window;
	private GameState currentState;
	private boolean hasPlayed;
	
	public GUIGame (boolean cheatMode, Collection<Extension> extensions){
		super(cheatMode, extensions);
	}
	
	public void run() {
		this.load();
		
		this.window = new Window (this);
		
		this.window.setVisible(true);

		this.currentState = GameState.playersCreation;
		this.currentPlayer = this.getPlayer(0);
		this.hasPlayed = false;
	}
	
	public Player getCurrentPlayer (){
		return this.currentPlayer;
	}
	
	public GameState getCurrentState (){
		return this.currentState;
	}

	public void setCurrentPlayer (Player p){
		this.currentPlayer = p;
	}
	
	public void setCurrentState (GameState s){
		this.currentState = s;
	}
	
	public void nextPlayer (){
		if (this.currentState == GameState.inTurn){
			this.updateExits(this.currentPlayer);
			if (this.isGameOver()){
				Player w = this.getRuleBook().getWinner();
				javax.swing.JOptionPane.showMessageDialog(this.window, w + " a gagné ! Bravo " + w);
			}
		}
		this.currentPlayer = this.currentPlayer == this.getPlayer(0) ? this.getPlayer(1) : this.getPlayer(0);
		
		if (this.currentPlayer.isAuto())
			this.currentPlayer.getAutoPlay().turn();
		
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
