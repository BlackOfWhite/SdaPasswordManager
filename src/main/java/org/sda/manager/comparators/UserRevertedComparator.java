package org.sda.manager.comparators;

import java.util.Comparator;
import org.sda.manager.authentication.model.User;

public class UserRevertedComparator implements Comparator<User> {

  @Override
  public int compare(User o1, User o2) {
    int result = o1.getName().compareTo(o2.getName());
    if (result != 0) {
      return result * -1;
    }
    result = new String(o1.getPassword()).compareTo(new String(o2.getPassword()));
    return result * -1;
  }
}
