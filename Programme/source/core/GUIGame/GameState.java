package core.GUIGame;

/**
 *	Enumération indiquant quelle est l'étape actuelle du jeu
 */

public enum GameState{
	playersCreation,		//	Création d'un joueur
	playerInitialization,	//	Initialisation des pions d'un joueur
	inTurn					//	Un joueur est en train de jouer
}