package org.sda.manager.exceptions;

import org.sda.manager.database.DatabaseManager;

public class DatabaseIntegrityException extends Exception {

  public DatabaseIntegrityException() {
    super();
    System.out.println("User already exists.");
  }

  public DatabaseIntegrityException(String message) {
    super(message);
    System.out.println(message);
  }
}
