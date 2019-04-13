package org.sda.manager.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.sda.manager.database.tables.UserTableRow;

public final class DatabaseManager {

  private static final Logger logger = Logger.getLogger(DatabaseManager.class);
  private static final String USERS_CSV_FILE_PATH = "/users.csv";
  private static final Path WRITE_USERS_CSV_FILE_PATH = Paths.get(
      "C:\\Users\\pniewinski\\IdeaProjects\\"
          + "SdaPasswordManager\\src\\main\\resources\\users.csv");
  //  private static final String CREDENTIALS_STORAGE_CSV_FILE_PATH = "/credentials.csv";
  private static final DatabaseManager INSTANCE = new DatabaseManager();
  private static final String[] HEADERS =
      new String[]{"name", "email", "country", "hash"};

  private DatabaseManager() {

  }

  public static DatabaseManager getInstance() {
    return INSTANCE;
  }

  List<UserTableRow> getAllUsersWithIterator() {
    List<UserTableRow> userDataList = new ArrayList<>();
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
        userDataList.add(new UserTableRow(name, email, country, hash));
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

  List<UserTableRow> getAllUsers() {
    List<UserTableRow> userDataList = new ArrayList<>();
    try (
        BufferedReader reader = Files.newBufferedReader(WRITE_USERS_CSV_FILE_PATH);
        CSVReader csvReader = new CSVReaderBuilder(reader)
            .withSkipLines(1).build()
    ) {
      List<String[]> allRecords = csvReader.readAll();
      for (String[] nextRecord : allRecords) {
        String name = nextRecord[0];
        String email = nextRecord[1];
        String country = nextRecord[2];
        String hash = nextRecord[3];
        userDataList.add(new UserTableRow(name, email, country, hash));
      }
    } catch (NoSuchFileException e) {
      logger.warn("File: " + USERS_CSV_FILE_PATH + " was not found.", e);
    } catch (IOException e) {
      logger.warn("Unexpected exception while reading file: "
          + USERS_CSV_FILE_PATH, e);
    }
    return userDataList;
  }

  boolean createUser(UserTableRow userTableRow) {
    String fileName = "src/main/resources/users.csv";
    try (
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
    ) {
      String[] row = userTableRow.toRow();
      writer.writeNext(row, false);
    } catch (IOException e) {
      logger.warn("Failed to create new user: " + userTableRow.getUserName());
      return false;
    }
    return true;
  }

  boolean writeUsers(List<UserTableRow> userTableRows) {
    String fileName = "src/main/resources/users.csv";
    try (
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
    ) {
      for (UserTableRow row : userTableRows) {
        writer.writeNext(row.toRow(), false);
      }
    } catch (IOException e) {
      logger.warn("Failed to insert " + userTableRows.size() + " new users.");
      return false;
    }
    return true;
  }

  boolean deleteUser(UserTableRow userTableRow) {
    // Copy database to list
    List<UserTableRow> userTableRowList = getAllUsers();
    // Empty database
    clearDatabase();
    // Remove user
    userTableRowList = removeUserFromList(userTableRowList, userTableRow.getUserName());
    // Write again
    writeUsers(userTableRowList);
    return true;
  }

  private List<UserTableRow> removeUserFromList(List<UserTableRow> userTableRowList,
      String userName) {
    int idToRemove = -1;
    for (int i = 0; i < userTableRowList.size(); i++) {
      if (userTableRowList.get(i).getUserName().equals(userName)) {
        idToRemove = i;
        break;
      }
    }
    if (idToRemove > -1) {
      userTableRowList.remove(idToRemove);
    }
    return userTableRowList;
  }

  private void clearDatabase() {
    String fileName = "src/main/resources/users.csv";
    try (
        CSVWriter writer = new CSVWriter(new FileWriter(fileName));
    ) {
      writer.flush();
      writer.writeNext(HEADERS);
    } catch (IOException e) {
      logger.warn("Failed to clear database - file not found.");
    }
  }
}