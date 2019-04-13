package org.sda.manager.authentication;

import org.sda.manager.authentication.hash.HashFunction;
import org.sda.manager.authentication.model.User;
import org.sda.manager.exceptions.UserNotFoundException;

public interface AuthService {

  boolean isAuthenticated(User user, HashFunction hashFunction);
}
