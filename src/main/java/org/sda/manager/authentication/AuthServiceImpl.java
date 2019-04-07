package org.sda.manager.authentication;

import org.sda.manager.authentication.hash.HashFunction;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.UsersServiceImpl;
import org.sda.manager.database.tables.UserTable;

public class AuthServiceImpl implements AuthService {

//  private UsersServiceImpl userDAO = new UsersServiceImpl();

  @Override
  public boolean isAuthenticated(final User user, HashFunction hashFunction) {
    String userHashedPassword = hashFunction.hash(user.getPassword());
    System.out.println("userHashedPassword = " + userHashedPassword);
//    UserTable userTable = userDAO.findUserByName(user.getName());
    return userHashedPassword.equals("32rfsdf");
  }
}
