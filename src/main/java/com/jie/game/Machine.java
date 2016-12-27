package com.jie.game;

import java.util.Random;

public class Machine extends Player {
	private Random r = new Random();

	@Override
	public PlayerType playerType() {
		return PlayerType.MACHINE;
	}

	@Override
	public int[][] matchPreviousBadMove() {
		int moveCount = gameBoard.getMoveCount();
		if (moveCount < 5) {
			return null;
		}

		boolean found = false;
		for (int[][] bad : loseSteps) {
			int badMoveCount = gameBoard.moveCount(bad);
			if (badMoveCount != moveCount + 2) {
				found = false;
				continue;
			}

			found = true;
			for (int i = 0; i < moveCount; i++) {
				if (gameBoard.getCurrentRoundSteps()[i][0] != bad[i][0]
						|| gameBoard.getCurrentRoundSteps()[i][1] != bad[i][1]) {
					found = false;
					break;
				}
			}

			// matched
			if (found) {
				int[][] badMove = new int[1][2];
				bad[0][0] = bad[moveCount][0];
				bad[0][1] = bad[moveCount][1];

				return badMove;
			}
		}

		return null;
	}

	@Override
	public boolean move() {
		// this can be done better if I have more time to design and use tree to
		// trace
		int size = gameBoard.getSize();
		int[][] badMove = matchPreviousBadMove();
		int leftover = size * size - gameBoard.getMoveCount();
		int pick = leftover > 1 ? r.nextInt(leftover) : 0;
		int aiRow = 0, aiCol = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (!gameBoard.isGridSet(row, col)) {
					if (leftover == 1) {
						gameBoard.setGrid(row, col, true);
						return true;
					}

					pick--;
					if (pick < 0) {
						if (badMove == null || badMove[0][0] != row + 1
								|| badMove[0][1] != col + 1) {
							gameBoard.setGrid(row, col, true);
							return true;
						}

						if (gameBoard.isConsoleGame()) {
							System.out
									.println("matched badMove:("
											+ badMove[0][0] + ","
											+ badMove[0][1] + ")");
						}

						if (aiRow > 0) {
							gameBoard.setGrid(aiRow, aiCol, true);
							return true;
						}

						pick++;

					} else {
						aiRow = row;
						aiCol = col;
					}

				}
			}
		}

		return false;
	}

}
