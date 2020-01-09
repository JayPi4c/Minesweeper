package com.JayPi4c;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * Diese Klasse hat die Aufgabe, das Laden der Texturen in einem Ladebalken zu
 * visualisieren. Auf moderen Rechnern ist diese Klasse leider nicht bemerkbar,
 * da das Laden der Texturen zu schnell geht (ca. 0.4s) Nichtsdestotrotz kann
 * diese Klasse als Template für andere Programme dienen, bei denen noch mehr
 * Texturen geladen werden müssen.
 * 
 * @author jaypi4c
 *
 */
public class Loader extends JFrame {

	private static final long serialVersionUID = -5691470377198557988L;

	private JProgressBar progressBar;

	public Loader() {
		super("Minesweeper");
		progressBar = new JProgressBar(0, 2);
		progressBar.setValue(0);
		add(progressBar);
		setUndecorated(true);

		pack();
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	/**
	 * Durch diese Funltion werden die Texturen in die statischen Variablen der
	 * jeweiligen Klassen geladen.
	 */
	public void load() {
		Board.preLoad();
		progressBar.setValue(1);
		Field.preLoad();
		progressBar.setValue(2);
		dispose();
	}

}
