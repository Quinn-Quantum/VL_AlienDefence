package model.persistence;

import model.User;

public interface IUserPersistance {

	User readUser(String username);

}