package core;
import java.util.SortedSet;
import java.util.TreeSet;

/**
*	Classe correspondant au livre de r�gles.
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
	*	Ajoute une r�gle au livre de r�gles.
	*	@param	rule La r�gle � ajouter.
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
	*	Indique si le placement initial d'un fant�me donn� est l�gal ou non selon les r�gles choisies pour cette partie.
	*	@param	player	Le joueur pla�ant ses fant�mes.
	*	@param	ghost	Le fant�me � placer.
	*	@param	position	La position � laquelle le joueur souhaite placer son fant�me.
	*	@return	<code>true</code> si ce placement est autoris�, <code>false</code> sinon.
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
		boolean b = true;
		for (Rule rule : top)
			b &= ((MovementRule)rule).requestMovement(player, squareA, squareB);
		
		return b;
	}
	/**
	*	Indique si la partie est termin�e ou non.
	*	@return	<code>true</code> si la partie est finie, <code>false</code> sinon.
	*/
	public boolean isGameOver (){
		SortedSet<Rule> top = getTopRules (this.gameEndRules);
		boolean b = true;
		for (Rule rule : top)
			b &= ((GameEndRule)rule).isGameOver();
		
		return b;
	}
	/**
	 * R�cup�re le gagnant de la partie.
	 * @return Instance de <code>Player</code> correspondant au gagnant de la partie, <code>null</code> si la partie n'est pas finie.
	 * @throws Rule.IncompatibleRulesException Si on a deux gagnants diff�rents
	 */
	public Player getWinner () throws Rule.IncompatibleRulesException{
		Player player = null;
		GameEndRule r = null;
		SortedSet<Rule> top = getTopRules (this.gameEndRules);
		for (Rule rule : top){
			GameEndRule ger = (GameEndRule)rule;
			if (ger.isGameOver()){
				Player p = ger.getWinner();
				if (p != player)
					throw new Rule.IncompatibleRulesException("Deux gagnants diff�rents.", r, ger);
				player = p;
				r = ger;
			}
		}
		return player;
	}
	/**
	 * R�cup�re les r�gles de priorit� maximale d'une famille de r�gles
	 * @param rules L'ensemble de r�gles de d�part
	 * @return L'ensemble des r�gles de priorit� maximale
	 */
	private SortedSet<Rule> getTopRules (SortedSet<Rule> rules){
		return rules.tailSet(rules.last());
	}
	public SortedSet<Rule> getTopInitializationRules () {
		return this.getTopRules (this.initializationRules);
	}
	public SortedSet<Rule> getTopMovementRules () {
		return this.getTopRules (this.movementRules);
	}
	public SortedSet<Rule> getTopCaptureRules () {
		return this.getTopRules (this.captureRules);
	}
	public SortedSet<Rule> getTopGameEndRules () {
		return this.getTopRules (this.gameEndRules);
	}
	/**
	* Renvoie un plateau
	* @return Le plateau de la partie
	* @throws Rule.IncompatibleRulesException Si on g�n�re deux plateaux
	*/
	public Board getBoard () throws Rule.IncompatibleRulesException {
		Board board = null;
		InitializationRule r = null;
		SortedSet<Rule> top = getTopInitializationRules ();
		for (Rule rule : top){
			InitializationRule ir = (InitializationRule)rule;
			Board b = ir.getBoard();
			if (b != null) {
				if (board != null)
					throw new Rule.IncompatibleRulesException ("Deux plateaux cr��s.", r, ir);
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
		boolean b = true;
		for (Rule r : rules)
			b &= ((InitializationRule)r).isReady(player);
		return b;
	}
}