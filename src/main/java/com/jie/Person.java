package com.jie;

public class Person extends Player {

	@Override
	public PlayerType playerType() {
		return PlayerType.PERSON;
	}

	@Override
	public int[][] matchPreviousBadMove() {
		// a person has own judgement
		return null;
	}

	@Override
	public boolean move() {
		while (true) {
			System.out.println("Please enter your choice ...");
			String inStr = scanIn.nextLine();

			if (inStr == null || inStr.isEmpty()) {
				System.out.println("Game is over.");
				return false;
			}

			int[] xy = convertToInt(inStr);
			if (gameBoard.validateYourMove(xy, true)) {
				gameBoard.setGrid(xy[0] - 1, xy[1] - 1, false);
				// print();
				return true;
			}
		}
	}

	private int[] convertToInt(String s) {
		try {
			int[] xy = new int[2];
			String[] ss = s.split(",");
			if (ss == null || ss.length != 2) {
				return null;
			}
			xy[0] = Integer.parseInt(ss[0]);
			xy[1] = Integer.parseInt(ss[1]);

			return xy;
		} catch (Exception e) {
			return null;
		}
	}

}
