import java.util.*;

public class Ghost implements Cloneable{
	private boolean isGood;
	
	/**
	*	@param	isGood	<code>true</code> si ce fantôme est gentil, <code>false</code> sinon
	*/
	public Ghost (boolean isGood){
		this.isGood = isGood;
	}
	/**
	*	Indique si ce fantôme est gentil ou non
	*	@return	<code>true</code> si ce fantôme est gentil, <code>false</false> sinon
	*/
	public boolean isGood (){
		return this.isGood;
	}
	
	/**
	*	Déplace ce fantôme
	*	@param	coordinates	Nouvelle position du fantôme
	*/
	public void move (String coordinates){
		throw new UnsupportedOperationException ("Pas implémenté");
	}
	/**
	*	Clone le fantôme (prévu pour cloner les modèles)
	*	@return	Copie du fantôme
	*/
	public Ghost clone (){
		Ghost g = this;
		return g;
	}
}
