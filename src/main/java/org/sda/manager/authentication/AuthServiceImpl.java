package org.sda.manager.authentication;

import org.apache.log4j.Logger;
import org.sda.manager.authentication.hash.HashFunction;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.UsersServiceImpl;
import org.sda.manager.database.tables.UserTableRow;
import org.sda.manager.exceptions.UserNotFoundException;

public class AuthServiceImpl implements AuthService {

  private static final Logger logger = Logger.getLogger(AuthServiceImpl.class);
  private UsersServiceImpl userDAO = new UsersServiceImpl();

  @Override
  public boolean isAuthenticated(final User user, HashFunction hashFunction) {
    String userHashedPassword = hashFunction.hash(user.getPassword());
    logger.debug("userHashedPassword = " + userHashedPassword);
    UserTableRow userTableRow;
    try {
      userTableRow = userDAO.findUserByName(user.getName());
    } catch (UserNotFoundException e) {
      logger.warn("Unable to authenticate - user '" + user.getName() + "' was not found.");
      return false;
    }
    return userHashedPassword.equalsIgnoreCase(userTableRow.getHash());
  }
}
