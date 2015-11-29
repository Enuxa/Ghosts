/**
*	Classe correspondant à une règle.
*/
public abstract class Rule implements Comparable<Rule>{
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	private int priority;
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
	*	@param	La règle à tester.
	*	@return	<code>true</code> si cette règle est prioritaire par rapport à celle passée en argument.
	*/
	public boolean prevailsOver (Rule rule){
		return this.compareTo(rule) >= 0;
	}
	/**
	 * Récupère la différence de priorité entre ces règles.
	 * @param	rule	La règle à comparer
	 */
	public int compareTo (Rule rule){
		return this.priority - rule.priority;
	}
}
