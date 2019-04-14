package org.sda.manager;

import java.io.Console;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.sda.manager.authentication.AuthService;
import org.sda.manager.authentication.AuthServiceImpl;
import org.sda.manager.authentication.hash.impl.SHA256;
import org.sda.manager.authentication.model.User;
import org.sda.manager.comparators.UserComparator;
import org.sda.manager.database.UsersServiceImpl;
import org.sda.manager.database.services.UsersService;
import org.sda.manager.database.tables.PasswordTableRow;
import org.sda.manager.database.tables.RotType;
import org.sda.manager.database.tables.UserTableRow;
import org.sda.manager.encryption.EncryptorDecryptor;
import org.sda.manager.encryption.impl.rot.Rot13;
import org.sda.manager.encryption.impl.rot.Rot18;
import org.sda.manager.exceptions.DatabaseIntegrityException;
import org.sda.manager.exceptions.InvalidROTAlgorithmType;
import org.sda.manager.exceptions.NoPasswordsFoundException;
import org.sda.manager.exceptions.PasswordNotFoundException;
import org.sda.manager.exceptions.UserNotFoundException;
import org.sda.manager.validation.Validators;

public class Main {

  private static final int NOT_FOUND = 9991;
  private static final Logger logger = Logger.getLogger(Main.class);

  private static AuthService authService = new AuthServiceImpl();
  private static UsersService usersService = new UsersServiceImpl();
  private Console console;

  public Main() {
    this.console = System.console();
  }

  public static void main22(String[] args) {
    List<String> list = Arrays.asList("some", "random", "text", "values", "and", "digits");
    Collections.sort(list);

    List<User> uList = Arrays.asList(
        new User("name3", "password1"),
        new User("name2", "password2"),
        new User("name2", "password3"),
        new User("name1", "password4"));
    Collections.sort(uList, new UserComparator());
    System.out.println(uList);

    Collections.sort(uList, new UserComparator());
    System.out.println("uList = " + uList);
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.runApp(args);
  }


  private static void showUserDetails(User user) {
    System.out.println("User's " + user.getName() + " details: ");
    System.out.println(user);
  }

  private static void loggerExample(String[] args) {
    logger.trace("Trace Message!");
    logger.debug("Debug Message!");
    logger.info("Info Message!");
    logger.warn("Warn Message!");
    logger.error("Error Message!");
    logger.fatal("Fatal Message!");
  }

  private void runApp(String[] args) {
    String userName;
    char[] password;
    if (args.length == 0) {
      signedOutView();
      return;
    } else if (args.length == 2) {
      userName = args[0];
      password = args[1].toCharArray();
    } else {
      throw new IllegalStateException("Invalid number of arguments: " + args.length);
    }
    User user = new User(userName, password);
    boolean authenticated = isUserAuthenticated(user);
    if (authenticated) {
      userDetailsView(user);
    } else {
      signedOutView();
    }
  }

  private void signedOutView() {
    boolean finish = false;
    try (Scanner scanner = new Scanner(System.in)) {
      while (!finish) {
        System.out.println("###########################");
        System.out.println("# Welcome!");
        System.out.println("###########################");
        System.out.println("# Select one of the following options: ");
        System.out.println("# 1. Sign in.");
        System.out.println("# 2. Sign up.");
        System.out.println("# 3. List users.");
        System.out.println("# 4. Exit.");
        int option = scanNextInt(scanner);
        switch (option) {
          case 1:
            System.out.println("Enter user name:");
            String userName = scanner.nextLine();
            System.out.println("Enter password:");
            char[] password = console.readPassword();
            User user = new User(userName, password);
            if (isUserAuthenticated(user)) {
              finish = true;
              userDetailsView(user);
            } else {
              System.out.println("Invalid credentials!");
              logger.warn("Failed to log in: " + user);
            }
            break;
          case 2:
            signUp(scanner);
            break;
          case 3:
            showUserList();
            break;
          case 4:
            finish = true;
            break;
          default:
            System.out.println("Command not found.");
            break;
        }
      }
    }
  }

  private void signUp(Scanner scanner) {
    System.out.println("Enter new user name:");
    String userName = scanner.nextLine();
    System.out.println("Enter new password:");
    char[] password = console.readPassword();
    System.out.println("Enter user mail:");
    String email = scanner.nextLine();
    System.out.println("Enter user country:");
    String country = scanner.nextLine();
    User user = new User(userName, email, password, country);
    if (!Validators.validateEmail(email)) {
      System.out.println("Invalid email address.");
      return;
    }
    try {
      if (usersService.registerNewUser(user)) {
        System.out.println("Successfully created user: " + userName);
        logger.info("Created new user: " + user);
      }
    } catch (DatabaseIntegrityException e) {
      System.out.println("Unable to create user.");
      logger.info("Failed to created new user: " + user);
    }
  }

  private boolean isUserAlreadyPresent(User user) {
    for (UserTableRow user1 : usersService.findAllUsers()) {
      if (user.getName().equalsIgnoreCase(user1.getUserName())) {
        return true;
      }
    }
    return false;
  }

