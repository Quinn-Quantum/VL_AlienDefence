package model.persistenceDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Vector;

import model.Attempt;
import model.Level;
import model.persistence.IAttemptPersistance;
import model.User;

public class AttemptDB implements IAttemptPersistance {

	// Attribute
	private AccessDB dbAccess;
	private int playerPosition = -100000;

	// Konstuktor
	public AttemptDB(AccessDB dbAccess) {
		this.dbAccess = dbAccess;
	}

	// Eintrag des Spiels in die Highscore wird vorgenommen - Aufgerufen wird von
	// der Klasse GameController
	public int createHighscoreEntry(int F_user_id, int F_level_id, int shots, int hits, long reaction_time) {
		String sql = "INSERT INTO attempts (F_user_id, F_level_id, shots, hits, reaction_time, date, time)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		int last_inserted_id = 0;
		try (Connection con = DriverManager.getConnection(this.dbAccess.getFullURL(), this.dbAccess.getUser(),
				this.dbAccess.getPassword());
				PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			// Aktuelle Datum und Uhrzeit holen und in Strings speichern
			LocalDate datum = LocalDate.now(); // Erstellt Datum-Objekt heute
			LocalTime time = LocalTime.now();
			String now = time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();

			statement.setInt(1, F_user_id);
			statement.setInt(2, F_level_id);
			statement.setInt(3, shots);
			statement.setInt(4, hits);
			statement.setLong(5, reaction_time);
			statement.setString(6, datum.toString());
			statement.setString(7, now);

			statement.execute();

			ResultSet rs = statement.getGeneratedKeys(); // Letzte eingtragene ID
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return last_inserted_id;
	}

	public Vector<Vector<String>> getAllAttemptsPerLevel(Level level, int game_id) {
		Vector<Vector<String>> vecTests = null;
		String url = dbAccess.getUrl() + dbAccess.getDbName();

		String highscoreFormula = "(hits / targets * 1000)*0.4 + (hits / shots * 1000)*0.2 + (1000 - (reaction_time / sum_duration * 1000))*0.4";

		String query = "SELECT P_attempt_id, targets, shots, hits, reaction_time, first_name, sur_name, date, time, (hits / targets * 1000) AS hitsTargets, (hits / shots * 1000) AS hitsShots, (1000 - (reaction_time / sum_duration * 1000)) AS reactionDuration,"
				+ highscoreFormula + "AS highscoreFormula FROM "
				+ "(SELECT F_level_id, COUNT(P_target_id) AS targets, SUM(duration ) AS sum_duration  FROM `targets` WHERE F_level_id = "
				+ level.getLevel_id() + ") AS count_targets INNER JOIN "
				+ "attempts ON attempts.F_level_id = count_targets.F_level_id INNER JOIN "
				+ "users ON P_user_id = F_user_id ORDER BY highscoreFormula DESC, reactionDuration DESC";

		try {
			Connection con = DriverManager.getConnection(url, dbAccess.getUser(), dbAccess.getPassword());
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(query);
			vecTests = getVecTest(level, game_id, results);

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return vecTests;
	}

	public int getPlayerPosition() {
		return this.playerPosition;
	}

	// 2 Diese Methode wird vom Konstruktor aufgerufen.
	// Daten aus der Datenbank werden in einem zweidimensionalen Vector gespeichert.
	private Vector<Vector<String>> getVecTest(Level level, int gameId, ResultSet results) {

		Vector<Vector<String>> vecTests = new Vector<Vector<String>>();
		try {
			int nummerierung = 0;
			while (results.next()) {

				nummerierung++;

				//Markierung des Spielers für Highscoreansicht
				//TODO Verstoü gegen 3 Schichtenarchitektur beheben
				if (results.getInt("P_attempt_id") == gameId)
					this.playerPosition = nummerierung - 1;

				LocalDate date = results.getDate("date").toLocalDate();
				User user = new User();
				user.setFirst_name(results.getString("first_name"));
				user.setSur_name(results.getString("sur_name"));
				LocalDateTime dateTime = date.atTime(results.getTime("time").toLocalTime());
				Attempt tmp = new Attempt(nummerierung, level, results.getDouble("hitsShots"),
						results.getDouble("hitsTargets"), results.getInt("reactionDuration"),
						results.getInt("highscoreFormula"), user, dateTime);

				Vector<String> v = tmp.getRowVector();
				vecTests.add(v);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return vecTests;
	}

	// lüscht alle Eintrüge
	public void deleteHighscore(int level_id) {

		String url = dbAccess.getFullURL();
		try {

			Connection con = DriverManager.getConnection(url, dbAccess.getUser(), dbAccess.getPassword());

			String query = "DELETE FROM attempts WHERE F_level_id = ?";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, level_id);

			preparedStmt.execute();

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
}