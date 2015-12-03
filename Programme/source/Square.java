/**
 *	Classe correspondant à une case.
 */
public class Square{
	private String coordinates;
	private Ghost ghost;
	/**
	 * @param coordinates Coordonnées de cette case.
	 */
	public Square (String coordinates){
		this.coordinates = coordinates;
		this.ghost = null;
	}
	/**
	 * Récupère le fantôme présent sur cette case
	 * @return Le fantôme sur cette case, ou <code>null</code> s'il n'y a aucun fantôme sur cette case.
	 */
	public Ghost getGhost (){
		return this.ghost;
	}
	/**
	 * Place un fantôme sur cette case.
	 * @param ghost Le fantôme à placer.
	 * @throws RuntimeException si un fantôme se trouve déjà sur cette case.
	 */
	public void putGhost (Ghost ghost) throws RuntimeException{
		if (this.ghost != null)
			throw new RuntimeException ("Un fantôme se trouve déjà à la position " + this);
		this.ghost = ghost;
	}
	/**
	 * Retire le fantôme présent sur cette case.
	 */
	public void removeGhost (){
		this.ghost = null;
	}
	/**
	 * Récupère les coordonnées de cette case.
	 */
	public String toString (){
		return this.coordinates;
	}
}
