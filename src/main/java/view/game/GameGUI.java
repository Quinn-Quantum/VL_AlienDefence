package view.game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.GameController;

/**
 * GUI der Klasse _______
 * 
 * @author Tenbusch
 *
 */
public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private GameJPanel spielfeld;
	private GameController gc;
	public final long STARTTIME;
	private final int WIDTH = 1280;
	private final int HEIGHT = 960;

	// Konstruktor
	public GameGUI(GameController gc) {
		super("Spiel v0.0 - FPS: ");

		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("pictures/crosshair.png").getImage(),
				new Point(10, 10), "Cursor"));

		// Fenstergestaltung
		setSize(WIDTH, HEIGHT);
		setLocation(10, 10);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Controller registrieren
		this.gc = gc;

		setBackground(new Color(200, 200, 200));

		gc.setHasWon(false);

		spielfeld = new GameJPanel(gc);
		getContentPane().add(spielfeld);
		setVisible(true);

		// Startzeit setzen
		STARTTIME = System.currentTimeMillis();
	}

	public void start() {

		long lastStep = System.currentTimeMillis() - 1;

		// Spielfeld vorbereiten
		gc.startLevel();

		while (true) {

			// FPS berechnen
			long delta = System.currentTimeMillis() - lastStep;
			lastStep = System.currentTimeMillis();
			setTitle("Spiel v0.0 - FPS: " + (1000 / delta));

			// Spiellogik
			this.gc.doLogik(delta);

			repaint();

			try {
				Thread.sleep(1);
			} catch (Exception e) {

			}

			if (gc.isHasWon() || gc.timeleft() <= -1) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}
				dispose();
				break;
			}

		}

	}

}
