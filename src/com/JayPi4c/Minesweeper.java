package com.JayPi4c;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Minesweeper extends JFrame implements MouseListener {

	private static final long serialVersionUID = 2236137673888511655L;

	private Board b = null;
	private boolean gameover = false;
	private boolean gameWon = false;

	private int width = 500;
	private int height = 500;

	public Minesweeper() {
		super("Minesweeper");
		JMenuBar menuBar = new JMenuBar();
		JMenuItem settings = new JMenuItem("settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Adding Actions to Settingsmenu
				System.out.println("Settingsmenu was pressed");
			}
		});
		JMenu test = new JMenu("Test");
		test.add(settings);
		menuBar.add(test);
		this.setJMenuBar(menuBar);
		b = new Board(this, width, height);
		this.add(b);
		this.addMouseListener(this);
		this.setFocusable(true);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Insets insets = getInsets();
				int w = e.getComponent().getWidth() - (insets.left + insets.right);
				int h = e.getComponent().getHeight() - (insets.bottom + insets.top + 20);
				b.changeSize(w, h);
				repaint();
			}
		});

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
		if (b.isUninitialized()) {
			Insets insets = this.getInsets();
			b.init(e.getX() - insets.left, e.getY() - insets.top - 20);

		}
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
