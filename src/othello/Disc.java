package othello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Creates Disc object which is button with color and coordinates
 *
 * @author AdrienBaldwin
 */
public class Disc extends JButton implements ActionListener {
	
	private static final long serialVersionUID = -4106813285892747130L;
	private String color;
	private final int x;
	private final int y;

	/**
	 * Constructs individual Disc objects with color and coordinates
	 * @param color color of Disc
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Disc (String color, int x, int y) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.setSize(new Dimension(80 , 80));
		this.setOpaque(true);
		this.setIconTextGap(0);
		this.setBounds(new Rectangle(0, 0, 80, 80));
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setIcon(new ImageIcon("src/resources/" + this.color + "Disc.png"));
		this.setDisabledIcon(new ImageIcon("src/resources/" + this.color + "Disc.png"));
		this.setVisible(true);
		this.addActionListener(this);
	}

	/**
	 * Sets color of Disc
	 * @param newColor new color of Disc
	 */
	public void setDiscColor(String newColor) {
		this.color = newColor;
	}

	/**
	 * Returns color of Disc
	 * @return color of Disc
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * When Disc is clicked, runs the following
	 * 1. Checks color of disc and changes created row if valid
	 * 2. Clears board and repaints it
	 * 3. Switches player turn
	 * 4. Displays new possible moves
	 * 5. Refreshes turn info for new turn
	 * @param e Disc clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (this.color.equals("Black") || this.color.equals("White"))
				return;
			if (this.color.equals("Yellow") && Gameplay.currentBoard.turn == 1) {
				Gameplay.changeRow(Gameplay.createRow(this.x, this.y, "Black"), "Black");
			}
			if (this.color.equals("Yellow") && Gameplay.currentBoard.turn == 0) {
				Gameplay.changeRow(Gameplay.createRow(this.x, this.y, "White"),"White");
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		Gameplay.clearBoard();
		Gameplay.currentBoard.revalidate();
		Gameplay.currentBoard.repaint();
		Gameplay.switchTurn();

		try {
			Gameplay.displayPossibleMoves();
			Board.txtPlayerScores.setText(Gameplay.updateScore());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
