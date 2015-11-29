import java.util.*;

/**
*	Classe correspondant au joueur
*/
public class Player{
	private String name;
	private ArrayList<Ghost> ghosts;
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
	public ArrayList<Ghost> getGhosts (){
		return this.ghosts;
	}
	/**
	*	Récupère seulement les bons (ou mauvais) fantômes du joueur
	*	@param	good	<code>true</code> si on demande les bons fantômes, <code>false</code> sinon
	*	@return	L'ensemble des bons (ou mauvais) fantômes du joueur
	*/
	public ArrayList<Ghost> getGhosts (boolean good){
		ArrayList<Ghost> g = new ArrayList<Ghost> ();
		Iterator i = this.ghosts.iterator ();
		while (i.hasNext()) {
			Ghost x = ((Ghost)i.next());
			if (x.isGood () == good)
				g.add (x);
		}
		return g;
	}
}
