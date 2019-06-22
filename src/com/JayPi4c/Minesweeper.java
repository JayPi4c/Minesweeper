package com.JayPi4c;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Minesweeper extends JFrame implements MouseListener {

	private static final long serialVersionUID = 2236137673888511655L;

	private Board b;
	private boolean gameover = false;
	private boolean gameWon = false;

	public Minesweeper() {
		super("Minesweeper");

		Field.preLoad();
		Board.preLoad();

		JMenuBar menuBar = new JMenuBar();
		JMenuItem settings = new JMenuItem("settings");
		JMenu test = new JMenu("Test");
		test.add(settings);
		menuBar.add(test);
		this.setJMenuBar(menuBar);
		b = new Board(this, 20, 20);
		b.init();
		this.add(b);
		this.addMouseListener(this);
		this.setFocusable(true);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	public boolean isGameOver() {
		return this.gameover;

	}

	public void setGameOver() {
		this.gameover = true;
	}

	@Override
	public void paintComponents(Graphics g) {
		b.paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameover && !gameWon) {
			Insets insets = this.getInsets();
			if (e.getButton() == MouseEvent.BUTTON1) {
				b.openField(e.getX() - insets.left, e.getY() - insets.top - 20);
			} else
				b.changeFlag(e.getX() - insets.left, e.getY() - insets.top - 20);
			if (b.hasWon())
				gameWon = true;
		} else {
			b.restart();
			b.init();
			gameover = false;
			gameWon = false;
		}
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
