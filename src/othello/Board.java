package othello;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.border.LineBorder;

/**
 * Creates an 8 by 8 Othello Board object with title, turn label, and score label.
 *
 * @author AdrienBaldwin
 */
public class Board extends JFrame {

	private static final long serialVersionUID = -8529214959070798188L;
	public JPanel contentPane;
	public JLabel lblGameLabel;
	public Disc[][] board;
	public JPanel titlePanel;
	public JPanel gameplayPanel;
	public JPanel boardPanel;
	public final int WHITE = 0;
	public final int BLACK = 1;
	public int turn = BLACK;
	public JPanel playerTurnPanel;
	public static JLabel lblPlayerTurn;
	public static JTextArea txtPlayerScores;
	public static JLabel lblAdrien;

	/**
	 * Constructs GUI for Othello Game
	 */
	public Board() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		//Creates panel that holds game title label
		titlePanel = new JPanel();
		titlePanel.setBackground(new Color(0, 100, 0));
		contentPane.add(titlePanel, BorderLayout.NORTH);

		//Creates game title label
		lblGameLabel = new JLabel("Othello");
		lblGameLabel.setFont(new Font("Goudy Stout", Font.PLAIN, 63));
		lblGameLabel.setForeground(Color.WHITE);
		titlePanel.add(lblGameLabel);

		//Creates panel which holds board and game info labels
		gameplayPanel = new JPanel();
		gameplayPanel.setBackground(new Color(0, 100, 0));
		gameplayPanel.setBounds(0, 0, 1000, 656);
		gameplayPanel.setLayout(new BorderLayout(0, 0));
		contentPane.add(gameplayPanel, BorderLayout.CENTER);

		//Creates Othello board
		boardPanel = new JPanel();
		boardPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		boardPanel.setBackground(new Color(46, 139, 87));
		boardPanel.setBounds(0, 0, 656, 656);
		boardPanel.setLayout(new GridLayout(8, 8, 0, 0));
		gameplayPanel.add(boardPanel);
		board = createBoardTable();

		//Creates player info panel
		playerTurnPanel = new JPanel();
		playerTurnPanel.setBackground(new Color(46, 139, 87));
		playerTurnPanel.setPreferredSize(new Dimension(324, 656));
		playerTurnPanel.setLayout(new BorderLayout());
		contentPane.add(playerTurnPanel, BorderLayout.EAST);

		//Creates player turn label
		lblPlayerTurn = new JLabel("Blacks Turn", SwingConstants.CENTER);
		lblPlayerTurn.setFont(new Font("Castellar", Font.PLAIN, 35));
		lblPlayerTurn.setForeground(Color.BLACK);
		playerTurnPanel.add(lblPlayerTurn, BorderLayout.NORTH);

		//Creates scores label
		txtPlayerScores = new JTextArea();
		txtPlayerScores.setFont(new Font("Castellar", Font.PLAIN, 25));
		txtPlayerScores.setLineWrap(false);
		txtPlayerScores.setEditable(false);
		txtPlayerScores.setBackground(new Color(46, 139, 87));
		txtPlayerScores.setForeground(Color.BLACK);
		txtPlayerScores.setText("\nBlack: 2\nWhite: 2");
		txtPlayerScores.setMargin(new Insets(0, 110,0, 0));
		playerTurnPanel.add(txtPlayerScores, BorderLayout.CENTER);

		//Creates player turn label
		lblAdrien = new JLabel("Created by Adrien Baldwin", SwingConstants.CENTER);
		lblAdrien.setFont(new Font("Castellar", Font.PLAIN, 13));
		lblAdrien.setForeground(Color.BLACK);
		playerTurnPanel.add(lblAdrien, BorderLayout.SOUTH);

	}

	/**
	 * Creates 8 by 8 board of Disc objects (buttons), but does not enable them to be clicked.
	 * Discs are set to a neutral, non-accessed color grey.
	 * @return board of Discs
	 */
	public Disc[][] createBoardTable() {
		board = new Disc[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Disc("Grey", i, j);
				board[i][j].setEnabled(false);
				board[i][j].setBackground(new Color(0, 100, 0));
				this.boardPanel.add(board[i][j]);
				board[i][j].setVisible(true);
			}
		}
		gameplayPanel.setLayout(null);
		return board;
	}

	/**
	 * Sets icon image for each disc and scales images from resource folder to be properly fitted
	 * Enables Disc object
	 * @param color color of Disc
	 * @param x x coordinate
	 * @param y y coordinate
	 * @throws IOException if not found
	 */
	public void addDiscToBoard(String color, int x, int y) throws IOException {
		BufferedImage image = ImageIO.read(this.getClass().getResource("resources/" + color + "Disc.png"));
		ImageIcon icon = new ImageIcon(getScaledImage(image, 75, 75));
		this.board[x][y].setDiscColor(color);
		this.board[x][y].setIcon(icon);
		this.board[x][y].setEnabled(true);
		boardPanel.revalidate();
		boardPanel.repaint();
	}

	/**
	 * Scales image from resource folder to fit within Othello Disc size
	 * @param srcImg reads from resource folder
	 * @param w width
	 * @param h height
	 * @return scaled image
	 */
	public Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Closes and disposes GUI
	 */
	public void exit() {
		dispose();
	}
}
