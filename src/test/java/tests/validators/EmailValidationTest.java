package tests.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sda.manager.validation.Validators;

public class EmailValidationTest {

  @Test
  public void validateEmailCharacterSetTest() {
    String email = "-.Test@example.com";
    char[] legalCharacters = new char[]{'@', '-', '.'};
    assertTrue("Email character set validation is expected to be true.",
        Validators.containsValidCharacterSet(email, legalCharacters));

    email = "-!Test@example.com";
    assertFalse("Email character set validation is expected to be false.",
        Validators.containsValidCharacterSet(email, legalCharacters));
  }

  @Test
  public void validateEmailTest() {
    String email = "Te.st@example.com";
    assertTrue("Email is expected to be valid.",
        Validators.validateEmail(email));

    email = "T! est@example.com";
    assertFalse("Email is expected to be invalid.",
        Validators.validateEmail(email));
  }


}
