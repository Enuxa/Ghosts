/**
*	Classe principale
*/
public class Game{
	private Interface inter;
	private boolean cheatMode;
	private Extension[] extensions;
	private Board board;
	private RuleBook ruleBook;
	private Player player1, player2;
	private static Game current;
	/**
	*	Instancie le jeu
	*	@param	inter	L'interface choisie pour cette partie
	*	@param	cheatMode	Mode triche activé ou non
	*	@param	extensions	Ensemble des extensions choisies pour cette partie
	*/
	public Game (Interface inter, boolean cheatMode, Extension[] extensions, String nom1, String nom2){
		this.inter = inter;
		this.cheatMode = cheatMode;
		this.extensions = extensions;
		this.board = null;
		this.ruleBook = new RuleBook();
		this.player1 = new Player (nom1);
		this.player2 = new Player (nom2);
		current = this;
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
		if (id == 0)
			return player1;
		return player2;
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
		return current;
	}
	/**
	*	Démarre la partie
	*/
	public void run (){
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	*	Fait passer un tour
	*	@param	player	Le joueur dont c'est le tour
	*/
	private void turn (Player player){
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	*	Indique si la partie est terminée
	*	@return	<code>true</code> si le jeu est fini, <code>false</code> sinon
	*/
	private boolean isGameOver (){
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	*	Prépare le jeu
	*/
	private void initialize (){ // appeler la règle d'initialisation priorité supérieure
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	*	Prépare les fantômes du joueur indiqué
	*	@param	player	Joueur devant placer ses fantômes
	*/
	private void initialize (Player player){
		throw new UnsupportedOperationException ("Pas implémenté");
	}
}
