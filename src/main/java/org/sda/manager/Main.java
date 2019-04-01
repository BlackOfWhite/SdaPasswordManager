package org.sda.manager;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.sda.manager.authentication.model.User;
import org.sda.manager.validation.Validators;

public class Main {

  List<String> stringList = Arrays.asList("string1", "string2");
  List<User> userList = Arrays.asList(
      new User("name1", "password1"),
      new User("name2", "password2"),
      new User("name3", "password3"));

  //  private static final Logger logger = Logger.getLogger(Main.class);
//  private static AuthService authService = new AuthServiceImpl();
  Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    Main main = new Main();
    System.out.println("Started.");
    main.validationExample();
    User userExample = new User("User", "Password");
    boolean authenticated = main.authenticationExample(userExample);
    main.scanNextIntExample();
    main.logInExample(args);
    main.menuExample(args);
    System.out.println("Done.");
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

  public static void main1(String[] args) throws IOException {
    System.out.println("World");
    Scanner scanner = new Scanner(System.in);
    boolean isClosed = false;
    while (!isClosed) {
      System.out.println("Hello user: Tomek");
      System.out.println("This is menu:\n1. Do something1\n2.Exit");
      int digit = scanner.nextInt();
      switch (digit) {
        case 1:
//          doSomething1();
          System.out.println("Did something!");
          break;
        case 2:
          isClosed = true;
          break;
        default:
          System.out.println("Wrong input, try again");
          break;
      }
    }
  }

  public static void mainTernaryOperator(String[] args) {
    new Main().getName(1);
    new Main().getNameTernary(1);
  }

  public static void main18(String[] args) {

  }

  private static void showUserDetails() {
    System.out.println("User details.");
  }

  private void menuExample(String[] args) {
    String username = "Tom Jerry";
    System.out.println("Hello user: " + username);
    System.out.println("Select one of the following options: ");
    System.out.println("1. User details.");
    System.out.println("2. Log out.");
    Scanner scanner = new Scanner(System.in);
    boolean finish = false;
    while (!finish) {
      try {
        int option = scanner.nextInt();
        switch (option) {
          case 1:
            showUserDetails();
            break;
          case 2:
            finish = true;
            break;
          default:
            System.out.println("Command not found.");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input, please try again");
        scanner.next();
      }
    }
    System.out.println("Bye bye " + username);
  }

  /**
   * To console only sout, log to log.
   */
  private void logInExample(String[] args) {
    String userName = "test";
    char[] password = "password".toCharArray();
    if (args.length > 0) {
      if (args.length == 2) {
        userName = args[0];
        password = args[1].toCharArray();
      } else {
        throw new IllegalArgumentException("Two arguments are required. Found: " + args.length);
      }
    } else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter user name:");
      userName = scanner.nextLine();
      Console console = System.console();
      userName = console.readLine("Enter user name: ");
      password = console.readPassword("Enter your password: ");
    }
    User user = new User(userName, password);
    System.out.println("Logged as: " + userName + ", password: " + new String(password));
//    logger.debug("Logged as: " + user);
//    boolean success = authService.isAuthenticated(user, new SHA256());
//    logger.debug("User was " + (success ? "" : "not ") + "successfully authenticated");
  }

  private void validationExample() {
    String password = "Abc";
    boolean success = Validators.validatePassword(password);
  }

  private boolean authenticationExample(User user) {
    if (userList.contains(user)) {
      return true;
    } else {
      return false;
    }
  }

  private void loggerExample(String[] args) {
//    logger.trace("Trace Message!");
//    logger.debug("Debug Message!");
//    logger.info("Info Message!");
//    logger.warn("Warn Message!");
//    logger.error("Error Message!");
//    logger.fatal("Fatal Message!");
  }

  private int scanNextIntExample() {
    System.out.println("Enter integer value.");
    int i = scanner.nextInt();
    scanner.nextLine();
    return i;
  }

  public String getName(int number) {
    String s = null;
    if (number == 0) {
      s = "this is 0";
    } else {
      s = "more than 0";
    }
    return s;
  }

  public String getNameTernary(int number) {
    return number == 0 ? "this is 0" : "more than 0";
  }
}
