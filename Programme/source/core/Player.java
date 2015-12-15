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
	*	Prépare les fantômes du joueur indiqué
	 * @param cheatMode <code>true</code> si le mode triche est activé
	*/
	public void initialize (boolean cheatMode){
		RuleBook book = Game.getCurrent().getRuleBook();
		Interface inter = Game.getCurrent().getInterface();
		Collection<String> ghostTypes = Game.getCurrent().getFactory().getTypes();
		Board board = Game.getCurrent().getBoard();
		boolean sure = false;
		
		while (!sure){
			this.initializeGhosts(board, book, inter, ghostTypes, cheatMode);
			sure = this.isSureOfInitialization (board, book, inter, cheatMode);
		}
	}
	/**
	 * Fait déposer les fantômes un à un au joueur jusqu'à ce que sa configuration soit correcte.
	 * @param board Le plateau de jeu
	 * @param book Le livre de règles
	 * @param inter L'interface utilisée
	 * @param ghostTypes Les types de fantômes autorisés
	 * @param cheatMode <code>true</code> si le mode triche est activé
	 */
	private void initializeGhosts (Board board, RuleBook book, Interface inter, Collection<String> ghostTypes, boolean cheatMode){
		while (!book.isReady(this)){//	Tant que le joueur n'est pas prêt selon les règles
			inter.updateDisplay(cheatMode ? null : this);
			String position = inter.readPosition("Veuillez saisir la position du prochain fantôme : ");
			if (position != null && book.requestInitialization(this, position)){//	Si le joueur a saisi une position et qu'elle est autorisée
				String type = inter.readSelection(ghostTypes, "Veuillez choisir le type de fantôme : ");
				if (type != null){//	Si le joueur a sélectionné un type
					String isGoodStr = inter.readSelection(Arrays.asList(new String[] {"Bon", "Mauvais"}), "Le fantôme est-il bon ou mauvais ? ");
					if (isGoodStr != null){//	Si le joueur a choisi si son fantôme était bon ou mauvais
						Ghost ghost = Game.getCurrent().getFactory().createGhost(type, isGoodStr.equals("Bon"));
						if (book.requestInitialization(this, ghost, position)){
							ghost.move(position);
							this.addGhost(ghost);
						}else
							inter.printText("Vous ne pouvez pas placer ce fantôme ! (du moins, pas ici)");
					}
				}
			}else
				inter.printText("Cette position n'est pas autorisée !");
		}
	}
	/**
	 * Vérifie si le joueur est sûr (et certain) de sa configuration.
	 * @param board Le plateau de jeu
	 * @param book Le livre de règles
	 * @param inter L'interface utilisée
	 * @return <code>true</code> si le joueur est sûr de sa configuration, <code>false</code> sinon.
	 * @param cheatMode <code>true</code> si le mode triche est activé.
	 */
	private boolean isSureOfInitialization (Board board, RuleBook book, Interface inter, boolean cheatMode){
		while (book.isReady(this)){
			inter.updateDisplay(cheatMode ? null : this);
			String ans = inter.readSelection(Arrays.asList(new String[] {"Oui", "Non"}), "ÃŠtes-vous sÃ»r de votre configuration ?");
			if (ans != null){//	Si le joueur est sÃ»r de sa configuration.
				if (ans.equals("Oui"))
					return true;
				else{//	Si le joueur n'est pas sÃ»r de sa configuration.
					String position = inter.readPosition("Quel fantÃ´me souhaitez-vous dÃ©placer ? ");
					if (position != null && board.getSquare(position) != null){
						Square s = board.getSquare(position);
						Ghost g1 = s.getGhost();
						String position2 = inter.readPosition("Avec quel fantÃ´me ? ");
						if (position2 != null && board.getSquare(position2) != null){
							Square s2 = board.getSquare(position2);
							Ghost g2 = s2.getGhost();
							if (g1 != null && this.hasGhost(g1) && g2 != null && this.hasGhost(g2)){//	Si ces fantÃ´mes (s'ils existent) appartiennent au joueur.
								s.removeGhost();
								s2.removeGhost();
								s.putGhost(g2);
								s2.putGhost(g1);
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	/**
	 * Fait jouer un tour Ã  ce joueur.
	 */
	public void turn (){
		boolean hasPlayed = false;
		Interface inter = Game.getCurrent().getInterface();
		Board board = Game.getCurrent().getBoard();
		RuleBook book = Game.getCurrent().getRuleBook();
		
		inter.printText("Joueur : " + this);
		
		while (!hasPlayed){
			String p0 = inter.readPosition("Quel fantÃ´me souhaitez-vous dÃ©placer ? ");
			if (p0 != null && board.getSquare(p0) != null && board.getSquare(p0).getGhost() != null){
				String p1 = inter.readPosition("OÃ¹ souhaitez-vous dÃ©placer votre fantÃ´me ? ");
				if (p1 != null && board.getSquare(p1) != null){
					if (book.requestMovement(this, p0, p1)){
						Ghost ghost = board.getSquare(p0).getGhost();
						Square s2 = board.getSquare(p1);
						Ghost g2 = s2.getGhost();
						if (g2 != null)
							board.capture(g2);
						hasPlayed = true;
						ghost.move(p1);
						if (board.canExit(p1, ghost))
							this.exitGhost(ghost);
					}else
						inter.printText("Ce dÃ©placement est interdit !");
				}
			}
		}
	}
	/**
	 * RÃ©cupÃ¨re le nom du joueur.
	 * @return Le nom du joueur
	 */
	public String toString (){
		return this.name;
	}
}
