package core;
import java.util.*;

/**
*	Classe correspondant au joueur
*/
public class Player{
	private String name;
	private Set<Ghost> ghosts, exited, captured;
	private AutoPlay autoPlay;
	public Player (String name){
		this.name = name;
		this.ghosts = new HashSet<Ghost> ();
		this.exited = new HashSet<Ghost> ();
		this.captured= new HashSet<Ghost> ();
		this.autoPlay = null;
	}
	/**
	 * Assigne le nom du joueur
	 * @param name Le nom du joueur
	 */
	public void setName (String name){
		this.name = name;
	}
	/**
	 * Assigne un mode de jeu automatique à
	 * @param autoPlay Le mode de jeu automatique
	 */
	public void setAutoplay (AutoPlay autoPlay){
		this.autoPlay = autoPlay;
	}
	/**
	 * Récupère le mode de jeu automatique
	 * @return Le mode de jeu automatique
	 */
	public AutoPlay getAutoPlay (){
		return this.autoPlay;
	}
	/**
	 * Indique si ce joueur est automatique
	 * @return <code>true</code> si ce joueur peut jouer automatiquement
	 */
	public boolean isAuto (){
		return this.autoPlay != null;
	}
	/**
	*	Récupère l'ensemble des fantômes que le joueur a possédé au long de la partie
	*	@return	L'ensemble des fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (){
		return this.ghosts;
	}
	/**
	 * Récupère les fantômes qui ont réussi à sortir
	 * @return Les fantômes sortis
	 */
	public Collection<Ghost> getExited (){
		return this.exited;
	}
	/**
	*	Récupère seulement les bons (ou mauvais) fantômes du joueur qu'il avait au début de la partie
	*	@param	isGood	<code>true</code> si on demande les bons fantômes, <code>false</code> sinon
	*	@return	L'ensemble des bons (ou mauvais) fantômes du joueur
	*/
	public Collection<Ghost> getGhosts (boolean isGood){
		Collection<Ghost> g = new ArrayList<Ghost> ();
		for (Ghost x : this.ghosts) {
			if (x.isGood () == isGood)
				g.add (x);
		}
		return g;
	}
	
	/**
	*	Récupère seulement les fantômes du joueur correspondant à un certain type qu'il avait au début de la partie
	*	@param	type Le type des fantômes désirés
	*	@return	L'ensemble des fantômes de ce type
	*/
	public Collection<Ghost> getGhosts (String type){
		Collection<Ghost> g = new ArrayList<Ghost> ();
		for (Ghost x : this.ghosts) {
			if (x.getType().equals(type))
				g.add (x);
		}
		return g;
	}
	
	/**
	 * Récupère les fantômes encore en jeu
	 * @return Les fantômes encore en jeu
	 */
	public Collection<Ghost> getPlayingGhosts (){
		Collection<Ghost> c = new ArrayList<Ghost> ();
		for (Ghost g : this.ghosts){
			if (!this.exited.contains(g) && !this.captured.contains(g))//	Si ce fantôme n'a ni été capturé, ni sorti
				c.add(g);
		}
		
		return c;
	}
	/**
	 * Ajoute un fantôme
	 * @param ghost Le fantôme à ajouter.
	 */
	public void addGhost (Ghost ghost) {
		this.ghosts.add(ghost);
	}
	/**
	 * Le nombre initial de fantômes de ce joueur
	 * @return Le nombre initial de fantômes
	 */
	public Collection<Ghost> getCaptured (){
		return this.captured;
	}
	/**
	 * Fait sortir un fantôme
	 * @param ghost Le fantôme à faire sortir.
	 */
	public void exitGhost (Ghost ghost) {
		if (this.hasGhost(ghost)){
			this.exited.add(ghost);
		}
	}
	/**
	 * Indique qu'un fantôme a été capturé
	 * @param ghost Le fantôme à capturer
	 */
	public void captureGhost (Ghost ghost) {
		if (this.hasGhost(ghost)){
			this.captured.add(ghost);
		}
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
	 * Fait déposer les fantômes un à un au joueur jusqu'à ce que sa configuration soit correcte.
	 * @param board Le plateau de jeu
	 * @param book Le livre de règles
	 * @param inter L'interface utilisée
	 * @param ghostTypes Les types de fantômes autorisés
	 * @param cheatMode <code>true</code> si le mode triche est activé
	 */
	/**
	 * Récupère le nom du joueur.
	 * @return Le nom du joueur
	 */
	public String toString (){
		return this.name;
	}
	/**
	 * Supprime un fantôme de la liste de fantôme du joueur
	 * @param ghost Le fantôme à supprimer
	 */
	public void removeGhost (Ghost ghost) {
		this.ghosts.remove(ghost);
	}
}