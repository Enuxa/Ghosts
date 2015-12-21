import core.*;
import java.util.*;
import base.*;

public class Main {
	public static void main(String[] args) {
		Interface defaultInterface = new base.WindowsTextBasedInterface();
		Interface inter = null;
		Game game = null;
		Boolean cheatMode = null;

		// S�lection de l'interface
		inter = readInterface(defaultInterface);

		// S�lection de l'interface
		cheatMode = readCheatMode(defaultInterface);

		// S�lection des extensions
		Collection<Extension> extensions = readExtensions(defaultInterface);

		game = new Game(inter, cheatMode, extensions);
		game.run();
	}

	private static Interface readInterface(Interface defaultInterface) {
		Map<String, Interface> interfaceMap = new HashMap<String, Interface>();
		interfaceMap.put("Interface textuelle Unix", new base.UnixTextBasedInterface());
		interfaceMap.put("Interface textuelle générale", new base.WindowsTextBasedInterface());
		Interface inter = null;

		while (inter == null) {
			String str = defaultInterface.readSelection(interfaceMap.keySet(), "Choisissez votre interface : ");
			if (str != null)
				inter = interfaceMap.get(str);
			else
				defaultInterface.printText("Incorrect");
		}

		inter.activate();
		
		return inter;
	}

	private static boolean readCheatMode(Interface defaultInterface) {
		Boolean cheatMode = null;
		Collection<String> yesNo = Arrays.asList(new String[] { "oui", "non" });
		while (cheatMode == null) {
			String str = defaultInterface.readSelection(yesNo, "Souhaitez-vous jouer en mode triche ? ");
			if (str != null && str.equals("oui"))
				cheatMode = true;
			else if (str != null && str.equals("non"))
				cheatMode = false;
			else
				defaultInterface.printText("Incorrect");
		}
		return cheatMode;
	}

	private static Collection<Extension> readExtensions(Interface defaultInterface) {
		Map<String, Extension> extensions = new HashMap<String, Extension>();

		if (extensions.size() == 0) {
			return Arrays.asList(new Extension[] { new BaseExtension() });
		}
		Collection<Extension> choice = null;
		// extensions.add (new base.defaultExtension ());
		while (choice == null) {
			Collection<String> ans = defaultInterface.readSelection(extensions.keySet(),
					"Avec quelles extensions souhaitez-vous jouer ?", 1, extensions.size());
			if (ans == null)
				defaultInterface.printText("Incorrect");
			else {
				choice = new ArrayList<Extension>();
				for (String key : ans)
					choice.add(extensions.get(key));
			}
		}

		return choice;
	}
}