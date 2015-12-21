package core;
import java.util.*;

public abstract class Interface{
	/**
	 * Active l'interface si elle a besoin d'être activée (par exemple dans le cas d'une interface graphique, il faut lui demander de s'afficher)
	 */
	public void activate (){}
	/**
	 * Lit une chaîne de caractères
	 * @param message Message adressé à l'utilisateur
	 * @return La chaîne saisie par l'utilisateur
	 */
	public abstract String readText (String message);
	/**
	 * Lit une chaîne de caractères
	 * @return La chaîne saisie par l'utilisateur
	 */
	public abstract String readText ();
	/**
	 * Actualise l'affichage avec la possibilité de n'afficher les fantômes que d'un seul joueur.
	 * @param player Le joueur dont les fantômes sont visibles (<code>null</code> pour qu'ils soient tous visibles).
	 */
	public abstract void updateDisplay (Player player);
	/**
	 * Transmet un message à l'utilisateur
	 * @param message Le message à transmettre.
	 */
	public abstract void printText (String message);
	/**
	 * Transmet un message à l'utilisateur et attend une interaction pour continuer
	 * @param message1 Le message à transmettre.
	 * @param message2 Le message d'attente (<code>null</code> pour le message par défaut)
	 */
	public abstract void printText (String message1, String message2);
	/**
	 * Récupère une position saisie par l'utilisateur.
	 * @param message message adressé à l'utilisateur
	 * @return La position saisie par l'utilisateur (<code>null</code> si aucune position n'a été saisie).
	 */
	public abstract String readPosition (String message);
	/**
	 * Récupère une position saisie par l'utilisateur.
	 * @return La position saisie par l'utilisateur (<code>null</code> si aucune position n'a été saisie).
	 */
	public abstract String readPosition ();
	/**
	 * Récupère la sélection de l'utilisateur parmi un choix de réponse.
	 * @param choice Liste ordonnée de réponses possibles
	 * @param message Message à afficher.
	 * @param min Nombre minimum de réponses à fournir.
	 * @param max Nombre maximum de réponses à fournir.
	 * @return Une liste ordonnées de réponses : pour tout <i>i</i>, l'utilisateur à sélectionné la <i>i</i>-ème réponse si la <i>i</i>-ème valeur de la liste renvoyée vaut <code>true</code> (<code>null</code> si la sélection a été annulée).
	 */
	public abstract Collection<String> readSelection (Collection<String> choice, String message, int min, int max);
	/**
	 * Récupère la sélection de l'utilisateur parmi un choix de réponse.
	 * @param choice Liste ordonnée de réponses possibles
	 * @param min Nombre minimum de réponses à fournir.
	 * @param max Nombre maximum de réponses à fournir.
	 * @return Une liste ordonnées de réponses : pour tout <i>i</i>, l'utilisateur à sélectionné la <i>i</i>-ème réponse si la <i>i</i>-ème valeur de la liste renvoyée vaut <code>true</code> (<code>null</code> si la sélection a été annulée).
	 */
	public abstract Collection<String> readSelection (Collection<String> choice, int min, int max);
	/**
	 * Récupère l'unique sélection de l'utilisateur parmi un choix de réponse.
	 * @param choice Ensemble de réponses possibles
	 * @param message Message à afficher.
	 * @return Le choix de l'utilisateur.
	 */
	public abstract String readSelection (Collection<String> choice, String message);
	/**
	 * Récupère l'unique sélection de l'utilisateur parmi un choix de réponse.
	 * @param choice Ensemble de réponses possibles
	 * @return Le choix de l'utilisateur.
	 */
	public abstract String readSelection (Collection<String> choice);
}
