package rcs.endproject.battleship;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Board {

	private Scanner userInput;

	private String[][] gameBoardP1 = new String[10][10];
	private String[][] gameBoardP2 = new String[10][10];
	private int[][] shipCoordsP1 = new int[6][13];
	private int[][] shipCoordsP2 = new int[6][13];
	private String[][] gameBoard = new String[10][10];

	public void printBoard(int playerNum) {

		System.out.println(" |   0|   1|   2|   3|   4|   5|   6|   7|   8|   9|");
		System.out.println("----------------------------------------------------");
		if (playerNum == 0) {
			this.gameBoard = gameBoardP1;
		} else {
			this.gameBoard = gameBoardP2;
		}
		for (int i = 0; i <= 9; i++) {
			System.out.print(i + "|");
			for (int j = 0; j <= 9; j++) {
				if (gameBoard[i][j] == null) {
					gameBoard[i][j] = " ~ ~";
				}

				System.out.print(gameBoard[i][j] + "|");
			}
			System.out.println("");
			System.out.println("----------------------------------------------------");
		}
		System.out.println("");
	}

	public void printHiddenBoard(int playerNum) {

		this.gameBoard = getGameBoard(playerNum);

		System.out.println(" |   0|   1|   2|   3|   4|   5|   6|   7|   8|   9|");
		System.out.println("----------------------------------------------------");
		for (int i = 0; i <= 9; i++) {
			System.out.print(i + "|");
			for (int j = 0; j <= 9; j++) {
				if (gameBoard[i][j].equals(" HIT") || gameBoard[i][j].equals("MISS")) {

				} else {
					gameBoard[i][j] = " ~ ~";
				}

				System.out.print(gameBoard[i][j] + "|");
			}
			System.out.println("");
			System.out.println("----------------------------------------------------");
		}
		System.out.println("");
		if (playerNum == 0) {
			setGameBoardP1(gameBoard);
		} else {
			setGameBoardP2(gameBoard);
		}
	}

	public void deployShipOnBoard(int playerNum) {

		for (int z = 0; z < Ship.ships.length; z++) {
			printBoard(playerNum);

			this.userInput = new Scanner(System.in);
			System.out.println("Player" + (playerNum + 1) + " turn!! ");
			System.out.println("Input start coordinate of the " + Ship.ships[z].getShipName() + ": ");
			String shipStartCoord = userInput.nextLine();
			System.out.println("Input end coordinate of the ship " + Ship.ships[z].getShipName() + ": ");
			String shipEndCoord = userInput.nextLine();
			if (isInputValid(shipStartCoord, shipEndCoord) == false
					|| shipsAreOverlapping(shipStartCoord, shipEndCoord) == true
					|| isShipLengthCorrect(shipStartCoord, shipEndCoord, z) == false) {
				do {
					System.out.println("Invalid coordinates or ships are overlapping, try again!");
					System.out.println("Input start coordinate of the " + Ship.ships[z].getShipName() + ": ");
					shipStartCoord = userInput.nextLine();
					System.out.println("Input end coordinate of the ship " + Ship.ships[z].getShipName() + ": ");
					shipEndCoord = userInput.nextLine();
				} while (isInputValid(shipStartCoord, shipEndCoord) == false
						|| shipsAreOverlapping(shipStartCoord, shipEndCoord) == true
						|| isShipLengthCorrect(shipStartCoord, shipEndCoord, z) == false);
			}

			shipCoords(shipStartCoord, shipEndCoord, playerNum, z);

		}
		printBoard(playerNum);
		if (playerNum == 0) {
			setGameBoardP1(gameBoard);
		} else {
			setGameBoardP2(gameBoard);
		}

	}

	public void shipCoords(String shipStartCoord, String shipEndCoord, int playerNum, int z) {

		for (int a = 0; a < 6; a++) {
			for (int b = 0; b < 13; b++) {
				if (getShipCoords(playerNum)[a][b] == 0) {
					getShipCoords(playerNum)[a][b] = -1;
				}
			}
		}

		if (Integer.parseInt(shipStartCoord) > Integer.parseInt(shipEndCoord)) {
			String temp = shipEndCoord;
			shipEndCoord = shipStartCoord;
			shipStartCoord = temp;
		}
		int a = 0;
		int b = 1;
		String shipStartCoordX = shipStartCoord.substring(0, 1);
		String shipStartCoordY = shipStartCoord.substring(1);
		String shipEndCoordX = shipEndCoord.substring(0, 1);
		String shipEndCoordY = shipEndCoord.substring(1);

		if (Integer.parseInt(shipStartCoordX) < Integer.parseInt(shipEndCoordX)) {
			for (int i = 0; i <= Integer.parseInt(shipEndCoordX) - Integer.parseInt(shipStartCoordX); i++) {
				gameBoard[Integer.parseInt(shipEndCoordY)][Integer.parseInt(shipStartCoordX) + i] = " |> ";
				getShipCoords(playerNum)[Ship.ships[z].id][a] = Integer.parseInt(shipEndCoordY);
				getShipCoords(playerNum)[Ship.ships[z].id][b] = Integer.parseInt(shipStartCoordX) + i;
				a = a + 2;
				b = b + 2;

			}
		} else {
			for (int i = 0; i <= Integer.parseInt(shipEndCoordY) - Integer.parseInt(shipStartCoordY); i++) {
				gameBoard[Integer.parseInt(shipStartCoordY) + i][Integer.parseInt(shipStartCoordX)] = " |> ";
				getShipCoords(playerNum)[Ship.ships[z].id][a] = Integer.parseInt(shipStartCoordY) + i;
				getShipCoords(playerNum)[Ship.ships[z].id][b] = Integer.parseInt(shipStartCoordX);

				a = a + 2;
				b = b + 2;
			}
		}

	}

	private boolean isShipLengthCorrect(String shipStartCoord, String shipEndCoord, int z) {
		if (Integer.parseInt(shipStartCoord) > Integer.parseInt(shipEndCoord)) {
			String temp = shipEndCoord;
			shipEndCoord = shipStartCoord;
			shipStartCoord = temp;
		}
		int sub = 0;
		String shipStartCoordX = shipStartCoord.substring(0, 1);
		String shipStartCoordY = shipStartCoord.substring(1);
		String shipEndCoordX = shipEndCoord.substring(0, 1);
		String shipEndCoordY = shipEndCoord.substring(1);
		if (Integer.parseInt(shipStartCoordX) < Integer.parseInt(shipEndCoordX)) {
			sub = Integer.parseInt(shipEndCoordX) - Integer.parseInt(shipStartCoordX);
		} else {
			sub = Integer.parseInt(shipEndCoordY) - Integer.parseInt(shipStartCoordY);
		}
		if ((sub + 1) == Ship.ships[z].length) {
			return true;
		} else {

			return false;
		}
	}

	public boolean shipsAreOverlapping(String shipStartCoord, String shipEndCoord) {
		if (Integer.parseInt(shipStartCoord) > Integer.parseInt(shipEndCoord)) {
			String temp = shipEndCoord;
			shipEndCoord = shipStartCoord;
			shipStartCoord = temp;
		}
		String shipStartCoordX = shipStartCoord.substring(0, 1);
		String shipStartCoordY = shipStartCoord.substring(1);
		String shipEndCoordX = shipEndCoord.substring(0, 1);
		String shipEndCoordY = shipEndCoord.substring(1);
		if (Integer.parseInt(shipStartCoordX) < Integer.parseInt(shipEndCoordX)) {
			for (int i = 0; i <= Integer.parseInt(shipEndCoordX) - Integer.parseInt(shipStartCoordX); i++) {
				if (gameBoard[Integer.parseInt(shipEndCoordY)][Integer.parseInt(shipStartCoordX) + i].equals(" |> ")) {
					return true;
				}
			}
		} else {

			for (int i = 0; i <= Integer.parseInt(shipEndCoordY) - Integer.parseInt(shipStartCoordY); i++) {
				if (gameBoard[Integer.parseInt(shipStartCoordY) + i][Integer.parseInt(shipStartCoordX)]
						.equals(" |> ")) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isNotValidCoordinate(int x, int y, int playerNum) {

		return (getGameBoard(playerNum)[y][x].equals(" HIT") || getGameBoard(playerNum)[y][x].equals("MISS")
				|| getGameBoard(playerNum)[y][x].equals(" |> ") || getGameBoard(playerNum)[y][x] == null);
	}

	public boolean isInputValid(String shipStartCoord, String shipEndCoord) {

		return (shipStartCoord != null && shipEndCoord != null && Pattern.matches("[a-zA-Z]+", shipStartCoord) == false
				&& Pattern.matches("[a-zA-Z]+", shipEndCoord) == false && shipStartCoord.length() == 2
				&& shipEndCoord.length() == 2);
	}

	public void setGameBoardP1(String[][] gameBoard) {

		this.gameBoardP1 = gameBoard;
	}

	public void setGameBoardP2(String[][] gameBoard) {
		this.gameBoardP2 = gameBoard;
	}

	public String[][] getGameBoard(int playerNum) {
		if (playerNum == 0) {
			return gameBoardP1;
		} else {
			return gameBoardP2;
		}

	}

	public int[][] getShipCoords(int playerNum) {
		if (playerNum == 0) {
			return shipCoordsP1;
		} else {
			return shipCoordsP2;
		}

	}

}
