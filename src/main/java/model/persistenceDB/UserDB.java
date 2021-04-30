package model.persistenceDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import model.persistence.IUserPersistance;
import model.persistenceDB.AccessDB;

/**
 * databaseconnection for userobjects, Story usermanagement
 * @author Clara Zufall
 * TODO finish this class
 */
public class UserDB implements IUserPersistance{

	private AccessDB dbAccess;

	public UserDB(AccessDB dbAccess) {
		this.dbAccess = dbAccess;
	}

	/**
	 * read userdata by unique username
	 * 
	 * @param username
	 * @return userobject, null if user didn't exists
	 */
	public User readUser(String username) {
		String sql = "SELECT * FROM users WHERE login_name = ? ;";
		User user = null;
		try (Connection con = DriverManager.getConnection(this.dbAccess.getFullURL(), this.dbAccess.getUser(),
				this.dbAccess.getPassword()); PreparedStatement statement = con.prepareStatement(sql)) {

			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				user = new User(rs.getInt("P_user_id"), rs.getString("first_name"), rs.getString("sur_name"),
						rs.getDate("birthday").toLocalDate(), rs.getString("street"), rs.getString("house_number"),
						rs.getString("postal_code"), rs.getString("city"), rs.getString("login_name"),
						rs.getString("password"), rs.getInt("salary_expectations"), rs.getString("marital_status"),
						rs.getBigDecimal("final_grade").doubleValue());
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
	}

}
