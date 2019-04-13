package org.sda.manager.database;

import java.util.List;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.services.UsersService;
import org.sda.manager.database.tables.UserTableRow;
import org.sda.manager.exceptions.DatabaseIntegrityException;
import org.sda.manager.exceptions.UserNotFoundException;

public class UsersServiceImpl implements UsersService {

  @Override
  public List<UserTableRow> findAllUsers() {
    return DatabaseManager.getInstance().getAllUsers();
  }

  @Override
  public UserTableRow findUserByName(String userName) throws UserNotFoundException {
    for (UserTableRow userTableRow : findAllUsers()) {
      if (userTableRow.getUserName().equals(userName)) {
        return userTableRow;
      }
    }
    throw new UserNotFoundException();
  }

  @Override
  public String findUserHashByName(String userName) throws UserNotFoundException {
    return findUserByName(userName).getHash();
  }

  @Override
  public boolean registerNewUser(User user) throws DatabaseIntegrityException {
    List<UserTableRow> rows = findAllUsers();
    for (UserTableRow row : rows) {
      if (row.getUserName().equals(user.getName())) {
        throw new DatabaseIntegrityException();
      }
      if (row.getEmail().equals(user.getEmail())) {
        throw new DatabaseIntegrityException();
      }
    }
    return DatabaseManager.getInstance().createUser(new UserTableRow(user));
  }

  @Override
  public boolean removeUser(User user) throws UserNotFoundException {
    UserTableRow row = new UserTableRow(user);
    findUserByName(row.getUserName());
    return DatabaseManager.getInstance().deleteUser(row);
  }

  public void editUser(User user) {

  }


}
