package othello;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.JFrame;

/**
 * Class containing methods for functionality of Othello game
 *
 * @author AdrienBaldwin
 */
public class Gameplay extends JFrame {

	private static final long serialVersionUID = 3798057624920462417L;
	static int countWhiteDiscs;
	static int countBlackDiscs;
	static int countYellowDiscs;
	static Board currentBoard;
	static int[][] intBoard;
	private static final int UP = 0;
	private static final int UP_RIGHT = 1;
	private static final int RIGHT = 2;
	private static final int DOWN_RIGHT = 3;
	private static final int DOWN = 4;
	private static final int DOWN_LEFT = 5;
	private static final int LEFT = 6;
	private static final int UP_LEFT = 7;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					startGame();
					currentBoard.setVisible(true);
					displayPossibleMoves();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initializes board with first four pieces
	 * @throws IOException if not found
	 */
	public static void startGame() throws IOException {
		currentBoard = new Board();
		currentBoard.addDiscToBoard("White", 3, 3);
		currentBoard.addDiscToBoard("Black", 3, 4);
		currentBoard.addDiscToBoard("White", 4, 4);
		currentBoard.addDiscToBoard("Black", 4, 3);
	}

	/**
	 * Creates yellow buttons for possible moves and enables them.
	 * @throws IOException if IO not found
	 */
	public static void displayPossibleMoves() throws IOException {
		intBoard = checkAllDirections(currentBoard.board);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (intBoard[i][j] != 0) {
					currentBoard.addDiscToBoard("Yellow", i, j);
				}
			}
		}
	}

	/**
	 * Removes existing suggested move spaces to allow for re-running of updated board.
	 */
	public static void clearBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (currentBoard.board[i][j].getColor().equals("Yellow")) {
					currentBoard.board[i][j].setDiscColor("Grey");
					currentBoard.board[i][j].setEnabled(false);
				}
			}
		}
		currentBoard.revalidate();
		currentBoard.repaint();
	}

	/**
	 * Checks if button is able to be set to new color for a row from the direction that was
	 * clicked.
	 * @param x clicked x coordinate
	 * @param y clicked y coordinate
	 * @throws IOException if IO not found
	 */
	public static int[][] createRow(int x, int y, String color) throws IOException {
		int[][] row = new int[8][8];
		if (currentBoard.board[x][y].getColor().equals("Yellow"))
		row[x][y] = 1;

		//Up
		for (int i = x - 2; i >= 0; i--) {
			if (withinBounds(i, y) && currentBoard.board[i][y].isEnabled() && currentBoard.board[x - 1][y].isEnabled()
					&& currentBoard.board[i][y].getColor().equals(color) && !currentBoard.board[i][y].getColor().equals("Yellow")) {
				for (int j = x - 1; j >= i; j--) {
					row[j][y] = 1;
				} break;
			}
		}

		//Down
		for (int i = x + 2; i <= 7; i++) {
			if (withinBounds(i, y) && currentBoard.board[i][y].isEnabled() && currentBoard.board[x + 1][y].isEnabled()
					&& currentBoard.board[i][y].getColor().equals(color) && !currentBoard.board[i][y].getColor().equals("Yellow")) {
				for (int j = x + 1; j <= i; j++) {
					row[j][y] = 1;
				} break;
			}
		}

		//Left
		for (int i = y - 2; i >= 0; i--) {
			if (withinBounds(x, i) && currentBoard.board[x][i].isEnabled() && currentBoard.board[x][y - 1].isEnabled()
					&& currentBoard.board[x][i].getColor().equals(color) && !currentBoard.board[x][i].getColor().equals("Yellow")) {
				for (int j = y - 1; j >= i; j--) {
					row[x][j] = 1;
				} break;
			}
		}

		//Right
		for (int i = y + 2; i <= 7; i++) {
			if (withinBounds(x, i) && currentBoard.board[x][i].isEnabled() && currentBoard.board[x][y + 1].isEnabled()
					&& currentBoard.board[x][i].getColor().equals(color) && !currentBoard.board[x][i].getColor().equals("Yellow")) {
				for (int j = y + 1; j <= i; j++) {
					row[x][j] = 1;
				} break;
			}
		}

		//Down-Left
		for (int i = 2; i <= 8; i++) {
			if (withinBounds(x - i, y + i) && currentBoard.board[x - i][y + i].isEnabled() && currentBoard.board[x - 1][y + 1].isEnabled()
					&& currentBoard.board[x - i][y + i].getColor().equals(color) && !currentBoard.board[x - i][y + i].getColor().equals("Yellow")) {
				for (int j = 1; j <= i; j++) {
						row[x - j][y + j] = 1;
				} break;
			}
		}

		//Down-Right
		for (int i = 2; i <= 7; i++) {
			if (withinBounds(x + i, y + i) && currentBoard.board[x + i][y + i].isEnabled() && currentBoard.board[x + 1][y + 1].isEnabled()
					&& currentBoard.board[x + i][y + i].getColor().equals(color) && !currentBoard.board[x + i][y + i].getColor().equals("Yellow")) {
				for (int j = 1; j <= i; j++) {
					row[x + j][y + j] = 1;
				} break;
			}
		}

		//Up-Left
		for (int i = 2; i <= 7; i++) {
			if (withinBounds(x - i, y - i) && currentBoard.board[x - i][y - i].isEnabled() && currentBoard.board[x - 1][y - 1].isEnabled()
					&& currentBoard.board[x - i][y - i].getColor().equals(color) && !currentBoard.board[x - i][y - i].getColor().equals("Yellow")) {
				for (int j = 1; j <= i; j++) {
					row[x - j][y - j] = 1;
				} break;
			}
		}

		//Up-Right
		for (int i = 2; i <= 7; i++) {
			if (withinBounds(x + i, y - i) && currentBoard.board[x + i][y - i].isEnabled() && currentBoard.board[x + 1][y - 1].isEnabled()
					&& currentBoard.board[x + i][y - i].getColor().equals(color) && !currentBoard.board[x + i][y - i].getColor().equals("Yellow")) {
				for (int j = 1; j <= i; j++) {
					row[x + j][y - j] = 1;
				} break;
			}
		}
		return row;
	}

	/**
	 * Sets all discs from the yellow disc clicked to the next disc of the same color to the color of the current player
	 * @param grid grid of integers.
	 * @param color color for row to be updated to.
	 * @throws IOException if not found
	 */
	public static void changeRow(int[][] grid, String color) throws IOException {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] == 1)
					currentBoard.addDiscToBoard(color, i, j);
			}
		}
	}

	/**
	 * Checks whether a coordinate is within the bounds of the grid/board
	 * @param i x coordinate
	 * @param j y coordinate
	 * @return true if within grid/board
	 */
	public static boolean withinBounds(int i, int j){
		return 0 <= i && i < 8 && 0 <= j && j < 8;
	}

	/**
	 * Checks whether the next two discs in any direction are opposite colors.
	 * If they are the same and it's the opposite turn, it returns 0 to be run again.
	 * @param this_i x coordinate of first disc
	 * @param this_j y coordinate of first disc
	 * @param that_i x coordinate of second disc
	 * @param that_j y coordinate of second disc
	 * @return 1 if
	 */
	public static int isOppCol(int this_i, int this_j, int that_i, int that_j) {
		if(withinBounds(this_i, this_j) && withinBounds(that_i, that_j)) {
		Disc this_disk = currentBoard.board[this_i][this_j];
		Disc that_disk = currentBoard.board[that_i][that_j];
		if (!that_disk.isEnabled()) return -1;
		if (!this_disk.isEnabled()) return -1;
		if (this_disk.getColor().equals("White") && that_disk.getColor().equals("White") && currentBoard.turn == 1) return 0;
		if (this_disk.getColor().equals("Black") && that_disk.getColor().equals("Black") && currentBoard.turn == 0) return 0;
		if (this_disk.getColor().equals("White") && that_disk.getColor().equals("Black") && currentBoard.turn == 1) return 1;
		if (this_disk.getColor().equals("Black") && that_disk.getColor().equals("White") && currentBoard.turn == 0) return 1;
		}
		return -1;
	}

	/**
	 * Recursively checks a double array of valid moves and sets value in grid to 1 if a move is possible
	 * @param this_row row that the current location is on
	 * @param this_col column that the current location is on
	 * @param that_row offset row being checked
	 * @param that_col offset column being checked
	 * @param diag_type 0 if right, 1 if up, 2 if diagonal (down-right), 3 if diagonal (up-right)
	 */
	public static boolean checkNeighbor(int this_row, int this_col, int that_row, int that_col, int diag_type) {
		switch(isOppCol(this_row, this_col, that_row, that_col)) {
		case 0:
			if (diag_type == UP)
				return checkNeighbor(this_row - 1, this_col, that_row - 1, that_col, diag_type);
			if (diag_type == UP_RIGHT)
				return checkNeighbor(this_row -1, this_col + 1, that_row - 1, that_col + 1, diag_type);
			if (diag_type == RIGHT)
				return checkNeighbor(this_row, this_col + 1, that_row, that_col + 1, diag_type);
			if (diag_type == DOWN_RIGHT)
				return checkNeighbor(this_row + 1, this_col + 1, that_row + 1, that_col + 1, diag_type);
			if (diag_type == DOWN)
				return checkNeighbor(this_row + 1, this_col, that_row + 1, that_col, diag_type);
			if (diag_type == DOWN_LEFT)
				return checkNeighbor(this_row + 1, this_col - 1, that_row + 1, that_col - 1, diag_type);
			if (diag_type == LEFT)
				return checkNeighbor(this_row, this_col - 1, that_row, that_col - 1, diag_type);
			if (diag_type == UP_LEFT)
				return checkNeighbor(this_row - 1, this_col - 1, that_row - 1, that_col - 1, diag_type);
		case 1:
			return true;
		}
		return false;
	}

	/**
	 * Checks a grid of Discs to see which spaces are disabled and able to be enabled.
	 * @param grid grid of Discs (buttons)
	 * @param row row of current disc
	 * @param col column of current disc
	 * @param that_row row of current disc to be checked
	 * @param that_col column of current disc to be checked
	 * @return true if is a valid spot for a possible move to be made
	 */
	public static boolean isValidEnabled(Disc[][] grid, int row, int col, int that_row, int that_col,int diag_type){
		if (!withinBounds(row, col)) return false;
		if (!withinBounds(that_row, that_col)) return false;
		if (!grid[row][col].isEnabled()) return false;
		if (!grid[that_row][that_col].isEnabled()) return false;
		if (currentBoard.turn == 0 && grid[that_row][that_col].getColor().equals("Black") && grid[row][col].getColor().equals("White"))
			return false;
		if (currentBoard.turn == 1 && grid[that_row][that_col].getColor().equals("White") && grid[row][col].getColor().equals("Black"))
			return false;
		return checkNeighbor(row, col, that_row, that_col, diag_type);
	}

	/**
	 * Checks all directions within current board to look for possible moves
	 * Then creates and returns 8 by 8 grid of integers with values of 1 if possible move
	 * @param grid grid of Discs
	 * @return 8 by 8 grid of integers
	 */
	public static int[][] checkAllDirections(Disc[][] grid) {
		int[][] validMoves = new int[8][8];
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {

				if (!grid[row][col].isEnabled()) {
					//Check upwards
					if (isValidEnabled(grid, row - 1, col, row - 2, col, UP)) validMoves[row][col] = 1;

					//Check upper right
					if (isValidEnabled(grid, row - 1, col + 1, row - 2, col + 2, UP_RIGHT)) validMoves[row][col] = 1;

					//Check right
					if (isValidEnabled(grid, row, col + 1, row, col + 2, RIGHT)) validMoves[row][col] = 1;

					//Check lower right
					if (isValidEnabled(grid, row + 1, col + 1, row + 2, col + 2, DOWN_RIGHT)) validMoves[row][col] = 1;
					
					//Check downwards
					if (isValidEnabled(grid, row + 1, col, row + 2, col, DOWN)) validMoves[row][col] = 1;
					
					//Check lower left
					if (isValidEnabled(grid, row + 1, col - 1, row + 2, col - 2, DOWN_LEFT)) validMoves[row][col] = 1;
					
					//Check left
					if (isValidEnabled(grid, row, col - 1, row, col - 2, LEFT)) validMoves[row][col] = 1;
					
					//Check upper left
					if (isValidEnabled(grid,row - 1, col - 1, row - 2, col - 2, UP_LEFT)) validMoves[row][col] = 1;
				}
			}
		}
		return validMoves;
	}

	/**
	 * Uses string builder to create and return scoreboard text
	 * @return string of scoreboard
	 */
	public static String updateScore() {
		StringBuilder sb = new StringBuilder();
		countBlackDiscs = 0;
		countWhiteDiscs = 0;
		countYellowDiscs = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (currentBoard.board[i][j].isEnabled() && currentBoard.board[i][j].getColor().equals("Black"))
					countBlackDiscs++;
				if (currentBoard.board[i][j].isEnabled() && currentBoard.board[i][j].getColor().equals("White"))
					countWhiteDiscs++;
				if (currentBoard.board[i][j].getColor().equals("Yellow"))
					countYellowDiscs++;
			}
		}
		if (countYellowDiscs == 0)
			endGame();
		sb.append("\nBlack: ").append(countBlackDiscs);
		sb.append("\nWhite: ").append(countWhiteDiscs);
		return sb.toString();
	}

	/**
	 * Switches turn in Board class and updates player turn label to reflect new turn
	 */
	public static void switchTurn() {
		if (currentBoard.turn == currentBoard.WHITE) {
			currentBoard.turn = currentBoard.BLACK;
			Board.lblPlayerTurn.setText("Black Turn");
		}
		else {
			currentBoard.turn = currentBoard.WHITE;
			Board.lblPlayerTurn.setText("White Turn");
		}
	}

	/**
	 * Checks to see which player has more Discs and updates text to indicate who won
	 * Then runs game over clock which allows screen to close after 5 seconds.
	 */
	public static void endGame() {
		if (countBlackDiscs > countWhiteDiscs)
			Board.lblPlayerTurn.setText("Black Wins!");
		if (countBlackDiscs < countWhiteDiscs)
			Board.lblPlayerTurn.setText("White Wins!");
		if (countBlackDiscs == countWhiteDiscs)
			Board.lblPlayerTurn.setText("TIE...");
		Timer timesUp = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentBoard.exit();
			}
		});
		timesUp.start();
	}
}
