package core;

/**
 *	Règle de mouvement
 */

public abstract class MovementRule extends Rule {
	/**
	*	@param	priority	Niveau de priorité de la règle.
	*/
	public MovementRule(int priority) {
		super(priority);
	}
	/**
	*	Indique si un déplacement est légal ou non selon cette règle.
	*	@param	player	Le joueur plaçant ses fantômes.
	*	@param	squareA	La position du fantôme a déplacer.
	*	@param	squareB	La position à laquelle placer le fantôme.
	*	@return	<code>true</code> si ce déplacement est autorisé, <code>false</code> sinon.
	*/
	public boolean requestMovement (Player player, String squareA, String squareB){
		Board board = Game.getCurrent().getBoard();
		Square a = board.getSquare(squareA);
		Square b = board.getSquare(squareB);
		if (a == null || b == null)		// Si l'une des cases n'existe pas
			return false;
		if (!player.hasGhost(a.getGhost()))		// Si le fantôme de départ est inexistant ou s'il n'appartient pas au joueur
			return false;
		if (player.hasGhost(b.getGhost()))		// Si le fantôme sur la case d'arrivée (s'il existe) appartient au joueur
			return false;
		return true;
	}
}
