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
	}
	/**
	 * Récupère le fantôme présent sur ctte case
	 * @return Le fantôme sur cette case, ou <code>null</code> s'il n'y a aucun fantôme sur cette case.
	 */
	public Ghost getGhost (){
		return this.ghost;
	}
	/**
	 * Place un fantôme sur cette case.
	 * @param ghost Le fantôme à placer.
	 */
	public void putGhost (Ghost ghost){
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
