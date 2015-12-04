public abstract class Extension{
	private String name;
	/**
	 * @param n Nom de l'extension
	 */
	public Extension (String n){
		this.name = n;
	}
	/**
	 * Charge le contenu de l'extension en mémoire.
	 */
	public abstract void load ();
}
