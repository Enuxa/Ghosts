package core;

import java.util.*;

public class RuleSet <T extends Rule>{
	private Collection<T> values;
	public RuleSet (){
		this.values = new ArrayList<T> ();
	}
	
	public void add (T rule){
		if (!this.values.contains(rule))
			this.values.add(rule);
	}
	
	public Collection<T> getTopRules (){
		//	On récupère la règle de priorité maximale
		Rule top = null;
		for (T r : this.values){
			if (top == null)
				top = r;
			else if (r.prevailsOver(top))
				top = r;
		}
		
		//	On ajoute les règles de priorité
		Collection<T> c = new ArrayList<T> ();
		for (T r : this.values){
			if (r.samePriority(top))
				c.add(r);
		}
		
		return c;
	}
}
