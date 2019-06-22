package com.JayPi4c;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Field {

	private static BufferedImage one;
	private static BufferedImage two;
	private static BufferedImage three;
	private static BufferedImage four;
	private static BufferedImage five;
	private static BufferedImage six;
	private static BufferedImage seven;
	private static BufferedImage eight;
	private static BufferedImage empty;
	private static BufferedImage flagPic;
	private static BufferedImage emptyOpen;
	private static BufferedImage flagError;

	private static BufferedImage bombPic;

	private boolean open;
	private boolean bomb;
	private boolean flag;
	private int bombsAdjecent;
	private int i, j;

	private int width, height;

	Minesweeper m;

	public Field(Minesweeper m, int i, int j, int w, int h) {
		this.m = m;
		this.i = i;
		this.j = j;
		this.width = w;
		this.height = h;
		this.open = false;
		this.flag = false;
	}

	public static void preLoad() {
		try {
			one = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/one.png"));
			two = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/two.png"));
			three = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/three.png"));
			four = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/four.png"));
			five = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/five.png"));
			six = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/six.png"));
			seven = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/seven.png"));
			eight = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/eight.png"));
			empty = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/empty.png"));
			flagPic = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/flag.png"));
			emptyOpen = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/emptyOpen.png"));
			flagError = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/flagError.png"));
			bombPic = ImageIO.read(ClassLoader.getSystemResource("com/JayPi4c/resource/bomb.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init(Board board) {
		if (!bomb) {
			int counter = 0;

			// links
			if (i - 1 >= 0) {
				if (j - 1 >= 0)
					counter += board.getBoard()[i - 1][j - 1].isBomb() ? 1 : 0;
				counter += board.getBoard()[i - 1][j].isBomb() ? 1 : 0;
				if (j + 1 < board.getCols())
					counter += board.getBoard()[i - 1][j + 1].isBomb() ? 1 : 0;
			}
			// mitte
			if (j - 1 >= 0)
				counter += board.getBoard()[i][j - 1].isBomb() ? 1 : 0;
			if (j + 1 < board.getCols())
				counter += board.getBoard()[i][j + 1].isBomb() ? 1 : 0;

			// rechts
			if (i + 1 < board.getRows()) {
				if (j - 1 >= 0)
					counter += board.getBoard()[i + 1][j - 1].isBomb() ? 1 : 0;
				counter += board.getBoard()[i + 1][j].isBomb() ? 1 : 0;
				if (j + 1 < board.getCols())
					counter += board.getBoard()[i + 1][j + 1].isBomb() ? 1 : 0;
			}

			bombsAdjecent = counter;

		}
	}

	public void show(Graphics2D g2d) {
		int w = i * width, h = j * height;

		if (open) {
			if (this.bomb) {
				g2d.drawImage(bombPic, w, h, null);
			} else {
				switch (bombsAdjecent) {
				case 0:
					g2d.drawImage(emptyOpen, w, h, null);
					break;
				case 1:
					g2d.drawImage(one, w, h, null);
					break;
				case 2:
					g2d.drawImage(two, w, h, null);
					break;
				case 3:
					g2d.drawImage(three, w, h, null);
					break;
				case 4:
					g2d.drawImage(four, w, h, null);
					break;
				case 5:
					g2d.drawImage(five, w, h, null);
					break;
				case 6:
					g2d.drawImage(six, w, h, null);
					break;
				case 7:
					g2d.drawImage(seven, w, h, null);
					break;
				case 8:
					g2d.drawImage(eight, w, h, null);
					break;
				}
			}
		} else if (flag) {
			g2d.drawImage(flagPic, w, h, null);
		} else {
			g2d.drawImage(empty, w, h, null);
		}
	}

	void gameover(Graphics2D g) {
		int w = i * width, h = j * height;
		if (bomb) {
			if (flag)
				g.drawImage(flagPic, w, h, null);
			else
				g.drawImage(bombPic, w, h, null);
		} else if (flag) {
			g.drawImage(flagError, w, h, null);
		} else {
			switch (bombsAdjecent) {
			case 0:
				g.drawImage(emptyOpen, w, h, null);
				break;
			case 1:
				g.drawImage(one, w, h, null);
				break;
			case 2:
				g.drawImage(two, w, h, null);
				break;
			case 3:
				g.drawImage(three, w, h, null);
				break;
			case 4:
				g.drawImage(four, w, h, null);
				break;
			case 5:
				g.drawImage(five, w, h, null);
				break;
			case 6:
				g.drawImage(six, w, h, null);
				break;
			case 7:
				g.drawImage(seven, w, h, null);
				break;
			case 8:
				g.drawImage(eight, w, h, null);
				break;
			}
		}
	}

	void open(Board b) {
		if (!open && !flag) {
			open = true;
			if (bomb) {
				m.setGameOver();
			}
			if (bombsAdjecent == 0) {
				// floodfill time!!
				// links
				if (i - 1 >= 0) {
					if (j - 1 >= 0)
						b.getBoard()[i - 1][j - 1].open(b);
					b.getBoard()[i - 1][j].open(b);
					if (j + 1 < b.getRows())
						b.getBoard()[i - 1][j + 1].open(b);
				}
				// mitte
				if (j - 1 >= 0)
					b.getBoard()[i][j - 1].open(b);
				if (j + 1 < b.getRows())
					b.getBoard()[i][j + 1].open(b);

				// rechts
				if (i + 1 < b.getCols()) {
					if (j - 1 >= 0)
						b.getBoard()[i + 1][j - 1].open(b);
					b.getBoard()[i + 1][j].open(b);
					if (j + 1 < b.getRows())
						b.getBoard()[i + 1][j + 1].open(b);
				}
			}
		}
	}

	void openAdjecent(Board b) {
		if (flagsAdjescent(b) == bombsAdjecent) {
			// links
			if (i - 1 >= 0) {
				if (j - 1 >= 0)
					b.getBoard()[i - 1][j - 1].open(b);
				b.getBoard()[i - 1][j].open(b);
				if (j + 1 < b.getCols())
					b.getBoard()[i - 1][j + 1].open(b);
			}
			// mitte
			if (j - 1 >= 0)
				b.getBoard()[i][j - 1].open(b);
			if (j + 1 < b.getCols())
				b.getBoard()[i][j + 1].open(b);

			// rechts
			if (i + 1 < b.getCols()) {
				if (j - 1 >= 0)
					b.getBoard()[i + 1][j - 1].open(b);
				b.getBoard()[i + 1][j].open(b);
				if (j + 1 < b.getRows())
					b.getBoard()[i + 1][j + 1].open(b);
			}
		}
	}

	int flagsAdjescent(Board b) {
		int counter = 0;

		// links
		if (i - 1 >= 0) {
			if (j - 1 >= 0)
				counter += b.getBoard()[i - 1][j - 1].hasFlag() ? 1 : 0;
			counter += b.getBoard()[i - 1][j].hasFlag() ? 1 : 0;
			if (j + 1 < b.getCols())
				counter += b.getBoard()[i - 1][j + 1].hasFlag() ? 1 : 0;
		}
		// mitte
		if (j - 1 >= 0)
			counter += b.getBoard()[i][j - 1].hasFlag() ? 1 : 0;
		if (j + 1 < b.getCols())
			counter += b.getBoard()[i][j + 1].hasFlag() ? 1 : 0;

		// rechts
		if (i + 1 < b.getRows()) {
			if (j - 1 >= 0)
				counter += b.getBoard()[i + 1][j - 1].hasFlag() ? 1 : 0;
			counter += b.getBoard()[i + 1][j].hasFlag() ? 1 : 0;
			if (j + 1 < b.getCols())
				counter += b.getBoard()[i + 1][j + 1].hasFlag() ? 1 : 0;
		}
		return counter;
	}

	// --------------------HELPER-------------------//

	boolean contains(int x, int y) {
		return (x > i * width && x < i * width + width && y > j * height && y < j * height + height);
	}

	boolean isOpend() {
		return open;
	}

	void setBomb() {
		this.bomb = true;
	}

	boolean isBomb() {
		return this.bomb;
	}

	boolean hasFlag() {
		return flag;
	}

	void changeFlag() {
		flag = !flag;
	}
}
