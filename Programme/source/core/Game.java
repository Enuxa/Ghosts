package core;

import java.util.*;

/**
*	Classe correspondant à une partie, il ne peut y avoir qu'une seule partie par instance du programme.
*/
public abstract class Game{
	private boolean cheatMode;
	private Collection<Extension> extensions;
	private Board board;
	private RuleBook ruleBook;
	private Player[] players;
	private GhostFactory factory;
	private Collection<Ghost> onExits;
	private static Game current;
	/**
	*	@param	cheatMode	Mode triche activé ou non
	*	@param	extensions	Ensemble des extensions choisies pour cette partie
	*/
	public Game (boolean cheatMode, Collection<Extension> extensions){
		this.cheatMode = cheatMode;
		this.extensions = extensions;
		this.board = null;
		this.ruleBook = new RuleBook();
		this.players = new Player[]{new Player ("Joueur 1"), new Player ("Joueur 2")};
		this.factory = new GhostFactory ();
		this.onExits = new ArrayList<Ghost> ();
		Game.current = this;
	}
	/**
	 * Indique si le mode triche est activé
	 * @return <code>true</code> si le mode triche est activé, <code>false</code> sinon.
	 */
	public boolean isCheatModeEnabled (){
		return this.cheatMode;
	}
	/**
	 * Récupère la fabrique de fantômes
	 * @return La fabrique de fantômes
	 */
	public GhostFactory getFactory (){
		return this.factory;
	}
	/**
	 * Récupère le plateau
	 * @return Le plateau
	 */
	public Board getBoard () {
		return this.board;
	}
	/**
	*	Récupère le joueur demandé
	*	@param	id	<code>0</code> pour le premier joueur, <code>1</code> pour le second
	*	@return	Le joueur demandé
	*/
	public Player getPlayer (int id){
		return this.players[id];
	}
	/**
	*	Récupère le livre de règles de la partie en cours
	*	@return	Le livre de règles
	*/
	public RuleBook getRuleBook (){
		return this.ruleBook;
	}
	/**
	*	Récupère l'instance de Game correspondant à la partie en cours
	*	@return L'instance courante de Game
	*/ 
	public static Game getCurrent (){
		return Game.current;
	}
	/**
	*	Démarre la partie
	*/
	public abstract void run ();
	/**
	 * Fait sortir les fantômes
	 */
	private void makeGhostsExit (){
		//	Sortie des fantômes
		for (Ghost g : this.onExits){
			g.getPlayer().exitGhost(g);
		}
	}
	/**
	 * Fait sortir les fantômes positionnés sur une sortie et ayant survécu un tour et met en attente les nouveaux fantômes sur les sorties.
	 */
	protected void updateExits (){
		this.makeGhostsExit();
		
		//	Ajout des fantômes qui sont sur des sorties
		for (Player p : this.players){
			for (Ghost g : p.getPlayingGhosts()){
				String position = this.board.getPosition(g);
				if (this.board.canExit(position, g))
					this.onExits.add(g);
			}
		}
	}
	/**
	*	Indique si la partie est terminée
	*	@return	<code>true</code> si le jeu est fini, <code>false</code> sinon
	*/
	protected boolean isGameOver (){
		return this.ruleBook.isGameOver();
	}
	/**
	*	Charge les extensions et récupère le plateau. Doit être appelé au début de <code>run</code>
	*	<strong>Attention : Les joueurs doivent avoir déjà été assignés (méthode setPlayer)</strong>
	*/
	protected void load (){
		//	Initialiser les positions des joueurs
		for (Extension e : this.extensions)
			e.load();
		
		try{
			this.board = this.ruleBook.getBoard();
		}catch (Rule.IncompatibleRulesException e){
			System.err.println ("Le programme a rencontré une erreur : " + e.getMessage () + "\nIl est impossible de définir le plateau de jeu.");
			System.exit (-1);
		}
	}
}