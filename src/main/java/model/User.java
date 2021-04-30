package model;

import java.time.LocalDate;

public class User {

	private int p_user_id;
	private String first_name;
	private String sur_name;
	private LocalDate birthday;
	private String street;
	private String house_number;
	private String postal_code;
	private String city;
	private String loginname;
	private String password;
	private int salary_expectations;
	private String marital_status;
	private double final_grade;

	public User(int p_user_id, String first_name, String sur_name, LocalDate birthday, String street,
			String house_number, String postal_code, String city, String loginname, String password,
			int salary_expectations, String marital_status, double final_grade) {
		super();
		this.p_user_id = p_user_id;
		this.first_name = first_name;
		this.sur_name = sur_name;
		this.birthday = birthday;
		this.street = street;
		this.house_number = house_number;
		this.postal_code = postal_code;
		this.city = city;
		this.loginname = loginname;
		this.password = password;
		this.salary_expectations = salary_expectations;
		this.marital_status = marital_status;
		this.final_grade = final_grade;
	}

	public User(int p_user_id, String login, String password) {
		super();
		this.p_user_id = p_user_id;
		this.loginname = login;
		this.password = password;
	}

	public User() {
	}

	public int getP_user_id() {
		return p_user_id;
	}

	public void setP_user_id(int p_user_id) {
		this.p_user_id = p_user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSur_name() {
		return sur_name;
	}

	public void setSur_name(String sur_name) {
		this.sur_name = sur_name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse_number() {
		return house_number;
	}

	public void setHouse_number(String house_number) {
		this.house_number = house_number;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getFinal_grade() {
		return final_grade;
	}

	public void setFinal_grade(double final_grade) {
		this.final_grade = final_grade;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}

	public int getSalary_expectations() {
		return salary_expectations;
	}

	public void setSalary_expectations(int salary_expectations) {
		this.salary_expectations = salary_expectations;
	}
}