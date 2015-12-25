package base.GUIGame;

import core.*;

import java.util.*;

public class GUIGame extends Game {
	private Player currentPlayer;
	private Window window;
	private GameState currentState;
	
	public GUIGame (boolean cheatMode, Collection<Extension> extensions){
		super(cheatMode, extensions);
		
		this.window = new Window (this);
	}
	
	public void run() {
		this.window.setVisible(true);

		this.currentState = GameState.playerInitialization;
		this.currentPlayer = this.getPlayer(0);
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
	}
}
