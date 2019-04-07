package tests.database;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.sda.manager.database.DatabaseManager;

public class DatabaseManagerTests {

  @Test
  public void singletonTest() {
    DatabaseManager databaseManager1 = DatabaseManager.getInstance();
    DatabaseManager databaseManager2 = DatabaseManager.getInstance();
    assertSame("Objects should have the same memory reference.",
        databaseManager1, databaseManager2);
  }
}
