package core;
import java.util.*;

public class GhostFactory {
	/**
	*	L'ensemble des types de fantômes autorisés dans cette partie
	*/
	private Collection<String> typeNames;
	public GhostFactory (){
		this.typeNames = new ArrayList<String> ();
	}
	/**
	*	Créé une nouvelle instance d'un certain type de fantôme
	*	@param	ghostType	Type de fantôme souhaité
	*	@param	isGood	<code>true</code> Si le nouveau fantôme doit être bon, <code>false</code> sinon.
	*	@param player Le joueur auquel appartient le fantôme.
	*	@return	Le fantôme souhaité.
	*/
	public Ghost createGhost (String ghostType, boolean isGood, Player player){
		if (this.typeNames.contains(ghostType))
			return new Ghost (isGood, ghostType, player);
		else
			throw new RuntimeException ("Le type " + ghostType + " n'existe pas");
	}
	/**
	*	Ajoute un nouveau type de fantôme aux de fantômes autorisés
	*	@param	ghostType	Nom du type
	*/
	public void addGhostType (String ghostType){
		typeNames.add(ghostType);
	}
	/**
	*	Récupère les noms des types de fantômes autorisés
	*	@return Ensemble des noms de modèles de fantômes
	*/
	public Collection<String> getTypes (){
		return typeNames;
	}
}
