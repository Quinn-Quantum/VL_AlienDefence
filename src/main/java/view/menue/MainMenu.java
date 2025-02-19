package view.menue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.AlienDefenceController;
import controller.GameController;
import model.Level;
import model.User;
import view.game.GameGUI;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private AlienDefenceController alienDefenceController;
	private JTextField loginTextField;
	private JPasswordField passwordTextField;
	private int selectedLevel = 0;

	private String[] getLevelNames(List<Level> arrLevel) {
		String[] arrLevelNames = new String[arrLevel.size()];

		for (int i = 0; i < arrLevel.size(); i++) {
			arrLevelNames[i] = arrLevel.get(i).getName(); // Array aus Arraylist erstellt
		}
		
		return arrLevelNames;
	}
	
	// Konstruktor
	public MainMenu(AlienDefenceController alienDefenceController) {
		
		this.alienDefenceController = alienDefenceController;		

		// Frame Formatierungen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBounds(100, 1, width, height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel contentPane = new JPanel();
		//Dimension screen1 = Toolkit.getDefaultToolkit().getScreenSize();
		//contentPane.setSize(screen1);
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout()); // GridBagLayout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// JLable mit üBerschrift
		JLabel lblheadline = new JLabel("  ALIEN DEFENCE");
		lblheadline.setForeground(new Color(124, 252, 0));
		lblheadline.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(lblheadline, c);

		// JPanel mit Logo
		JPanel p = new JPanel() {
			private static final long serialVersionUID = 1L;

			private ImageIcon imageIcon = new ImageIcon("./pictures/logo.png");
			private Image image = imageIcon.getImage();

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(image, 5, 8, 145, 145, this);
				}
			}
		};
		c.ipady = 150; // make this component tall
		c.ipadx = 120;
		c.weightx = 0.0;
		c.gridwidth = 15;
		c.gridx = 0;
		c.gridy = 1;
		contentPane.add(p, c);

		// Text Login
		JLabel loginText = new JLabel("Login: "); // Einfacher Text
		loginText.setForeground(Color.orange);
		c.ipady = 0;
		c.ipadx = 0;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(loginText, c);

		// Textfeld
		loginTextField = new JTextField(15);
		c.gridy = 3;
		contentPane.add(loginTextField, c);

		// Text Passwort
		JLabel passwordText = new JLabel("Passwort: "); // Einfacher Text
		passwordText.setForeground(Color.orange);
		c.gridy = 4;
		contentPane.add(passwordText, c);

		// Textfeld
		passwordTextField = new JPasswordField(15);
		c.gridy = 5;
		contentPane.add(passwordTextField, c);

		// Text Level
		//JLabel levelText = new JLabel("Level: "); // Einfacher Text
		//levelText.setForeground(Color.orange);
		//c.gridy = 6;
		//contentPane.add(levelText, c);

		// Levelliste für die ComboBox abrufen
		List<Level> arrLevel = this.alienDefenceController.getLevelController().readAllLevels();
		String[] arrLevelNames = getLevelNames(arrLevel);
		
		// Level Auswahlbox - ActionListener
		//JComboBox<String> combo = new JComboBox<String>(arrLevelNames);
		//c.gridy = 7;
		//contentPane.add(combo, c);
		//ActionListener actLisCombo = new ActionListener() {
		//	@Override
		//	public void actionPerformed(ActionEvent e) {
		//		selectedLevel = combo.getSelectedIndex();
		//	}
		//};
		//combo.addActionListener(actLisCombo); // Listener

		// Button Spielen - ActionListener
		JButton btnNewButton = new JButton("Spielen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// User aus Datenbank holen
				User user = alienDefenceController.getAlienDefenceModel().getUserPersistance().readUser(loginTextField.getText());

				// Spielstarten, wenn Nutzer existiert und Passwort übereinstimmt
				if (user != null && user.getPassword().equals(new String(passwordTextField.getPassword()))) {

					Thread t = new Thread("GameThread") {
						@Override
						public void run() {

							GameController gameController = alienDefenceController.startGame(arrLevel.get(selectedLevel), user);
							new GameGUI(gameController).start();
						}
					};
					t.start();
				} else {
					// Fehlermeldung - Zugangsdaten fehlerhaft
					JOptionPane.showMessageDialog(null, "Zugangsdaten nicht korrekt", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		c.gridy = 8;
		contentPane.add(btnNewButton, c);

		// Button Testen - ActionListener
		JButton btnTestButton = new JButton("Testen");
		btnTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Erstellt Modell von aktuellen Nutzer
				User user = new User(1, "test", "pass");

				Thread t = new Thread("GameThread") {

					@Override
					public void run() {

						List<Level> arrLevel = alienDefenceController.getLevelController().readAllLevels();
						//Aufgabe 1
						new LeveldesignWindow(alienDefenceController.getLevelController(), alienDefenceController.getTargetController(), alienDefenceController ,user, "Testen");

						//GameController gameController = alienDefenceController.startGame(arrLevel.get(selectedLevel), user);
						//new GameGUI(gameController).start();
					}
				};
				t.start();
			}
		});
		c.gridy = 9;
		contentPane.add(btnTestButton, c);

		// Button Highscore
		JButton btnNewButton_2 = new JButton("Highscore");

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Highscore(alienDefenceController.getAttemptController(), arrLevel.get(1));
			}
		});
		// selectedLevel

		c.gridy = 10;
		contentPane.add(btnNewButton_2, c);

		// Button Leveleditor
		JButton btnLeveleditor = new JButton("Leveleditor");
		btnLeveleditor.setBackground(Color.ORANGE);
		btnLeveleditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LeveldesignWindow(alienDefenceController.getLevelController(), alienDefenceController.getTargetController(), alienDefenceController,null, "Leveleditor");

			}
		});
		c.gridy = 11;
		contentPane.add(btnLeveleditor, c);

		// Button Beenden
		JButton btnNewButton_3 = new JButton("Beenden");
		btnNewButton_3.setBackground(Color.GRAY);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		c.gridy = 12;
		c.anchor = GridBagConstraints.PAGE_END;
		contentPane.add(btnNewButton_3, c);
		this.pack();
	}

}