import java.util.*;

public class GhostFactory {
	/**
	*	L'ensemble des modèles de fantômes autorisés dans cette partie
	*/
	private static Map<String, Ghost> templates;
	/**
	*	Créé une nouvelle instance d'un certain type de fantôme
	*	@param	ghostType	Type de fantôme souhaité
	*	@param	isGood	<code>true</code> Si le nouveau fantôme doit être bon, <code>false</code> sinon
	*	@return	Le fantôme souhaité.
	*/
	public static Ghost createGhost (String ghostType, boolean isGood){
		Ghost ghost = templates.get(ghostType);
		return ghost.clone(isGood);
	}
	/**
	*	Ajoute un nouveau type de fantôme aux modèles de fantômes autorisés
	*	@param	template	Modèle de fantôme
	*	@param	ghostType	Nom du modèle
	*/
	public static void addGhostType (String ghostType, Ghost template){
		templates.put (ghostType, template);
	}
	/**
	*	Récupère les noms des types de fantômes autorisés
	*	@return Ensemble des noms de modèles de fantômes
	*/
	public static Collection<String> getTypes (){
		return templates.keySet();
	}
}
