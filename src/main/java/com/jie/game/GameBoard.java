package com.jie.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
	private final int size = 3;
	private PlayerType[][] board;
	private PlayerType winner;
	private int moveCount;
	private Player[] players = new Player[2];
	private int[][] currentRoundSteps;

	protected StringBuilder sb = new StringBuilder();

	protected StringBuilder sb() {
		sb.setLength(0);
		return sb;
	}

	public void setPlayer(Player player, int i) {
		players[i] = player;
	}

	public int[][] getCurrentRoundSteps() {
		return currentRoundSteps;
	}

	public int getMoveCount() {
		return moveCount;
	}

	private int[][] newSteps() {
		return new int[size * size][2];
	}

	public int getSize() {
		return size;
	}

	private PlayerType[][] newBoard() {
		PlayerType[][] b = new PlayerType[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				b[row][col] = PlayerType.NONE;
			}
		}

		return b;
	}

	public int moveCount(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			if (m[i][0] == 0) {
				return i;
			}
		}

		return m.length;
	}

	private int[][] rotate90CounterClock(int[][] from) {
		int xShiftUp, yShiftLeft;
		int[][] turn90 = newSteps();
		for (int i = 0; i < from.length; i++) {
			if (from[i][0] == 0) {
				break;
			}

			xShiftUp = from[i][0] - 2;
			yShiftLeft = from[i][1] - 2;
			if (xShiftUp == 0 && yShiftLeft == 0) {
				turn90[i][0] = xShiftUp + 2;
				turn90[i][1] = yShiftLeft + 2;
			} else if (xShiftUp == 0) {
				turn90[i][0] = -1 * yShiftLeft + 2;
				turn90[i][1] = xShiftUp + 2;
			} else if (yShiftLeft == 0) {
				turn90[i][0] = yShiftLeft + 2;
				turn90[i][1] = xShiftUp + 2;
			} else if (xShiftUp == yShiftLeft) {
				turn90[i][0] = -1 * xShiftUp + 2;
				turn90[i][1] = yShiftLeft + 2;
			} else {
				turn90[i][0] = xShiftUp + 2;
				turn90[i][1] = -1 * yShiftLeft + 2;
			}
		}
		return turn90;
	}

	public void start() throws IOException {
		board = newBoard();
		winner = PlayerType.NONE;
		moveCount = 0;
		currentRoundSteps = newSteps();
	}

	public void init(BufferedReader fis, Player player) throws IOException {
		try {
			String msg = player.playerType().description() + " Load Steps: ";
			String line;
			String[] ss;
			int[][] steps;
			while ((line = fis.readLine()) != null) {
				ss = line.split("-");
				steps = newSteps();
				for (int i = 0; i < (ss == null ? 0 : ss.length); i++) {
					steps[i][0] = Integer.parseInt(ss[i].substring(1, 2));
					steps[i][1] = Integer.parseInt(ss[i].substring(3, 4));
				}

				addSteps(player.loseSteps, steps, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public boolean move() {
		boolean b = players[this.moveCount % 2].move();
		if (!b) {
			System.out.println("Cannot move any more !!!");
		}

		return b;
	}

	private boolean isValidateRowCol(int x) {
		return (x >= 1 && x <= size);
	}

	private boolean isTaken(int x, int y) {
		return (board[x - 1][y - 1] != PlayerType.NONE);
	}

	public boolean validateYourMove(int[] xy, boolean printMsg) {
		// x, y from 1 to size
		if (xy == null || !isValidateRowCol(xy[0]) || !isValidateRowCol(xy[1])) {
			System.out
					.println("Warning: Invalid move. A valid row/col is between 1 to "
							+ size);
			return false;
		}

		if (isTaken(xy[0], xy[1])) {
			if (printMsg) {
				System.out.println("Warning: Invalid move. " + xy[0] + ","
						+ xy[1] + " was taken.");
			}
			return false;
		}
		return true;
	}

	public void setGrid(int row, int col, boolean print) {
		board[row][col] = players[this.moveCount % 2].playerType();
		currentRoundSteps[moveCount][0] = row + 1;
		currentRoundSteps[moveCount][1] = col + 1;
		moveCount++;

		if (print) {
			print();
		}
	}

	public boolean isGridSet(int row, int col) {
		return PlayerType.NONE != board[row][col];
	}

	private PlayerType checkRow() {
		if (moveCount == 0) {
			return PlayerType.NONE;
		}

		int row = this.currentRoundSteps[moveCount - 1][0] - 1;
		PlayerType potentialWinner = board[row][this.currentRoundSteps[moveCount - 1][1] - 1];
		for (int col = 0; col < size; col++) {
			if (potentialWinner != board[row][col]) {
				return PlayerType.NONE;
			}
		}

		return potentialWinner;
	}

	private PlayerType checkCol() {
		if (moveCount == 0) {
			return PlayerType.NONE;
		}

		int col = this.currentRoundSteps[moveCount - 1][1] - 1;
		PlayerType potentialWinner = board[this.currentRoundSteps[moveCount - 1][0] - 1][col];
		for (int row = 0; row < size; row++) {
			if (potentialWinner != board[row][col]) {
				return PlayerType.NONE;
			}
		}

		return potentialWinner;
	}

	private PlayerType checkBackwardDiagonal() {

		if (this.currentRoundSteps[moveCount - 1][0] != this.currentRoundSteps[moveCount - 1][1]) {
			return PlayerType.NONE;
		}

		PlayerType potentialWinner = board[this.currentRoundSteps[moveCount - 1][0] - 1][this.currentRoundSteps[moveCount - 1][1] - 1];
		for (int row = 0; row < size; row++) {
			if (potentialWinner != board[row][row]) {
				return PlayerType.NONE;
			}
		}
		return potentialWinner;
	}

	private PlayerType checkForwardDiagonal() {

		if (this.currentRoundSteps[moveCount - 1][0]
				+ this.currentRoundSteps[moveCount - 1][1] != size + 1) {
			return PlayerType.NONE;
		}

		PlayerType potentialWinner = board[this.currentRoundSteps[moveCount - 1][0] - 1][this.currentRoundSteps[moveCount - 1][1] - 1];
		for (int row = 0; row < size; row++) {
			if (potentialWinner != board[row][size - row - 1]) {
				return PlayerType.NONE;
			}
		}
		return potentialWinner;
	}

	public boolean isGameOver() {

		if ((winner = checkRow()) != PlayerType.NONE) {
			return true;
		}

		if ((winner = checkCol()) != PlayerType.NONE) {
			return true;
		}
		if ((winner = checkBackwardDiagonal()) != PlayerType.NONE) {
			return true;
		}
		if ((winner = checkForwardDiagonal()) != PlayerType.NONE) {
			return true;
		}

		if (moveCount == size * size) {
			winner = PlayerType.NONE;
			return true;
		}

		return false;
	}

	public void tellWhoIsTheWinner() {
		// System.out.println("\n\nScore Block **************");
		switch (winner) {
		case MACHINE:
			System.out.println("Info: Machine is the winner.");
			break;
		case PERSON:
			System.out.println("Info: You are the winner.");
			break;
		default:
			System.out.println("Info: No winner.");
			break;
		}

		print();

		// System.out.println("Block End**************");
	}

	@Override
	public String toString() {
		sb().append("-------------------------------\n");
		sb.append("size:").append(size).append(", whoMoveFirst:")
				.append(players[0].playerType().description())
				.append(", moveCount:").append(moveCount).append("\n");

		for (int row = 0; row < size; row++) {
			if (row > 0) {
				sb.append("-----\n");
			}
			for (int col = 0; col < size; col++) {
				switch (board[row][col]) {
				case MACHINE:
					sb.append(PlayerType.MACHINE.mark());
					break;
				case PERSON:
					sb.append(PlayerType.PERSON.mark());
					break;
				default:
					sb.append(PlayerType.NONE.mark());
					break;
				}

				sb.append(col == size - 1 ? "\n" : "|");
			}
		}

		sb.append("-------------------------------\n");

		return sb.toString();
	}

	public void print() {
		System.out.println(toString());
	}

	public boolean isPersonTurn() {
		return players[this.moveCount % 2].playerType() == PlayerType.PERSON;
	}

	public boolean isMachineLose() {
		return winner == PlayerType.PERSON;
	}

	private void addSteps(List<int[][]> loseSteps, int[][] ss, String msg)
			throws IOException {
		loseSteps.add(ss);
		String s = stepString(ss);
		System.out.println(msg + s);
	}

	public void recordCurrentStepsAndRotateSteps() throws IOException {
		Player player = players[this.moveCount % 2];
		String msg = player.playerType().description() + " Record Steps: ";

		List<int[][]> loseSteps = player.loseSteps;
		addSteps(loseSteps, currentRoundSteps, msg);

		int[][] r90 = this.currentRoundSteps;
		for (int i = 0; i < 3; i++) {
			r90 = rotate90CounterClock(r90);
			addSteps(loseSteps, r90, msg);
		}
	}

	private String stepString(int[][] steps) {
		sb();
		for (int i = 0; i < steps.length; i++) {
			if (steps[i][0] == 0) {
				break;
			}

			if (i > 0) {
				sb.append("-");
			}
			sb.append("(").append(steps[i][0]).append(",").append(steps[i][1])
					.append(")");

		}
		return sb.toString();
	}

	public List<String> stepString(List<int[][]> steps) {
		List<String> l = new ArrayList<String>();
		for (int[][] a : steps) {
			l.add(stepString(a));
		}
		return l;
	}
}
