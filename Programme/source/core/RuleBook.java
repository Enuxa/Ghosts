package core;
import java.util.*;

/**
*	Classe correspondant au livre de règles.
*/
public class RuleBook{
	private SortedSet<Rule> initializationRules;
	private SortedSet<Rule> movementRules;
	private SortedSet<Rule> gameEndRules;
	private SortedSet<Rule> captureRules;
	public RuleBook (){
		this.movementRules = new TreeSet<Rule> ();
		this.gameEndRules = new TreeSet<Rule> ();
		this.initializationRules = new TreeSet<Rule> ();
		this.captureRules = new TreeSet<Rule> ();
	}
	/**
	*	Ajoute une règle au livre de règles.
	*	@param	rule La règle à ajouter.
	*/
	public void addRule (Rule rule){
		if (rule instanceof InitializationRule)
			this.initializationRules.add((InitializationRule)rule);
		else if (rule instanceof MovementRule)
			this.movementRules.add((MovementRule)rule);
		else if (rule instanceof GameEndRule)
			this.gameEndRules.add((GameEndRule)rule);
		else if (rule instanceof CaptureRule)
			this.captureRules.add((CaptureRule)rule);
	}
	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	ghost	Le fantôme à placer.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestInitialization (Player player, Ghost ghost, String position){
		SortedSet<Rule> top = getTopRules (this.initializationRules);
		for (Rule rule : top)
			if (!((InitializationRule)rule).requestInitialization(player, ghost, position))
				return false;
		return true;
	}

	/**
	*	Indique si le placement initial d'un fant�me donn� est l�gal ou non selon les r�gles choisies pour cette partie.
	*	@param	player	Le joueur pla�ant ses fant�mes.
	*	@param	position	La position � laquelle le joueur souhaite placer son fant�me.
	*	@return	<code>true</code> si ce placement est autoris�, <code>false</code> sinon.
	*/
	public boolean requestInitialization (Player player, String position){
		SortedSet<Rule> top = getTopRules (this.initializationRules);
		for (Rule rule : top)
			if (!((InitializationRule)rule).requestInitialization(player, position))
				return false;
		return true;
	}
	/**
	*	Indique si un d�placement est l�gal ou non selon les r�gles choisies pour cette partie.
	*	@param	player	Le joueur pla�ant ses fant�mes.
	*	@param	squareA	La position du fant�me a d�placer.
	*	@param	squareB	La position � laquelle placer le fant�me.
	*	@return	<code>true</code> si ce d�placement est autoris�, <code>false</code> sinon.
	*/
	public boolean requestMovement (Player player, String squareA, String squareB){
		SortedSet<Rule> top = getTopRules (this.movementRules);
		for (Rule rule : top)
			if (!((MovementRule)rule).requestMovement(player, squareA, squareB))
				return false;
		
		return true;
	}
	/**
	*	Indique si la partie est termin�e ou non.
	*	@return	<code>true</code> si la partie est finie, <code>false</code> sinon.
	*/
	public boolean isGameOver (){
		SortedSet<Rule> top = getTopRules (this.gameEndRules);
		for (Rule rule : top)
			if (!((GameEndRule)rule).isGameOver())
				return false;
		
		return true;
	}
	/**
	 * R�cup�re le gagnant de la partie.
	 * @return Instance de <code>Player</code> correspondant au gagnant de la partie, <code>null</code> si la partie n'est pas finie.
	 * @throws Rule.IncompatibleRulesException Si on a deux gagnants différents
	 */
	public Player getWinner (){
		Player player = null;
		GameEndRule r = null;
		SortedSet<Rule> top = getTopRules (this.gameEndRules);
		for (Rule rule : top){
			GameEndRule ger = (GameEndRule)rule;
			if (ger.isGameOver()){
				Player p = ger.getWinner();
				if (p != null && player != null && p != player)
					throw new Rule.IncompatibleRulesException("Deux gagnants différents.", r, ger);
				player = p;
				r = ger;
			}
		}
		return player;
	}
	/**
	 * Récupère les règles de priorité maximale d'une famille de règles
	 * @param rules L'ensemble de règles de départ
	 * @return L'ensemble des règles de priorité maximale
	 */
	private SortedSet<Rule> getTopRules (SortedSet<Rule> rules){
		return rules.tailSet(rules.last());
	}
	/**
	 * Récupère les règles d'initialisation de priorité maximale
	 * @return L'ensemble des règles d'initialisation de priorité maximale
	 */
	public SortedSet<Rule> getTopInitializationRules () {
		return this.getTopRules (this.initializationRules);
	}
	/**
	 * Récupère les règles de mouvement de priorité maximale
	 * @return L'ensemble des règles de mouvement de priorité maximale
	 */
	public SortedSet<Rule> getTopMovementRules () {
		return this.getTopRules (this.movementRules);
	}
	/**
	 * Récupère les règles de capture de priorité maximale
	 * @return L'ensemble des règles d'initialisation de priorité maximale
	 */
	public SortedSet<Rule> getTopCaptureRules () {
		return this.getTopRules (this.captureRules);
	}
	/**
	 * Récupère les règles de fin de jeu de priorité maximale
	 * @return L'ensemble des règles de fin de jeu de priorité maximale
	 */
	public SortedSet<Rule> getTopGameEndRules () {
		return this.getTopRules (this.gameEndRules);
	}
	/**
	* Renvoie un plateau
	* @return Le plateau de la partie
	* @throws Rule.IncompatibleRulesException Si on génère deux plateaux
	*/
	public Board getBoard () {
		Board board = null;
		InitializationRule r = null;
		SortedSet<Rule> top = getTopInitializationRules ();
		for (Rule rule : top){
			InitializationRule ir = (InitializationRule)rule;
			Board b = ir.getBoard();
			if (b != null) {
				if (board != null)
					throw new Rule.IncompatibleRulesException ("Deux plateaux créés.", r, ir);
				board = b;
				r = ir;
			}
		}
		return board;
	}

	/**
	 * Indique si un joueur est pr�t � jouer
	 * @param player Le joueur
	 * @return <code>true</code> si le joueur a une configuration correcte, <code>false</code> sinon.
	 */
	public boolean isReady (Player player){
		SortedSet<Rule> rules = this.getTopInitializationRules();
		for (Rule r : rules)
			if (!((InitializationRule)r).isReady(player))
				return false;
		return true;
	}
}