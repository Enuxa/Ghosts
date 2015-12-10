package core;
import java.util.*;

/**
*	Classe correspondant au joueur <strong>humain</strong>
*/
public class Player{
	private String name;
	private Set<Ghost> ghosts, exited, captured;
	/**
	*	@param	name	Nom du joueur
	*/
	public Player (String name){
		this.name = name;
		this.ghosts = new HashSet<Ghost> ();
		this.exited = new HashSet<Ghost> ();
		this.captured= new HashSet<Ghost> ();
	}
	/**
	*	Récupère l'ensemble des fantômes restant du joueur
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
	 * @param ghost Le fantôme à ajouter.
	 */
	public void addGhost (Ghost ghost) {
		this.ghosts.add(ghost);
	}
	/**
	 * Le nombre initial de fantômes de ce joueur
	 * @return Le nombre initial de fantômes
	 */
	public Set<Ghost> getCaptured (){
		return this.captured;
	}
	/**
	 * Fait sortir un fantôme
	 * @param ghost Le fantôme à faire sortir.
	 */
	public void exitGhost (Ghost ghost) {
		if (this.hasGhost(ghost)){
			this.captured.add(ghost);
		}
	}
	/**
	 * Indique qu'un fantôme a été capturé
	 * @param ghost le fantôme à capturer
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
	*	Prépare les fantômes du joueur indiqué
	*/
	public void initialize (){
		RuleBook book = Game.getCurrent().getRuleBook();
		Interface inter = Game.getCurrent().getInterface();
		Collection<String> ghostTypes = Game.getCurrent().getFactory().getTypes();
		Board board = Game.getCurrent().getBoard();
		boolean sure = false;
		
		while (!sure){
			this.initializeGhosts(board, book, inter, ghostTypes);
			sure = this.isSureOfInitialization (board, book, inter);
		}
	}
	/**
	 * Fais déposer les fantômes un à un au joueur jusqu'à ce que sa configuration soit correcte.
	 * @param board Le plateau de jeu
	 * @param book Le livre de règles
	 * @param inter L'interface utilisée
	 * @param ghostTypes Les types de fantômes autorisés
	 */
	private void initializeGhosts (Board board, RuleBook book, Interface inter, Collection<String> ghostTypes){
		while (!book.isReady(this)){//	Tant que le joueur n'est pas prêt selon les règles
			inter.updateDisplay(this);
			String position = inter.readPosition("Veuillez saisir la position du prochain fantôme.");
			if (position != null){//	Si le joueur a saisi une position
				String type = inter.readSelection(ghostTypes, "Veuillez choisir le type de fantôme");
				if (type != null){//	Si le joueur a séléctionné un type
					String isGoodStr = inter.readSelection(Arrays.asList(new String[] {"Bon", "Mauvais"}), "Le fantôme est il bon ou mauvais ?");
					if (isGoodStr != null){//	Si le joueur a choisi si son fantôme était bon ou mauvais
						Ghost ghost = Game.getCurrent().getFactory().createGhost(type, isGoodStr.equals("Bon"));
						if (book.requestInitialization(this, ghost, position)){
							ghost.move(position);
							this.addGhost(ghost);
						}else
							inter.printText("La position " + position + " n'est pas valide pour ce fantôme.");
					}
				}
			}
		}
	}
	/**
	 * Vérifie si le joueur est sûr (et certain) de sa configuration.
	 * @param board Le plateau de jeu
	 * @param book Le livre de règles
	 * @param inter L'interface utilisée
	 * @return <code>true</code> si le joueur est sûr de sa configuration, <code>false</code> sinon.
	 */
	private boolean isSureOfInitialization (Board board, RuleBook book, Interface inter){
		while (book.isReady(this)){
			inter.updateDisplay(this);
			String ans = inter.readSelection(Arrays.asList(new String[] {"Oui", "Non"}), "Êtes vous sûr de votre configuration ?");
			if (ans != null){//	Si le joueur est sûr de sa cnfiguration.
				if (ans.equals("Oui"))
					return true;
				else{//	Si le joueur n'est pas sûr de sa configuration.
					String position = inter.readPosition("Quel fantôme souhaitez vous modifier ?");
					if (position != null && board.getSquare(position) != null){
						Ghost g = board.getSquare(position).getGhost();
						if (g != null && this.hasGhost(g)){//	Si ce fantôme (s'il existe) appartient au joueur.
							this.ghosts.remove(g);
							board.getSquare(position).removeGhost();
						}
					}
				}
			}
		}
		
		return false;
	}
	/**
	 * Fait jouer un tour à ce joueur.
	 */
	public void turn (){
		boolean hasPlayed = false;
		Interface inter = Game.getCurrent().getInterface();
		Board board = Game.getCurrent().getBoard();
		RuleBook book = Game.getCurrent().getRuleBook();
		
		while (!hasPlayed){
			String p0 = inter.readPosition("Quel fantôme souhaitez-vous déplacer ?");
			if (p0 != null && board.getSquare(p0) != null && board.getSquare(p0).getGhost() != null){
				String p1 = inter.readPosition("Où souhaitez-vous déplacer votre fantôme ?");
				if (p1 != null && board.getSquare(p1) != null){
					if (book.requestMovement(this, p0, p1)){
						Ghost ghost = board.getSquare(p0).getGhost();
						hasPlayed = true;
						ghost.move(p1);
						if (board.canExit(p1, ghost))
							this.exitGhost(ghost);
					}else
						inter.printText("Ce déplacement est interdit !");
				}
			}
		}
	}
	/**
	 * Récupère le nom du joueur.
	 * @return Le nom du joueur
	 */
	public String toString (){
		return this.name;
	}
}
