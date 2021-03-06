package core;
/**
*	Classe correspondant à une règle.
*/
public abstract class Rule {
	private int priority;
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public Rule (int priority){
		this.priority = priority;
	}
	/**
	*	Récupère le niveau de priorité de cette règle.
	*	@return	Le niveau de priorité de cette règle.
	*/
	public int getPriority (){
		return this.priority;
	}
	/**
	*	Indique si cette règle annule une règle donnée.
	*	@param	rule La règle à tester.
	*	@return	<code>true</code> si cette règle est prioritaire par rapport à celle passée en argument.
	*/
	public boolean prevailsOver (Rule rule){
		return this.priority - rule.priority > 0;
	}

	public boolean samePriority (Rule rule){
		return rule.priority == this.priority;
	}
	
	@SuppressWarnings("serial")
	public static class IncompatibleRulesException extends RuntimeException{
		/**
		 * @param msg Précision sur l'incompatibilité.
		 * @param r1 Règle incompatible avec <code>r2</code>.
		 * @param r2 Règle incompatible avec <code>r1</code>.
		 */
		public IncompatibleRulesException (String msg, Rule r1, Rule r2){
			super ("Règles incompatibles : " + msg + "\n" + r1 + " en conflit avec " + r2);
		}
	}
}