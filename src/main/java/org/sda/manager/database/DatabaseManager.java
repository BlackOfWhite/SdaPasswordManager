package org.sda.manager.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.sda.manager.database.tables.PasswordTableRow;
import org.sda.manager.database.tables.RotType;
import org.sda.manager.database.tables.UserTableRow;

public final class DatabaseManager {

  private static final Logger logger = Logger.getLogger(DatabaseManager.class);
  //  private static final String USERS_CSV_FILE_PATH = "/users.csv";
  private static final Path WRITE_USERS_CSV_FILE_PATH = Paths.get(
      "C:\\Users\\pniewinski\\IdeaProjects\\"
          + "SdaPasswordManager\\src\\main\\resources\\users.csv");
  private static final String PASSWORDS_CSV_FILE_PATH = "/passwords.csv";
  private static final Path WRITE_PASSWORDS_CSV_FILE_PATH = Paths.get(
      "C:\\Users\\pniewinski\\IdeaProjects\\"
          + "SdaPasswordManager\\src\\main\\resources\\passwords.csv");
  private static final DatabaseManager INSTANCE = new DatabaseManager();
  private static final String[] USERS_HEADERS =
      new String[]{"name", "email", "country", "hash"};

  private static final String[] PASSWORDS_HEADERS = new String[]{
      "ID", "Title", "Desc", "Email", "URL", "Password", "ROT"
  };

  private DatabaseManager() {

  }

  public static DatabaseManager getInstance() {
    return INSTANCE;
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
      logger.warn("File: " + WRITE_USERS_CSV_FILE_PATH + " was not found.", e);
    } catch (IOException e) {
      logger.warn("Unexpected exception while reading file: "
          + WRITE_USERS_CSV_FILE_PATH, e);
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
      writer.writeNext(USERS_HEADERS);
    } catch (IOException e) {
      logger.warn("Failed to clear database - file not found.");
    }
  }

  List<PasswordTableRow> getAllPasswordsForUser(String userId) {
    List<PasswordTableRow> passwords = new ArrayList<>();
    try (
        BufferedReader reader = Files.newBufferedReader(WRITE_PASSWORDS_CSV_FILE_PATH);
        CSVReader csvReader = new CSVReaderBuilder(reader)
            .withSkipLines(1).build()
    ) {
      List<String[]> allRecords = csvReader.readAll();
      for (String[] nextRecord : allRecords) {
        String id = nextRecord[0];
        String uId = nextRecord[1];
        String title = nextRecord[2];
        String desc = nextRecord[3];
        String email = nextRecord[4];
        String url = nextRecord[5];
        String password = nextRecord[6];
        String rot = nextRecord[7];
        if (userId.equals(uId)) {
          passwords.add(new PasswordTableRow(id, uId, title, RotType.valueOf(rot), password));
        }
      }
    } catch (NoSuchFileException e) {
      logger.warn("File: " + PASSWORDS_CSV_FILE_PATH + " was not found.", e);
    } catch (IOException e) {
      logger.warn("Unexpected exception while reading file: "
          + PASSWORDS_CSV_FILE_PATH, e);
    }
    return passwords;
  }
}