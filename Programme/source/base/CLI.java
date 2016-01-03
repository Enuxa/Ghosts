package base;

import java.util.*;

public class CLI {
	private Scanner sc;
	
	public String readText(String message) {
		System.out.print(message);
		return this.readText();
	}

	public CLI (){
		this.sc = new Scanner (System.in);
	}
	
	public String readText() {
		String s = this.sc.nextLine();
		if (s == null || s.equals(""))
			return null;
		return s;
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
			return new ArrayList<String> ();
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