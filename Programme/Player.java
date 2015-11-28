import java.util.*;

/**
*	Classe correspondant au joueur
*/
public class Player{
	/**
	*	@param	name	Nom du joueur
	*/
	public Player (String name){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère l'ensemble des fantômes du joueur
	*	@return	L'ensemble des fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Récupère seulement les bons (ou mauvais) fantômes du joueur
	*	@param	good	<code>true</code> si on demande les bons fantômes, <code>false</code> sinon.
	*	@return	L'ensemble des bons (ou mauvais) fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (boolean good){
		throw new UnsupportedOperationException ("Pas implementé");
	}
}
