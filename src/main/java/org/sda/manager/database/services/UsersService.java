package org.sda.manager.database.services;

import java.util.List;
import org.sda.manager.database.tables.UserTable;
import org.sda.manager.exceptions.UserNotFoundException;

public interface UsersService {

  List<UserTable> findAllUsers();

  UserTable findUserByName(String userName) throws UserNotFoundException;

}
