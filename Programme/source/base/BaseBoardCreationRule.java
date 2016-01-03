package base;

import java.util.Arrays;

import core.Board;
import core.BoardCreationRule;

public class BaseBoardCreationRule extends BoardCreationRule {

	private int boardSize;
	public BaseBoardCreationRule (int priority){
		this (priority, 6);
	}
	/**
	 * @param priority La priorité de cette règle
	 * @param size Le nombre cases sur un coté du plateau
	 */
	public BaseBoardCreationRule (int priority, int size){
		super(priority);
		this.boardSize = size;
	}
	
	public Board getBoard() {
		String maxRow = Integer.toString(this.boardSize);
		String maxCol = Character.toString((char)('A' + this.boardSize - 1));
		return new Board (this.boardSize, Arrays.asList(new String[]{"A" + maxRow, maxCol + maxRow}),
				Arrays.asList(new String[]{"A1", maxCol + "1"}));
	}
}
