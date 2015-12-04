import java.util.*;

/**
*	Classe principale
*/
public class Game{
	private Interface inter;
	private boolean cheatMode;
	private Collection<Extension> extensions;
	private Board board;
	private RuleBook ruleBook;
	private Player[] players;
	private static Game current;
	/**
	*	Instancie le jeu
	*	@param	inter	L'interface choisie pour cette partie
	*	@param	cheatMode	Mode triche activé ou non
	*	@param	extensions	Ensemble des extensions choisies pour cette partie
	*/
	public Game (Interface inter, boolean cheatMode, Collection<Extension> extensions){
		this.inter = inter;
		this.cheatMode = cheatMode;
		this.extensions = extensions;
		this.board = null;
		this.ruleBook = new RuleBook();
		this.players = new Player[2];
		Game.current = this;
	}
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
	*	Récupère l'interface choisie pour la partie en cours
	*	@return	L'interface utilisée
	*/
	public Interface getInterface (){
		return this.inter;
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
	public void run (){
		this.initialize();
		while (!this.isGameOver()) {
			for (Player p : this.players){
				this.inter.updateDisplay(this.cheatMode ? null : p);
				p.turn();
				this.inter.printText("Au joueur suivant ! Le dernier joueur est prié de quitter la pièce, ou de se bander les yeux !", null);
			}
		}
	}
	/**
	*	Indique si la partie est terminée
	*	@return	<code>true</code> si le jeu est fini, <code>false</code> sinon
	*/
	private boolean isGameOver (){
		return this.ruleBook.isGameOver();
	}
	/**
	*	Prépare le jeu
	*/
	private void initialize (){
		//	Initialiser les positions des joueurs
		for (Extension e : this.extensions)
			e.load();
		
		try{
			this.board = this.ruleBook.getBoard();
		}catch (Rule.IncompatibleRulesException e){
			System.out.println ("Le programme a rencontré une erreur : " + e.getMessage () + "\nIl est impossible de définir le plateau de jeu.");
			System.exit (-1);
		}
		
		this.initializePlayers ();
	}
	private void initializePlayers () {
		for (int i = 0; i < 2; i++)
			this.players[i] = this.initializePlayer ();
		
		for (Player p : this.players)
			p.initialize ();
	}
	private Player initializePlayer (){
		Collection<String> choicePlayer = Arrays.asList (new String[] {"Humain"});

		String nature = null;
		while (nature == null)
			nature = this.inter.readSelection(choicePlayer, "Quelle est la nature de ce joueur ?");
		String name = null;
		while (name == null)
			name = this.inter.readText("Comment ce joueur s'appelle-t-il ?");
		
		switch (nature){
			case "Humain" : 
				return new Player (name);
			//	default n'arrivant jamais
			default :
				return null;
		}
	}
}
