package com.jie.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsoleGame {
	@Value("${filepath1}")
	private String filepath1;
	@Value("${filepath2}")
	private String filepath2;

	@Autowired
	private GameBoard gameBoard;

	@Autowired
	private Scanner scanIn;

	@PreDestroy
	public void shutdown() {
		if (scanIn != null) {
			scanIn.close();
			scanIn = null;
		}
	}

	@PostConstruct
	public void start() {

		Player[] players = { new Machine(), new Person() };

		String[] filePaths = { filepath1, filepath2 };

		BufferedWriter fos = null;
		BufferedReader fis = null;
		String inStr;

		try {
			gameBoard.setBoardType(BoardType.CONSOLE);
			for (int i = 0; i < 2; i++) {
				players[i].consoleReader(scanIn);
				gameBoard.setPlayer(players[i], i);
				players[i].setGameBoard(gameBoard);
				System.out.println("load " + filePaths[i]);
				if (new File(filePaths[i]).exists()) {
					fis = new BufferedReader(new FileReader(filePaths[i]));
					if (fis != null) {
						gameBoard.init(fis, players[i]);
						fis.close();
						fis = null;
					}
				}
			}

			while (true) {
				System.out
						.println("================== Input rules ==================");
				System.out
						.println("Input rules:\nPress enter key quit the game or start a new game.\nEnter row,col for your choice. For example: 1,2 if you want to take row 1 and column 2.\n");
				System.out
						.println("================================================");

				startNewGame(scanIn, gameBoard, fos);

				System.out
						.println("Do you want another game? (Press enter key for yes, OR n to end game.)");

				inStr = scanIn.nextLine();
				if (inStr == null || inStr.isEmpty()) {
					continue;
				} else {
					for (int i = 0; i < 2; i++) {
						fos = new BufferedWriter(new FileWriter(filePaths[i]));
						List<String> l = gameBoard
								.stepString(players[i].loseSteps);
						for (String s : l) {
							fos.write(s);
							fos.newLine();
							fos.flush();
						}
					}
					System.out.println("It's fun to play with you. Bye ...");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fis = null;
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fos = null;
			}
		}
	}

	public static void startNewGame(Scanner scanIn, GameBoard gb,
			BufferedWriter fos) {
		// String inStr;
		try {
			// do {
			// System.out
			// .println("What is your tic tac toe size? (e.g. 3, 5, 7) ");
			// size = convertToInt(scanIn.nextLine());
			// if (size > 1) {
			// // 2 or even number will not check diagonal
			// Break;
			// }
			// } while (true);

			// System.out.println("Do you want to start first?(y)");
			// inStr = scanIn.nextLine();
			// if ("y".equalsIgnoreCase(inStr)) {
			// gm.start(size, GameBoard.Player.YOU);
			// } else {
			gb.start();
			// }

			while (true) {
				if (gb.move()) {

					if (gb.isGameOver()) {
						gb.tellWhoIsTheWinner();
						gb.recordCurrentStepsAndRotateSteps();
						break;
					}
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
