package org.sda.manager.database;

import java.util.List;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.services.UsersService;
import org.sda.manager.database.tables.UserTable;
import org.sda.manager.exceptions.UserNotFoundException;

public class UsersServiceImpl implements UsersService {

  @Override
  public List<UserTable> findAllUsers() {
    return DatabaseManager.getInstance().getAllUsers();
  }

  @Override
  public UserTable findUserByName(String userName) throws UserNotFoundException {
//    return DatabaseManager.getInstance().getUser(userName);
    return null;
  }

  public String findUserHashByName(String userName) {
//    DatabaseManager.getInstance().getAllUsers();
    return "";
  }

//  public void registerNewUser(User user) {
//    DatabaseManager.getInstance().registerUser(user);
//  }

  public void removeUser(User user) {

  }

  public void editUser(User user) {

  }


}
