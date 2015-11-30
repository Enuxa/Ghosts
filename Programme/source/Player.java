import java.util.*;

/**
*	Classe correspondant au joueur <strong>humain</strong>
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
	 * Retire un fantôme (par exemple si ce fantôme est sorti ou s'il s'est fait manger)
	 */
	public void removeGhost (Ghost ghost) {
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	 * Indique si un fantôme appartient à ce joueur.
	 * @param ghost Le fantôme à tester.
	 * @return <code>true</code> si ce fantôme appartient à ce joueur, <code>false</code> sinon.
	 */
	public boolean hasGhost (Ghost ghost){
		return this.ghosts.contains(ghost);
	}
	/**
	*	Prépare les fantômes du joueur indiqué
	*/
	public void initialize (){
		//	Tant que le joueur n'est pas prêt à jouer selon le livre de règles : on le laisse soumettre des demandes d'initialisation au livre de règles.
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	 * Fait passer un tour pour ce joueur.
	 */
	public void turn (){
		throw new UnsupportedOperationException ("Pas implémenté");		
	}
	/**
	 * Récupère le nom du joueur.
	 * @return Le nom du joueur
	 */
	public String toString (){
		return this.name;
	}
}
