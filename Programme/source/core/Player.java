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
	*	R�cup�re l'ensemble des fant�mes restant du joueur
	*	@return	L'ensemble des fant�mes du joueur
	*/
	public Collection<Ghost> getGhosts (){
		return this.ghosts;
	}
	/**
	 * R�cup�re les fant�mes qui ont r�ussi � sortir
	 * @return Les fant�mes sortis
	 */
	public Collection<Ghost> getExited (){
		return this.exited;
	}
	/**
	*	R�cup�re seulement les bons (ou mauvais) fant�mes du joueur qu'il avait au d�but de la partie
	*	@param	good	<code>true</code> si on demande les bons fant�mes, <code>false</code> sinon
	*	@return	L'ensemble des bons (ou mauvais) fant�mes du joueur
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
	 * Ajoute un fant�me
	 * @param ghost Le fant�me � ajouter.
	 */
	public void addGhost (Ghost ghost) {
		this.ghosts.add(ghost);
	}
	/**
	 * Le nombre initial de fant�mes de ce joueur
	 * @return Le nombre initial de fant�mes
	 */
	public Set<Ghost> getCaptured (){
		return this.captured;
	}
	/**
	 * Fait sortir un fant�me
	 * @param ghost Le fant�me � faire sortir.
	 */
	public void exitGhost (Ghost ghost) {
		if (this.hasGhost(ghost)){
			this.exited.add(ghost);
		}
	}
	/**
	 * Indique qu'un fant�me a �t� captur�
	 * @param ghost Le fant�me � capturer
	 */
	public void captureGhost (Ghost ghost) {
		if (this.hasGhost(ghost)){
			this.captured.add(ghost);
		}
	}
	/**
	 * Indique si un fant�me appartient � ce joueur.
	 * @param ghost Le fant�me � tester.
	 * @return <code>true</code> si ce fant�me appartient � ce joueur, <code>false</code> sinon.
	 */
	public boolean hasGhost (Ghost ghost){
		return this.ghosts.contains(ghost);
	}
	/**
	*	Pr�pare les fant�mes du joueur indiqu�
	 * @param cheatMode <code>true</code> si le mode triche est activ�
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
	 * Fait d�poser les fant�mes un � un au joueur jusqu'� ce que sa configuration soit correcte.
	 * @param board Le plateau de jeu
	 * @param book Le livre de r�gles
	 * @param inter L'interface utilis�e
	 * @param ghostTypes Les types de fant�mes autoris�s
	 * @param cheatMode <code>true</code> si le mode triche est activ�
	 */
	private void initializeGhosts (Board board, RuleBook book, Interface inter, Collection<String> ghostTypes, boolean cheatMode){
		while (!book.isReady(this)){//	Tant que le joueur n'est pas pr�t selon les r�gles
			inter.updateDisplay(cheatMode ? null : this);
			String position = inter.readPosition("Veuillez saisir la position du prochain fant�me : ");
			if (position != null && book.requestInitialization(this, position)){//	Si le joueur a saisi une position et qu'elle est autoris�e
				String type = inter.readSelection(ghostTypes, "Veuillez choisir le type de fant�me : ");
				if (type != null){//	Si le joueur a s�lectionn� un type
					String isGoodStr = inter.readSelection(Arrays.asList(new String[] {"Bon", "Mauvais"}), "Le fant�me est-il bon ou mauvais ? ");
					if (isGoodStr != null){//	Si le joueur a choisi si son fant�me �tait bon ou mauvais
						Ghost ghost = Game.getCurrent().getFactory().createGhost(type, isGoodStr.equals("Bon"));
						if (book.requestInitialization(this, ghost, position)){
							ghost.move(position);
							this.addGhost(ghost);
						}else
							inter.printText("Vous ne pouvez pas placer ce fant�me ! (du moins, pas ici)");
					}
				}
			}else
				inter.printText("Cette position n'est pas autoris�e !");
		}
	}
	/**
	 * V�rifie si le joueur est s�r (et certain) de sa configuration.
	 * @param board Le plateau de jeu
	 * @param book Le livre de r�gles
	 * @param inter L'interface utilis�e
	 * @return <code>true</code> si le joueur est s�r de sa configuration, <code>false</code> sinon.
	 * @param cheatMode <code>true</code> si le mode triche est activ�.
	 */
	private boolean isSureOfInitialization (Board board, RuleBook book, Interface inter, boolean cheatMode){
		while (book.isReady(this)){
			inter.updateDisplay(cheatMode ? null : this);
			String ans = inter.readSelection(Arrays.asList(new String[] {"Oui", "Non"}), "Êtes-vous sûr de votre configuration ?");
			if (ans != null){//	Si le joueur est sûr de sa configuration.
				if (ans.equals("Oui"))
					return true;
				else{//	Si le joueur n'est pas sûr de sa configuration.
					String position = inter.readPosition("Quel fantôme souhaitez-vous déplacer ? ");
					if (position != null && board.getSquare(position) != null){
						Square s = board.getSquare(position);
						Ghost g1 = s.getGhost();
						String position2 = inter.readPosition("Avec quel fantôme ? ");
						if (position2 != null && board.getSquare(position2) != null){
							Square s2 = board.getSquare(position2);
							Ghost g2 = s2.getGhost();
							if (g1 != null && this.hasGhost(g1) && g2 != null && this.hasGhost(g2)){//	Si ces fantômes (s'ils existent) appartiennent au joueur.
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
	 * Fait jouer un tour à ce joueur.
	 */
	public void turn (){
		boolean hasPlayed = false;
		Interface inter = Game.getCurrent().getInterface();
		Board board = Game.getCurrent().getBoard();
		RuleBook book = Game.getCurrent().getRuleBook();
		
		inter.printText("Joueur : " + this);
		
		while (!hasPlayed){
			String p0 = inter.readPosition("Quel fantôme souhaitez-vous déplacer ? ");
			if (p0 != null && board.getSquare(p0) != null && board.getSquare(p0).getGhost() != null){
				String p1 = inter.readPosition("Où souhaitez-vous déplacer votre fantôme ? ");
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
