package org.sda.manager;

import java.io.Console;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.sda.manager.authentication.model.User;

public class Main {

  private static final int NOT_FOUND = 9991;

  private List<User> userList = Arrays.asList(
      new User("name1", "password1"),
      new User("name2", "password2"),
      new User("name3", "password3"),
      new User("name4", "password4"));
  //  private static final Logger logger = Logger.getLogger(Main.class);
//  private static AuthService authService = new AuthServiceImpl();
  private Console console;

  public Main() {
    this.console = System.console();
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.runApp(args);
  }

  public static void main15(String[] args) {
    for (String s : args) {
      System.out.println("Arg: " + s);
    }
  }

  public static void main23(String[] args) {
    String s = Integer.toHexString('j');
    System.out.println(s);
  }

  private static void showUserDetails(User user) {
    System.out.println("User's " + user.getName() + " details: ");
    System.out.println(user);
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
        System.out.println("# 2. List users.");
        System.out.println("# 3. Exit.");
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
            }
            break;
          case 2:
            showUserList();
            break;
          case 3:
            finish = true;
            break;
          default:
            System.out.println("Command not found.");
            break;
        }
      }
    }
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
        System.out.println("# 2. User list.");
        System.out.println("# 3. Log out.");
        int option = scanNextInt(scanner);
        switch (option) {
          case 1:
            showUserDetails(user);
            break;
          case 2:
            showUserList();
            break;
          case 3:
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

  private void closeApp() {
    System.out.println("Bye bye!");
  }

  private void showUserList() {
    System.out.println("User list:");
    int i = 0;
    for (User user : userList) {
      System.out.println(++i + ". " + user.getName());
    }
  }

  private boolean isUserAuthenticated(User user) {
    return userList.contains(user);
  }

  private void loggerExample(String[] args) {
//    logger.trace("Trace Message!");
//    logger.debug("Debug Message!");
//    logger.info("Info Message!");
//    logger.warn("Warn Message!");
//    logger.error("Error Message!");
//    logger.fatal("Fatal Message!");
  }

  private int scanNextInt(Scanner scanner) {
    try {
      System.out.println("Enter integer value:");
      int i = scanner.nextInt();
      scanner.nextLine();
      return i;
    } catch (InputMismatchException e) {
      System.out.println("Invalid input, please try again");
      scanner.nextLine();
      return NOT_FOUND;
    }
  }
}
