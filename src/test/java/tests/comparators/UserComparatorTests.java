package tests.comparators;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.sda.manager.authentication.model.User;
import org.sda.manager.comparators.UserComparator;
import org.sda.manager.comparators.UserRevertedComparator;

public class UserComparatorTests {

  @Test
  public void userComparatorTest() {
    List<User> uList = Arrays.asList(
        new User("name3", "password3"),
        new User("name2", "password22"),
        new User("name2", "password21"),
        new User("name1", "password1"));

    List<User> expected = Arrays.asList(
        new User("name1", "password1"),
        new User("name2", "password21"),
        new User("name2", "password22"),
        new User("name3", "password3"));

    Collections.sort(uList, new UserComparator());

    assertEquals("Two list should have same order.", expected, uList);
  }

  @Test
  public void userRevertedComparatorTest() {
    List<User> uList = Arrays.asList(
        new User("name2", "password21"),
        new User("name3", "password3"),
        new User("name1", "password1"),
        new User("name2", "password22"));

    List<User> expected = Arrays.asList(
        new User("name3", "password3"),
        new User("name2", "password22"),
        new User("name2", "password21"),
        new User("name1", "password1"));

    Collections.sort(uList, new UserRevertedComparator());

    assertEquals("Two list should have same reverted order.", expected, uList);
  }

}
