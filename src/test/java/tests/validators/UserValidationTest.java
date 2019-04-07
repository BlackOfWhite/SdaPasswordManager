package tests.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sda.manager.validation.Validators;

public class UserValidationTest {

  private static int count = 0;

  @BeforeClass
  public static void beforeClass() {
    System.out.println("Before class");
  }

  @AfterClass
  public static void afterClass() {
    System.out.println("After class");
  }

  @Before
  public void beforeEach() {
    count++;
  }

  @After
  public void afterEach() {
    count--;
  }

  @Test
  public void userNameLengthValidation() {
    System.out.println("test:" + count);
    String userName = "TommyLeeJohnes";
    assertTrue("User name's length is expected to be true.",
        Validators.isLengthWithinRange(userName, 3, 20));

    userName = "Ab";
    assertFalse("User name's length is expected to be false.",
        Validators.isLengthWithinRange(userName, 3, 20));
  }

  @Test
  public void passwordValidation() {
    System.out.println("test:" + count);
    String password = "123#AbC";
    assertTrue("Password is expected to be valid.",
        Validators.validatePassword(password));

    password = "Ab";
    assertFalse("Password is expected to be invalid.",
        Validators.validatePassword(password));
  }


}
