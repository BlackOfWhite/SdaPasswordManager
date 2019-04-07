package org.sda.manager.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.sda.manager.database.tables.UserTable;

public final class DatabaseManager {

  private static final Logger logger = Logger.getLogger(DatabaseManager.class);
  private static final String USERS_CSV_FILE_PATH = "/users.csv";
  //  private static final String CREDENTIALS_STORAGE_CSV_FILE_PATH = "/credentials.csv";
  private static final DatabaseManager INSTANCE = new DatabaseManager();

  private DatabaseManager() {

  }

  public static DatabaseManager getInstance() {
    return INSTANCE;
  }

  List<UserTable> getAllUsers() {
    List<UserTable> userDataList = new ArrayList<>();
    try (
        InputStream in = getClass().getResourceAsStream(USERS_CSV_FILE_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        CSVReader csvReader = new CSVReaderBuilder(reader)
            .withSkipLines(1).build()
    ) {
      String[] nextRecord = csvReader.readNext();
      while (nextRecord != null) {
        String name = nextRecord[0];
        String email = nextRecord[1];
        String country = nextRecord[2];
        String hash = nextRecord[3];
        userDataList.add(new UserTable(name, email, country, hash));
        nextRecord = csvReader.readNext();
      }
    } catch (NoSuchFileException e) {
      logger.warn("File: " + USERS_CSV_FILE_PATH + " was not found.", e);
    } catch (IOException e) {
      logger.warn("Unexpected exception while reading file: "
          + USERS_CSV_FILE_PATH, e);
    }
    return userDataList;
  }
//
//  UserTable getUser(String name) throws UserNotFoundException {
//    return null;
//  }
//
//  void registerUser(User newUser) {
//
//  }
}
