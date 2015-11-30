import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
*	Classe correspondant au joueur
*/
public class Player{
	private String name;
	private Collection<Ghost> ghosts;
	/**
	*	@param	name	Nom du joueur
	*/
	public Player (String name){
		this.name = name;
	}
	/**
	*	Récupère l'ensemble des fantômes du joueur
	*	@return	L'ensemble des fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (){
		return this.ghosts;
	}
	/**
	*	Récupère seulement les bons (ou mauvais) fantômes du joueur
	*	@param	good	<code>true</code> si on demande les bons fantômes, <code>false</code> sinon
	*	@return	L'ensemble des bons (ou mauvais) fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (boolean good){
		Collection<Ghost> g = new ArrayList<Ghost> ();
		for (Ghost x : this.ghosts) {
			if (x.isGood () == good)
				g.add (x);
		}
		return g;
	}
	/**
	 * Ajoute un fantôme
	 */
	public void addGhost (Ghost ghost) {
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	 * Retire un fantôme (par exemple si ce fantôme est sorti ou qu'il s'est fait manger)
	 */
	public void removeGhost (Ghost ghost) {
		throw new UnsupportedOperationException ("Pas implémenté");
	}
}