  private void userDetailsView(final User user) {
    boolean finish = false;
    try (Scanner scanner = new Scanner(System.in)) {
      while (!finish) {
        System.out.println("###########################");
        System.out.println("# Welcome: " + user.getName());
        System.out.println("###########################");
        System.out.println("# Select one of the following options: ");
        System.out.println("# 1. User details.");
        System.out.println("# 2. Show user passwords.");
        System.out.println("# 3. Show user's decrypted password.");
        System.out.println("# 4. User list.");
        System.out.println("# 5. Delete account.");
        System.out.println("# 0. Log out.");
        int option = scanNextInt(scanner);
        switch (option) {
          case 1:
            showUserDetails(user);
            break;
          case 2:
            showUserPasswords(user);
            break;
          case 3:
            showUserDecryptedPassword(user, scanner);
            break;
          case 4:
            showUserList();
            break;
          case 5:
            editUserView(user, scanner);
            break;
          case 6:
            try {
              boolean success = usersService.removeUser(user);
              if (success) {
                logger.debug("Successfully removed user: " + user);
                finish = true;
                signedOutView();
              }
            } catch (UserNotFoundException e) {
              logger.warn("Unable to remove user: '" + user);
            }
            break;
          case 0:
            System.out.println("Bye bye: " + user.getName());
            finish = true;
            signedOutView();
            break;
          default:
            System.out.println("Command not found.");
            break;
        }
      }
    }
  }

  private void editUserView(User user, Scanner scanner) {
    try {
      // Change optional parameters
      System.out.println("Enter new password:");
      char[] password = console.readPassword();
      System.out.println("Enter new user mail:");
      String email = scanner.nextLine();
      System.out.println("Enter new user country:");
      String country = scanner.nextLine();
      // Validate and abort if necessary
      if (!Validators.validateEmail(email)) {
        System.out.println("Invalid email address!");
        return;
      }
      user.setEmail(email);
      user.setPassword(password);
      user.setCountry(country);
      usersService.editUser(user);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void closeApp() {
    System.out.println("Bye bye!");
  }

  private void showUserList() {
    System.out.println("User list:");
    int i = 0;
    for (UserTableRow user : usersService.findAllUsers()) {
      System.out.println(++i + ". " + user.getUserName());
    }
  }

  private void showUserPasswords(User user) {
    System.out.println("User '" + user.getName() + "' passwords:");
    int i = 0;
    try {
      List<PasswordTableRow> passwordTableRows = usersService.findAllUserPasswords(user);
      logger.debug("User '" + user.getName() + "' passwords: " + passwordTableRows);
      for (PasswordTableRow passwordTableRow : passwordTableRows) {
        System.out.println(++i + ". " + passwordTableRow);
      }
    } catch (UserNotFoundException e) {
      logger.warn("Failed to find user's passwords - user doesn't exist.");
      System.out.println("Failed to find user's passwords - user doesn't exist.");
    } catch (NoPasswordsFoundException e) {
      logger.debug("There were no password assigned to user yet.");
      System.out.println("There are no password assigned to user yet.");
    }
  }

  private void showUserDecryptedPassword(User user, Scanner scanner) {
    System.out.println("Enter password database id:");
    try {
      int id = scanNextInt(scanner);
      PasswordTableRow passwordTableRow = usersService.findUserPasswordById(user, id);
      String decryptedPassword = decryptRot(passwordTableRow.getPassword(), passwordTableRow.getRotType());
      passwordTableRow.setPassword(decryptedPassword);
      System.out.println("Decrypted password: " + passwordTableRow);
    } catch (PasswordNotFoundException | NoPasswordsFoundException e) {
      System.out.println("Password with given id does not exist.");
      return;
    } catch (InvalidROTAlgorithmType invalidROTAlgorithmType) {
      System.out.println("Provided encryption algorithm is invalid.");
      return;
    } catch (UserNotFoundException e) {
      logger.warn("Failed to find user's password - user doesn't exist.");
      System.out.println("Failed to find user's password - user doesn't exist.");
    }
  }

  private String decryptRot(String password, RotType rotType) throws InvalidROTAlgorithmType {
    EncryptorDecryptor rot;
    switch (rotType) {
      case ROT13:
        rot = new Rot13();
        break;
      case ROT18:
        rot = new Rot18();
        break;
      default:
        throw new InvalidROTAlgorithmType();
    }
    return new String(rot.encrypt(password.toCharArray()));
  }


  private boolean isUserAuthenticated(User user) {
    return authService.isAuthenticated(user, new SHA256());
  }

  private int scanNextInt(Scanner scanner) {
    try {
      System.out.println("Enter integer value:");
      int i = scanner.nextInt();
      scanner.nextLine();
      return i;
    } catch (InputMismatchException e) {
      System.out.println("Invalid input, please try again");
      logger.debug("User eneter invalid data type: ", e);
      scanner.nextLine();
      return NOT_FOUND;
    }
  }
}
