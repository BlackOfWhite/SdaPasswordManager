package org.sda.manager.database.services;

import java.util.List;
import org.sda.manager.authentication.model.User;
import org.sda.manager.database.tables.UserTableRow;
import org.sda.manager.exceptions.DatabaseIntegrityException;
import org.sda.manager.exceptions.UserNotFoundException;

public interface UsersService {

  List<UserTableRow> findAllUsers();

  UserTableRow findUserByName(String userName) throws UserNotFoundException;

  String findUserHashByName(String userName) throws UserNotFoundException;

  boolean registerNewUser(User user) throws DatabaseIntegrityException;

  boolean removeUser(User user) throws UserNotFoundException;
}
