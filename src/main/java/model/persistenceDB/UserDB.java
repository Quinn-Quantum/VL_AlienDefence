package model.persistenceDB;

import java.sql.*;

import model.Target;
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
	public int createUser(User user, int user_id) {
		String sql = "INSERT INTO targets (P_user_id,first_name,sur_name, birthday, street, hous_number, postal_code, city, login_name, password, salary_expections, marital_status,final_grade); VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		int lastKey = -1;
		try (Connection con = DriverManager.getConnection(this.dbAccess.getFullURL(), this.dbAccess.getUser(),
				this.dbAccess.getPassword());
			 PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, user_id);
			statement.setString(2, user.getFirst_name());
			statement.setString(3, user.getSur_name());
			statement.setDate(4,Date.valueOf (user.getBirthday()));
			statement.setString(5, user.getStreet());
			statement.setString(6, user.getHouse_number());
			statement.setString(7, user.getPostal_code());
			statement.setString(8, user.getCity());
			statement.setString(9,user.getLoginname());
			statement.setString(10,user.getPassword());
			statement.setInt(11, user.getSalary_expectations());
			statement.setString(12, user.getMarital_status());
			statement.setDouble(13,user.getFinal_grade());
			statement.execute();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				lastKey = generatedKeys.getInt(1);
			}
			generatedKeys.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lastKey;
	}
	public void updateUser(User user) {
		String sql = "UPDATE user SET first_name = ?,sur_name = ?, birthday = ?, street = ?, hous_number = ?, postal_code = ?, city = ?, login_name = ?, password = ?, salary_expections = ?, marital_status = ?,final_grade = ? WHERE P_user_id = ?;";
		try (Connection con = DriverManager.getConnection(this.dbAccess.getFullURL(), this.dbAccess.getUser(),
				this.dbAccess.getPassword()); PreparedStatement statement = con.prepareStatement(sql)) {

			statement.setString(1, user.getFirst_name());
			statement.setString(2, user.getSur_name());
			statement.setDate(3,Date.valueOf (user.getBirthday()));
			statement.setString(4, user.getStreet());
			statement.setString(5, user.getHouse_number());
			statement.setString(6, user.getPostal_code());
			statement.setString(7, user.getCity());
			statement.setString(8,user.getLoginname());
			statement.setString(9,user.getPassword());
			statement.setInt(10, user.getSalary_expectations());
			statement.setString(11, user.getMarital_status());
			statement.setDouble(12,user.getFinal_grade());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteUser(int user_id) {
		String sql = "DELETE FROM user WHERE P_user_id = " + user_id + ";";

		try (Connection con = DriverManager.getConnection(this.dbAccess.getFullURL(), this.dbAccess.getUser(),
				this.dbAccess.getPassword()); Statement statement = con.createStatement()) {
			statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
