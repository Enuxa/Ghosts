/**
*	Classe principale
*/
public class Game{
	/**
	*	Instancie le jeu
	*	@param	inter	L'interface choisie pour cette partie
	*	@param	cheatMode	Mode triche activé ou non
	*	@param	extensions	Ensemble des extensions choisies pour cette partie
	*/
	public Game (Interface inter, boolean cheatMode, Extension[] extensions){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Démarre la partie
	*/
	public void run (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère l'instance de Game correspondant à la partie en cours
	*	@return L'instance courante de Game
	*/ 
	public static Game getCurrent (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Fait passer un tour
	*	@param	player	Le joueur dont c'est le tour
	*/
	private void turn (Player player){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Indique si la partie est terminée
	*	@return	<code>true</code> si le jeu est fini, <code>false</code> sinon.
	*/
	private boolean isGameOver (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Prépare le jeu
	*/
	private void initialize (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Prépare les fantômes du joueur indiqué
	*	@param	player	Joueur devant placer ses fantômes
	*/
	private void initialize (Player player){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère le joueur demandé
	*	@param	id	<code>0</code> pour le premier joueur, <code>1</code> pour le second.
	*	@return	Le joueur demandé
	*/
	public Player getPlayer (int id){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère l'interface choisie pour la partie en cours.
	*	@return	L'interface utilisée.
	*/
	public Interface getInterface (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère le livre de règles de la partie en cours.
	*	@return	Le livre de règles.
	*/
	public RuleBook getRuleBook (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
}
