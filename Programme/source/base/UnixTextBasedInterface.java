package base;

import java.util.*;
import java.util.regex.Pattern;

import core.*;

public class UnixTextBasedInterface extends Interface {
	private Scanner sc;
	private Map<String, String> colorKeys;
	public UnixTextBasedInterface () {
		this.sc = new Scanner (System.in);
		this.colorKeys = new HashMap <String, String> ();
		this.colorKeys.put("red", "\u001B[31m");
		this.colorKeys.put("blue", "\u001B[36m");
		this.colorKeys.put("yellow", "\u001B[33m");
		this.colorKeys.put("green", "\u001B[32m");
		this.colorKeys.put("purple", "\u001B[35m");
		this.colorKeys.put("default", "\u001B[0m");
	}
	
	@Override
	public String readText(String message) {
		System.out.print(message);
		return this.readText();
	}

	@Override
	public String readText() {
		String s = this.sc.nextLine();
		if (s == null || s.equals(""))
			return null;
		return s;
	}

	@Override
	public void updateDisplay(Player player) {
		Collection<String> ghostTypes = GhostFactory.getTypes();
		int ghostTypesNumber = ghostTypes.size();
		Game game = Game.getCurrent();
		Board board = game.getBoard();
		int size = board.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++){
				Square s = board.getSquare(Board.toCoordinates(i, j));
				Ghost g = s.getGhost();
				this.displayGhost(ghostTypesNumber, game, g, player);
				if (j < size - 1)
					System.out.print(" | ");
			}
			System.out.println();
		}
		
		//	Explications de la représentation des fantômes
		System.out.println("\nMinuscules : bon fantômes, majuscules : mauvais fantômes.");
		for (String type : ghostTypes)
			System.out.println(hashGhostType (type, ghostTypesNumber) + " : " + type);
	}

	/**
	 * Donne le caractère représentant un fantôme d'un type donné
	 * @param ghostType Le type du fantôme
	 * @param ghostTypesNumber Le nombre de type de fantômes.
	 * @return Le caractère représentant le fantôme.
	 */
	private char hashGhostType (String ghostType, int ghostTypesNumber){
		return (char)('a' + ghostType.hashCode() % ghostTypesNumber);
	}
	
	private char hashGhostType (Ghost ghost, int ghostTypesNumber){
		return hashGhostType (GhostFactory.getType(ghost), ghostTypesNumber);
	}
	
	private void displayGhost (int ghostTypesNumber, Game game, Ghost ghost, Player player){
		String ghostType = Character.toString(this.hashGhostType(ghost, ghostTypesNumber));
		
		String colorCode = null;
		
		//	Vert minuscule : bon, rouge majuscule : mauvais
		if (!ghost.isGood()){
			ghostType = ghostType.toUpperCase();
			colorCode = this.colorKeys.get("red");
		}else
			colorCode = this.colorKeys.get("green");
		
		//	X majuscule jaune : joueur caché
		if (player != null && !player.hasGhost(ghost)){
			ghostType = "X";
			colorCode = this.colorKeys.get("yellow");
		}
		
		//	Si le on est en mode triche, [.] : joueur 1, (.) : joueur 2
		if (player == null){
			if (Game.getCurrent().getPlayer(0).hasGhost(ghost))
				ghostType = "[" + ghostType + "]";
			else
				ghostType = "(" + ghostType + ")";
		}
		
		System.out.print(colorCode + ghostType + this.colorKeys.get ("default"));
	}
	@Override
	public void printText(String message) {
		System.out.println(message);
	}

	@Override
	public void printText(String message1, String message2) {
		this.printText(message1);
		if (message2 == null)
			message2 = "Appuyez sur Entrée pour continuer...";
		System.out.print(message2);
		this.sc.nextLine();
	}

	@Override
	public String readPosition(String message) {
		System.out.print(message);
		return this.readPosition();
	}

	@Override
	public String readPosition() {
		String s = this.readText();
		if (Pattern.matches("^[a-zA-Z]\\d{1,}?$", s))
			return s.toUpperCase();
		return null;
	}

	@Override
	public Collection<String> readSelection(Collection<String> choice, String message, int min, int max) {
		this.printText(message);
		return this.readSelection(choice, min, max);
	}

	@Override
	public Collection<String> readSelection(Collection<String> c, int min, int max) {
		List<String> choice = new ArrayList<String> (c);
		for (int i = 0; i < choice.size(); i++)
			System.out.println((i+1) + " : " + choice.get(i));
		String msg = "";
		if (min == max && min != 1)
			msg = min + " réponses : ";
		if (min != max){
			if (max < min){
				int tmp = max;
				max = min;
				min = tmp;
			}
			msg = "Entre " + min + " et " + max + " réponses : ";	
		}
		String ans = this.readText(msg);
		if (ans == null)
			return null;
		String[] tab = ans.split(" ");
		Collection<String> answers = new ArrayList<String>();
		for (String s : tab){
			try{
				int n = Integer.parseInt(s);
				if (1 <= n && n <= choice.size() && answers.size() < max)
					answers.add(choice.get(n - 1));
			}catch (Exception e){}
		}
		
		if (answers.size() < min)
			return null;
		return answers;
	}

	@Override
	public String readSelection(Collection<String> choice, String message) {
		this.printText(message);
		return this.readSelection(choice);
	}

	@Override
	public String readSelection(Collection<String> choice) {
		Collection<String> c = this.readSelection(choice, 1, 1);

		String a = null;
		for (String s : c)
			a = s;
		
		return a;
	}
}
