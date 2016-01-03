import java.util.*;
import base.*;
import base.GUIGame.*;
import core.*;

public class Main {

	public static void main(String[] args) {
		Collection<Extension> extensions = new ArrayList <Extension>(),
				selectedExtensions = null;
		CLI cli = new CLI ();
		boolean cheatMode = false;
		String cheatModeString = null;
		
		//	Ajout des extensions existantes
		extensions.add(new Board12Per12Extension ());
		extensions.add(new base.Knights.KnightsExtension ());
		
		//	Récupération du choix de mode de jeu
		while (cheatModeString == null){
			cheatModeString = cli.readSelection(Arrays.asList(new String[]{"Oui", "Non"}), "Souhaitez vous jouer en mode triche ?");
		}
		cheatMode = cheatModeString.equals("Oui");
		
		selectedExtensions = getSelectedExtensions (extensions, cli);
		
		Game game = new GUIGame (cheatMode, selectedExtensions);
		
		game.run();
	}
	
	/**
	 * Récupère le choix d'extensions
	 * @param extensions Les extensions existantes, en plus de l'extension de base
	 * @param cli L'interface en ligne de commande
	 * @return Le choix d'extensions
	 */
	private static Collection<Extension> getSelectedExtensions (Collection<Extension> extensions, CLI cli){
		if (extensions.isEmpty())
			return Arrays.asList(new Extension[]{new BaseExtension ()});
		
		HashMap<String, Extension> map = new HashMap <String, Extension>();
		Collection<String> answer = null;
		Collection<Extension> selectedExtensions = new ArrayList<Extension> ();
		
		for (Extension e : extensions)
			map.put(e.toString(), e);
		
		while (answer == null)
			answer = cli.readSelection(map.keySet(), "Avec quelles extensions souhaitez-vous jouer ?", 0, extensions.size());
		
		selectedExtensions.add(new BaseExtension ());
		for (String k : answer)
			selectedExtensions.add(map.get(k));
		
		return selectedExtensions;
	}

}
