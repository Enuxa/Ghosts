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
		boolean b = true;
		for (Rule rule : top)
			b &= ((InitializationRule)rule).requestInitialization(player, ghost, position);
		
		return b;
	}
	/**
	*	Indique si un déplacement est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestMovement (Player player, String squareA, String squareB){
		SortedSet<Rule> top = getTopRules (this.movementRules);
		boolean b = true;
		for (Rule rule : top)
			b &= ((MovementRule)rule).requestMovement(player, squareA, squareB);
		
		return b;
	}
	/**
	*	Indique si la partie est terminée ou non.
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
	 * Récupère le gagnant de la partie.
	 * @return Intance de <code>Player</code> correspondant au gagnant de la partie, <code>null</code> si la partie n'est pas finie.
	 * @throws Exception Si on a deux gagnants différents
	 */
	public Player getWinner () throws Exception{
		Player player = null;
		SortedSet<Rule> top = getTopRules (this.gameEndRules);
		for (Rule rule : top){
			Player p = ((GameEndRule)rule).getWinner();
			if (p != null){
				if (player != null){
					if (p != player)
						throw new Exception ("Règles incompatibles : Deux gagnants différents.");
				}else
					player = p;
			}
		}
		
		return player;
	}
	/**
	 * Récupère les règles de priorité maximale d'une famille de règle
	 * @param rules L'ensemble de règles de départ
	 * @return L'ensemble des règles de priorité maximale
	 */
	private SortedSet<Rule> getTopRules (SortedSet<Rule> rules){
		return rules.tailSet(rules.last());
	}

}
