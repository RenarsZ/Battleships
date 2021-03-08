package rcs.endproject.battleship;

import java.util.Scanner;

public class GameStart {


	private Scanner userInput;
	public Board board;

	public void start() {
		this.board = new Board();
		userInput = new Scanner(System.in);

		board.deployShipOnBoard(0);
		board.deployShipOnBoard(1);

		

		int shipLivesP1 = 21;// 21
		int shipLivesP2 = 21;
		int playerNum = 0;
		while ((shipLivesP1 != 0) && (shipLivesP2 != 0)) {

			board.printHiddenBoard(playerNum);
			System.out.println("It's Player" + (playerNum + 1) + " turn to shoot!");
			System.out.println("Input coordinate x: ");
			int x = userInput.nextInt();
			System.out.println("Input coordinate y: ");
			int y = userInput.nextInt();
			if (board.isNotValidCoordinate(x, y, playerNum)) {
				do {
					System.out.println("You have already taken a shot at those coordinates, try again!");
					System.out.println("Input coordinate x: ");
					x = userInput.nextInt();
					System.out.println("Input coordinate y: ");
					y = userInput.nextInt();
				} while (board.isNotValidCoordinate(x, y, playerNum));
			}

			if (isShipHit(playerNum, x, y)) {

				board.getGameBoard(playerNum)[y][x] = " HIT";
				board.printHiddenBoard(playerNum);

				if (playerNum == 0) {
					shipLivesP2--;
					board.setGameBoardP1(board.getGameBoard(playerNum));
				} else {
					board.setGameBoardP2(board.getGameBoard(playerNum));
					shipLivesP1--;
				}
			} else {
				board.getGameBoard(playerNum)[y][x] = "MISS";
				board.printHiddenBoard(playerNum);
				if (playerNum == 0) {
					board.setGameBoardP1(board.getGameBoard(playerNum));
				} else {
					board.setGameBoardP2(board.getGameBoard(playerNum));
				}
			}
			if (playerNum == 0) {
				playerNum = playerNum + 1;
			} else {
				playerNum--;
			}

		}

		if (shipLivesP1 == 0) {
			System.out.println("Congratulations Player2!!! You have won the game!!");
		} else {
			System.out.println("Congratulations Player1!!! You have won the game!!");
		}
	}
	public boolean isShipHit(int playerNum, int x, int y) {
		if (playerNum == 0) {
			playerNum++;
		} else {
			playerNum--;
		}
		for (int i = 0; i < board.getShipCoords(playerNum).length; i++) {
			for (int j = 0; j < Ship.ships[i].length * 2; j++) {
				if ((board.getShipCoords(playerNum)[Ship.ships[i].id][j] == y)
						&& (board.getShipCoords(playerNum)[Ship.ships[i].id][j + 1] == x)) {
					return true;
				}

			}
		}
		return false;
	}

}
