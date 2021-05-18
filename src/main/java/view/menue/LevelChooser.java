package view.menue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.LevelController;
import model.Level;

@SuppressWarnings("serial")
public class LevelChooser extends JPanel {

	private LevelController lvlControl;
	private LeveldesignWindow leveldesignWindow;
	private JTable tblLevels;
	private DefaultTableModel jTableData;

	/**
	 * Create the panel.
	 * 
	 * @param leveldesignWindow
	 */
	public LevelChooser(LevelController lvlControl, LeveldesignWindow leveldesignWindow) {
		this.lvlControl = lvlControl;
		this.leveldesignWindow = leveldesignWindow;

		setLayout(new BorderLayout());

		JPanel pnlButtons = new JPanel();
		add(pnlButtons, BorderLayout.SOUTH);

		JButton btnNewLevel = new JButton("Neues Level");
		btnNewLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewLevel_Clicked();
			}
		});
		pnlButtons.add(btnNewLevel);

		JButton btnUpdateLevel = new JButton("ausgew\u00E4hltes Level bearbeiten");
		btnUpdateLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUpdateLevel_Clicked();
			}
		});
		pnlButtons.add(btnUpdateLevel);

		JButton btnDeleteLevel = new JButton("Level l\u00F6schen");
		btnDeleteLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteLevel_Clicked();
			}
		});
		pnlButtons.add(btnDeleteLevel);

		JButton btnSpielen = new JButton ("Speilen");
		btnSpielen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSpeilen_Clicked();
			}
		});

		JLabel lblLevelauswahl = new JLabel("Levelauswahl");
		lblLevelauswahl.setFont(new Font("Arial", Font.BOLD, 18));
		lblLevelauswahl.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblLevelauswahl, BorderLayout.NORTH);

		JScrollPane spnLevels = new JScrollPane();
		add(spnLevels, BorderLayout.CENTER);

		tblLevels = new JTable();
		tblLevels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spnLevels.setViewportView(tblLevels);

		this.updateTableData();
	}

	private String[][] getLevelsAsTableModel() {
		List<Level> levels = this.lvlControl.readAllLevels();
		String[][] result = new String[levels.size()][];
		int i = 0;
		for (Level l : levels) {
			result[i++] = l.getData();
		}
		return result;
	}

	public void updateTableData() {
		this.jTableData = new DefaultTableModel(this.getLevelsAsTableModel(), Level.getLevelDescriptions());
		this.tblLevels.setModel(jTableData);
	}

	public void btnNewLevel_Clicked() {
		this.leveldesignWindow.startLevelEditor();
	}

	public void btnUpdateLevel_Clicked() {
		int level_id = Integer
				.parseInt((String) this.tblLevels.getModel().getValueAt(this.tblLevels.getSelectedRow(), 0));
		this.leveldesignWindow.startLevelEditor(level_id);
	}

	public void btnDeleteLevel_Clicked() {
		int level_id = Integer
				.parseInt((String) this.tblLevels.getModel().getValueAt(this.tblLevels.getSelectedRow(), 0));
		this.lvlControl.deleteLevel(level_id);
		this.updateTableData();
	}
	public void btnSpeilen_Clicked(){
		//Spielaufruf
	}
}
