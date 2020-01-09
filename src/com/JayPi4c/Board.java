package com.JayPi4c;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Board extends JPanel {

	private static final long serialVersionUID = -1031785993347374710L;

	private Field[][] board;

	private int bombCount = 75;

	private int cols = 20, rows = 20;

	private Minesweeper m;

	private int w, h;

	private static BufferedImage GameOverScreen;
	private static BufferedImage winScreen;

	private boolean initialized = false;

	private static boolean guardSingleField = false;
	private static boolean guardSurroundingFields = true;

	public Board(Minesweeper m, int width, int height) {
		this.m = m;
		this.w = width / cols;
		this.h = height / rows;
		setPreferredSize(new Dimension(width, height));
		board = new Field[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				board[i][j] = new Field(m, i, j, w, h);
			}
		}
	}

	public static void preLoad() {
		try {
			GameOverScreen = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/GameOver.png"));
			winScreen = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/Win.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void restart() {
		board = new Field[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				board[i][j] = new Field(m, i, j, w, h);
			}
		}
		initialized = false;
	}

	void init(int x, int y) {
		ArrayList<Field> pool = new ArrayList<Field>();

		if (guardSurroundingFields) {
			int i_target = -1;
			int j_target = -1;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j].contains(x, y)) {
						i_target = i;
						j_target = j;
						break;
					}
				}
				if (i_target != -1)
					break;
			}
			if (i_target == -1 && j_target == -1)
				return;

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (i == i_target || i == i_target - 1 || i == i_target + 1)
						if (j == j_target || j == j_target - 1 || j == j_target + 1)
							continue;
					pool.add(board[i][j]);
				}
			}
		} else if (guardSingleField) {
			for (Field[] fs : board) {
				for (Field f : fs) {
					if (f.contains(x, y))
						continue;
					pool.add(f);
				}
			}
		} else {
			for (Field[] fs : board) {
				for (Field f : fs) {
					pool.add(f);
				}
			}
		}
		for (int i = 0; i < bombCount; i++) {
			int n = (int) Math.floor(Math.random() * pool.size());
			Field f = pool.remove(n);
			f.setBomb();
		}
		for (Field fs[] : board) {
			for (Field f : fs) {
				f.init(this);
			}
		}
		initialized = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!m.isGameOver()) {
			for (Field[] fs : board) {
				for (Field f : fs) {
					f.show((Graphics2D) g);
				}
			}
			if (hasWon()) {
				g.drawImage(winScreen, 0, 175, null);
			}
		} else {

			gameover((Graphics2D) g);
			g.drawImage(GameOverScreen, 0, 175, null);
		}
	}

	void openField(int x, int y) {
		for (Field fs[] : board) {
			for (Field f : fs) {
				if (f.contains(x, y)) {
					if (!f.isOpend())
						f.open(this);
					else
						f.openAdjecent(this);
					return;
				}
			}
		}
	}

	void changeFlag(int x, int y) {
		for (Field fs[] : board) {
			for (Field f : fs) {
				if (f.contains(x, y))
					f.changeFlag();
			}
		}
	}

	// ----------------------HELPER-------------------------//

	public void changeSize(int w, int h) {
		this.w = w / cols;
		this.h = h / rows;
		setPreferredSize(new Dimension(w, h));
		for (Field fs[] : board) {
			for (Field f : fs) {
				f.changeSize(this.w, this.h);
			}
		}

	}

	public static void setGuardSingleField(boolean flag) {
		guardSingleField = flag;
	}

	public static void setGuardSurroundingFields(boolean flag) {
		guardSurroundingFields = flag;
	}

	public static void setNoGuard() {
		guardSingleField = false;
		guardSurroundingFields = false;
	}

	public Field[][] getBoard() {
		return this.board;
	}

	public int getCols() {
		return this.cols;
	}

	public int getRows() {
		return this.rows;
	}

	public boolean isUninitialized() {
		return !initialized;
	}

	boolean hasWon() {
		int counter = 0;
		for (Field[] fs : board)
			for (Field f : fs)
				if (f.isOpend())
					counter++;
		return counter == (cols * rows - bombCount);
	}

	void gameover(Graphics2D g) {
		for (Field fs[] : board) {
			for (Field f : fs) {
				f.gameover(g);
			}
		}
	}

	/**
	 * deprecated Um zu gewinnen müssen alle Flaggen an der richtigen Stelle
	 * platziert worden sein.
	 **/
	boolean hasWonOld() {
		int counter = 0;
		int flagCounter = 0;
		for (Field[] fs : board) {
			for (Field f : fs)
				if (f.hasFlag()) {
					flagCounter++;
					if (f.isBomb())
						counter++;
				}
		}

		return (counter == bombCount && flagCounter == bombCount);
	}
}
