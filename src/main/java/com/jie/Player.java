package com.jie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Player implements Play {

	protected GameBoard gameBoard;
	protected List<int[][]> loseSteps = new ArrayList<int[][]>();
	protected StringBuilder sb = new StringBuilder();
	protected Scanner scanIn = null;

	public void consoleReader(Scanner scanIn) {
		this.scanIn = scanIn;
	}

	public abstract PlayerType playerType();

	protected StringBuilder sb() {
		sb.setLength(0);
		return sb;
	}

	public List<int[][]> getLoseSteps() {
		return loseSteps;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

}


