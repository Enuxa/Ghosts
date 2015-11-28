/**
*	Classe correspondant au livre de règles.
*/
public class RuleBook{
	/**
	*	Ajoute une règle au livre de règles.
	*	@param	rule	La règle à ajouter.
	*/
	public void addRule (Rule rule){
	}
	/**
	*	Indique si le placement initial d'un fantôme donné est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	ghost	Le fantôme à placer.
	*	@param	position	La position à laquelle le joueur souhaite placer son fantôme.
	*	@return	<code>true</code> si ce placement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestInitialization (Player player, Ghost ghost, String position){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Indique si un déplacement est légal ou non selon les règles choisies pour cette partie.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestMovement (Player player, String squareA, String squareB){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}
	/**
	*	Indique si la partie est terminée ou non.
	*	@return	<code>true</code> si la partie est finie, <code>false</code> sinon.
	*/
	public boolean isGameOver (){
		throw new UnsupportedOperationException ("Pas encore implémenté");
	}

}
