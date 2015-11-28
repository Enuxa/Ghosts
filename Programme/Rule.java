/**
*	Classe correspondant à une règle.
*/
public abstract class Rule{
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public Rule (int priority){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Récupère le niveau de priorité de cette règle.
	*	@return	Le niveau de priorité de cette règle.
	*/
	public int getPriority (){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Indique si cette règle annule une règle donnée.
	*	@param	La règle à tester.
	*	@return	<code>true</code> si cette règle est prioritaire par rapport à celle passée en argument.
	*/
	public boolean prevailsOver (Rule rule){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
}
