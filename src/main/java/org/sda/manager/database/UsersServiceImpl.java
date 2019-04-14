package org.sda.manager.database;

import java.util.List;
import java.util.Optional;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.services.UsersService;
import org.sda.manager.database.tables.PasswordTableRow;
import org.sda.manager.database.tables.UserTableRow;
import org.sda.manager.exceptions.DatabaseIntegrityException;
import org.sda.manager.exceptions.NoPasswordsFoundException;
import org.sda.manager.exceptions.PasswordNotFoundException;
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
        throw new DatabaseIntegrityException("Username already exists!");
      }
      if (row.getEmail().equals(user.getEmail())) {
        throw new DatabaseIntegrityException("Email already exists!");
      }
    }
    UserTableRow userTableRow = new UserTableRow(user);
    userTableRow.setId(getNewValidUserId());
    return DatabaseManager.getInstance().createUser(userTableRow);
  }

  @Override
  public boolean editUser(final User user) throws UserNotFoundException {
    String userId = findUserByName(user.getName()).getId();
    UserTableRow newRow = new UserTableRow(user);
    newRow.setId(userId);
    removeUser(user);
    DatabaseManager.getInstance().createUser(newRow);
    return true;
  }

  @Override
  public boolean removeUser(User user) throws UserNotFoundException {
    UserTableRow row = new UserTableRow(user);
    findUserByName(row.getUserName());
    return DatabaseManager.getInstance().deleteUser(row);
  }

  @Override
  public List<PasswordTableRow> findAllUserPasswords(User user) throws UserNotFoundException, NoPasswordsFoundException {
    // Check if user exists, if doesn't throw exception
    UserTableRow userTableRow = findUserByName(user.getName());
    return DatabaseManager.getInstance().getAllPasswordsForUser(userTableRow.getId());
  }

  @Override
  public PasswordTableRow findUserPasswordById(User user, int passwordId)
      throws PasswordNotFoundException, NoPasswordsFoundException, UserNotFoundException {
    List<PasswordTableRow> passwords = findAllUserPasswords(user);
    for (PasswordTableRow row : passwords) {
      if (row.getId().equals(String.valueOf(passwordId))) {
        return row;
      }
    }
    throw new PasswordNotFoundException();
  }

  private String getNewValidUserId() {
    Optional<Integer> max = findAllUsers().stream()
        .map(userTableRow -> userTableRow.getId())
        .map(s -> Integer.parseInt(s))
        .max(Integer::compareTo);
    if (max.isPresent()) {
      return String.valueOf(max.get() + 1);
    } else {
      return "1";
    }
  }
}
