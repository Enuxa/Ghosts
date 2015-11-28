import java.util.*;

public class Ghost implements Cloneable{
	/**
	*	L'ensemble des modèles de fantômes autorisés dans cette partie.
	*/
	private static Map<String, Ghost>templates;
	/**
	*	Créé une nouvelle instance d'un certain type de fantôme.
	*	@param	ghostType	Type de fantôme souhaité
	*	@param	isGood	<code>true</code> Si le nouveau fantôme doit être bon, <code>false</code> sinon.
	*	@return	Le fantôme souhaité.
	*/
	public static Ghost createGhost (String ghostType, boolean isGood){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Ajoute un nouveau type de fantôme aux modèles de fantômes autorisés.
	*	@param	template	Modèle de fantôme.
	*	@param	ghostType	Nom du modèle.
	*/
	public static void addGhostType (Ghost template, String ghostType){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	@param	isGood	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon.
	*/
	/**
	*	Récupère les noms des types de fantômes autorisés.
	*	@return Ensemble des noms de modèles de fantômes.
	*/
	public static Collection<String> getTypes (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	public Ghost (boolean isGood){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Indique si ce fantôme est gentil ou non
	*	@return	<code>true</code> si ce fantôme est gentil, <code>false</false> sinon.
	*/
	public boolean isGood (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Déplace ce fantôme.
	*	@param	coordinates	Nouvelle position du fantôme.
	*/
	public void move (String coordinates){
		throw new UnsupportedOperationException ("Pas implementé");
	}
	/**
	*	Clone le fantôme (prévu pour cloner les modèles).
	*	@return	Copie du fantôme
	*/
	public Ghost clone (){
		throw new UnsupportedOperationException ("Pas implementé");
	}
}
