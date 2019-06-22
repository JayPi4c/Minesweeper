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

	public Board(Minesweeper m, int width, int height) {
		this.m = m;
		this.w = width;
		this.h = height;
		setPreferredSize(new Dimension(cols * width, rows * height));
		board = new Field[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				board[i][j] = new Field(m, i, j, width, height);
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
	}

	void init() {
		ArrayList<Field> pool = new ArrayList<Field>();
		for (Field fs[] : board) {
			for (Field f : fs) {
				pool.add(f);
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
	public Field[][] getBoard() {
		return this.board;
	}

	public int getCols() {
		return this.cols;
	}

	public int getRows() {
		return this.rows;
	}

	boolean hasWon() {
		int counter = 0;
		for (Field[] fs : board)
			for (Field f : fs)
				if (f.isOpend())
					counter++;

		return counter == (h * w - bombCount);
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
