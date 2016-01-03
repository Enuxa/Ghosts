package core;
import java.util.*;

/**
*	Classe correspondant au livre de règles.
*/
public class RuleBook{
	private RuleSet<InitializationRule> initializationRules;
	private RuleSet<MovementRule> movementRules;
	private RuleSet<GameEndRule> gameEndRules;
	private RuleSet<CaptureRule> captureRules;
	public RuleBook (){
		this.movementRules = new RuleSet<MovementRule> ();
		this.gameEndRules = new RuleSet<GameEndRule> ();
		this.initializationRules = new RuleSet<InitializationRule> ();
		this.captureRules = new RuleSet<CaptureRule> ();
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
		Collection<InitializationRule> top = this.initializationRules.getTopRules();
		for (InitializationRule rule : top)
			if (!rule.requestInitialization(player, ghost, position))
				return false;
		return true;
	}

	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestInitialization (Player player, String position){
		Collection<InitializationRule> top = this.initializationRules.getTopRules();
		for (InitializationRule rule : top)
			if (!rule.requestInitialization(player, position))
				return false;
		return true;
	}
	/**
	*	Indique si un déplacement est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestMovement (Player player, String squareA, String squareB){
		Collection<MovementRule> top = this.movementRules.getTopRules();
		for (MovementRule rule : top)
			if (!rule.requestMovement(player, squareA, squareB))
				return false;
		
		return true;
	}
	/**
	*	Indique si la partie est terminée ou non.
	*	@return	<code>true</code> si la partie est finie, <code>false</code> sinon.
	*/
	public boolean isGameOver (){
		Collection<GameEndRule> top = this.gameEndRules.getTopRules();
		for (GameEndRule rule : top)
			if (!rule.isGameOver())
				return false;
		
		return true;
	}
	/**
	 * Récupère le gagnant de la partie.
	 * @return Instance de <code>Player</code> correspondant au gagnant de la partie, <code>null</code> si la partie n'est pas finie.
	 * @throws Rule.IncompatibleRulesException Si on a deux gagnants différents
	 */
	public Player getWinner (){
		Player player = null;
		GameEndRule r = null;
		Collection<GameEndRule> top = this.gameEndRules.getTopRules();
		for (GameEndRule rule : top){
			if (rule.isGameOver()){
				Player p = rule.getWinner();
				if (p != null && player != null && p != player)
					throw new Rule.IncompatibleRulesException("Deux gagnants différents.", r, rule);
				player = p;
				r = rule;
			}
		}
		return player;
	}
	/**
	* Renvoie un plateau
	* @return Le plateau de la partie
	* @throws Rule.IncompatibleRulesException Si on génère deux plateaux
	*/
	public Board getBoard () {
		Board board = null;
		InitializationRule r = null;
		Collection<InitializationRule> top = this.initializationRules.getTopRules();
		for (InitializationRule rule : top){
			Board b = rule.getBoard();
			if (b != null) {
				if (board != null)
					throw new Rule.IncompatibleRulesException ("Deux plateaux créés.", r, rule);
				board = b;
				r = rule;
			}
		}
		return board;
	}

	/**
	 * Indique si un joueur est prêt à jouer
	 * @param player Le joueur
	 * @return <code>true</code> si le joueur a une configuration correcte, <code>false</code> sinon.
	 */
	public boolean isReady (Player player){
		Collection<InitializationRule> rules = this.initializationRules.getTopRules();
		for (InitializationRule r : rules)
			if (!r.isReady(player))
				return false;
		return true;
	}

	/**
	 * Indique si une capture est autorisée
	 * @param player Le joueur qui souhaite capturer
	 * @param square La case où a lieu la capture
	 * @param ghost Le fantôme qui va capturer
	 * @return <code>true</code> si cette capture est autorisée, <code>false</code> sinon.
	 */
	public boolean requestCapture (Player player, String square, Ghost ghost) {
		Collection<CaptureRule> rules = this.captureRules.getTopRules();
		for (CaptureRule r : rules){
			if (!r.requestCapture(square, ghost, player))
				return false;
		}
		
		return true;
	}
}