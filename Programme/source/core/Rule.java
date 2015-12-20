package core;
/**
*	Classe correspondant � une r�gle.
*/
public abstract class Rule implements Comparable<Rule>{
	private int priority;
	/**
	*	@param	priority	Niveau de priorit� de la r�gle.
	*/
	public Rule (int priority){
		this.priority = priority;
	}
	/**
	*	R�cup�re le niveau de priorit� de cette r�gle.
	*	@return	Le niveau de priorit� de cette r�gle.
	*/
	public int getPriority (){
		return this.priority;
	}
	/**
	*	Indique si cette r�gle annule une r�gle donn�e.
	*	@param	rule La r�gle � tester.
	*	@return	<code>true</code> si cette r�gle est prioritaire par rapport � celle pass�e en argument.
	*/
	public boolean prevailsOver (Rule rule){
		return this.compareTo(rule) >= 0;
	}
	/**
	 * R�cup�re la différence de priorit� entre ces r�gles.
	 * @param rule La r�gle � comparer
	 */
	public int compareTo (Rule rule){
		return this.priority - rule.priority;
	}

	@SuppressWarnings("serial")
	public static class IncompatibleRulesException extends RuntimeException{
		/**
		 * @param msg Pr�cision sur l'incompatibilit�.
		 * @param r1 R�gle incompatible avec <code>r2</code>.
		 * @param r2 R�gle incompatible avec <code>r1</code>.
		 */
		public IncompatibleRulesException (String msg, Rule r1, Rule r2){
			super ("R�gles incompatibles : " + msg + "\n" + r1 + " en conflit avec " + r2);
		}
	}
}