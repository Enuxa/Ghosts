package base;

import java.util.*;
import java.util.regex.Pattern;

import core.*;

public class CLI {
	private boolean useColor;
	private Scanner sc;
	private String RED, BLUE, YELLOW, GREEN, PURPLE, BOLD, DEFAULT;
	public CLI (boolean useColor) {
		this.sc = new Scanner (System.in);
		this.RED = "\u001B[31m";
		this.BLUE = "\u001B[36m";
		this.YELLOW = "\u001B[33m";
		this.GREEN = "\u001B[32m";
		this.PURPLE = "\u001B[35m";
		this.DEFAULT = "\u001B[0m";
		this.useColor = useColor;
	}
	public CLI (){
		this (true);
	}
	
	public String readText(String message) {
		System.out.print(message);
		return this.readText();
	}

	public String readText() {
		String s = this.sc.nextLine();
		if (s == null || s.equals(""))
			return null;
		return s;
	}

	public void updateDisplay(Player player) {
		Game game = Game.getCurrent();
		Collection<String> ghostTypes = game.getFactory().getTypes();
		int ghostTypesNumber = ghostTypes.size();
		Board board = game.getBoard();
		int size = board.getSize();
		
		System.out.println();
		
		if (player != null)
			System.out.println("Joueur : " + player);
		System.out.print("\t");
		for (int k = 0; k < size; k++)
			System.out.print("  " + (char)('A' + k) + " ");
		System.out.println();
		for (int j = size - 1; j >= 0; j--) {
			System.out.print((j+1) + "\t|");
			for (int i = 0; i < size; i++){
				Square s = board.getSquare(Board.toCoordinates(i, j));
				Ghost g = s.getGhost();
				this.displayGhost(ghostTypesNumber, game, g, player);
				
				System.out.print("|");
			}
			System.out.println();
		}
		
		//	Explications de la repr�sentation des fant�mes
		if (!this.useColor)
			System.out.println("\nMinuscules : bon fantômes, majuscules : mauvais fantômes.");
		for (String type : ghostTypes)
			System.out.println(hashGhostType (type, ghostTypesNumber) + " : " + type);
	}

	/**
	 * Donne le caract�re repr�sentant un fant�me d'un type donn�
	 * @param ghostType Le type du fant�me
	 * @param ghostTypesNumber Le nombre de type de fant�mes.
	 * @return Le caract�re repr�sentant le fant�me.
	 */
	private char hashGhostType (String ghostType, int ghostTypesNumber){
		return (char)('a' + ghostType.hashCode() % ghostTypesNumber);
	}
	
	private char hashGhostType (Ghost ghost, int ghostTypesNumber){
		return hashGhostType (Game.getCurrent().getFactory().getType(ghost), ghostTypesNumber);
	}
	
	private void displayGhost (int ghostTypesNumber, Game game, Ghost ghost, Player player){
		if (ghost == null){
			System.out.print("   ");
			return;
		}
		
		String ghostType = Character.toString(this.hashGhostType(ghost, ghostTypesNumber));
		
		String colorCode = null;
		
		//	Vert minuscule : bon, rouge majuscule : mauvais
		if (!ghost.isGood()){
			ghostType = ghostType.toUpperCase();
			colorCode = this.RED;
		}else
			colorCode = this.GREEN;
		
		//	X majuscule jaune : joueur cach�
		if (player != null && !player.hasGhost(ghost)){
			ghostType = "X";
			colorCode = this.YELLOW;
		}
		
		//	Si on est en mode triche, [.] : joueur 1, (.) : joueur 2
		if (player == null){
			if (Game.getCurrent().getPlayer(0).hasGhost(ghost))
				ghostType = "[" + ghostType + "]";
			else
				ghostType = "(" + ghostType + ")";
		}else
			ghostType = " " + ghostType + " ";
		if (this.useColor)
			ghostType = colorCode + ghostType+ this.DEFAULT;
		System.out.print(ghostType);
	}
	public void printText(String message) {
		System.out.println(message);
	}

	public void printText(String message1, String message2) {
		this.printText(message1);
		if (message2 == null)
			message2 = "Appuyez sur Entrée pour continuer...";
		System.out.print(message2);
		this.sc.nextLine();
	}

	public String readPosition(String message) {
		System.out.print(message);
		return this.readPosition();
	}

	public String readPosition() {
		String s = this.readText();
		if (s == null)
			return null;
		if (Pattern.matches("^[a-zA-Z]\\d{1,}?$", s))
			return s.toUpperCase();
		return null;
	}

	public Collection<String> readSelection(Collection<String> choice, String message, int min, int max) {
		this.printText(message);
		return this.readSelection(choice, min, max);
	}

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

	public String readSelection(Collection<String> choice, String message) {
		this.printText(message);
		return this.readSelection(choice);
	}

	public String readSelection(Collection<String> choice) {
		Collection<String> c = this.readSelection(choice, 1, 1);

		if (c == null)
			return null;
		
		String a = null;
		for (String s : c)
			a = s;
		
		return a;
	}
}