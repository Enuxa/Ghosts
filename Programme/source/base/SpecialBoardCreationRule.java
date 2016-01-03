package base;

import java.util.*;

import core.*;

/**
 * Règle d'initialisation pour un plateau de 10 x 10 cases avec une partie de chaque ligne extrême en sortie
 */
public class SpecialBoardCreationRule extends BoardCreationRule {

	public SpecialBoardCreationRule(int priority) {
		super(priority);
	}
	
	public Board getBoard (){
		Collection<String> ex0 = new ArrayList<String> ();
		Collection<String> ex1 = new ArrayList<String> ();
		
		for (int i = 2; i < 8; i++){
			ex0.add(Character.toString((char)('A' + i)) + 1);
			ex1.add(Character.toString((char)('A' + i)) + 10);
		}
		
		return new Board (10, ex0, ex1);
	}
}
