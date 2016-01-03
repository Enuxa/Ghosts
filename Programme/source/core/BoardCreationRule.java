package core;

/**
 * Règle décrivant un nouveau plateau de jeu
 */
public abstract class BoardCreationRule extends Rule {
	public BoardCreationRule(int priority) {
		super(priority);
	}

	/**
	 * Récupère le plateau définit par la règle
	 * @return Le plateau
	 */
	public abstract Board getBoard ();
}