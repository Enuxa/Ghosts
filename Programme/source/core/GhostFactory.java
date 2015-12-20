package core;
import java.util.*;

public class GhostFactory {
	/**
	*	L'ensemble des modèles de fantômes autorisés dans cette partie
	*/
	private Map<String, Ghost> templates;
	/**
	 * Initialise la fabrique
	 */
	public GhostFactory (){
		templates = new HashMap<String, Ghost> ();
	}
	/**
	*	Créé une nouvelle instance d'un certain type de fantôme
	*	@param	ghostType	Type de fantôme souhaité
	*	@param	isGood	<code>true</code> Si le nouveau fantôme doit être bon, <code>false</code> sinon.
	*	@param player Le joueur auquel appartient le fantôme.
	*	@return	Le fantôme souhaité.
	*/
	public Ghost createGhost (String ghostType, boolean isGood, Player player){
		Ghost ghost = templates.get(ghostType);
		return ghost.clone(isGood, player);
	}
	/**
	*	Ajoute un nouveau type de fantôme aux modèles de fantômes autorisés
	*	@param	template	Modèle de fantôme
	*	@param	ghostType	Nom du modèle
	*/
	public void addGhostType (String ghostType, Ghost template){
		templates.put (ghostType, template);
	}
	/**
	*	Récupère les noms des types de fantômes autorisés
	*	@return Ensemble des noms de modèles de fantômes
	*/
	public Collection<String> getTypes (){
		return templates.keySet();
	}
	/**
	 * Récupère le type d'un fantôme.
	 * @param ghost Le fantôme dont on veut connaître le type.
	 * @return Le type du fantôme.
	 */
	public String getType (Ghost ghost){
		Collection<String> types = getTypes ();
		for (String type : types){
			Class<? extends Ghost> c1 = templates.get (type).getClass();//	Classe du modèle (de ce tour de boucle)
			Class<? extends Ghost> c2 = ghost.getClass();				//	Classe du fantôme passé en argument
			if (c1.equals(c2))	//	Si ce sont les mêmes classes, on renvoie le type du modèle
				return type;
		}
		return null;
	}
}
