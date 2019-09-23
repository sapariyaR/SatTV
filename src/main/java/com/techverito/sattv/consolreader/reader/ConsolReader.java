package com.techverito.sattv.consolreader.reader;

import java.util.Scanner;
import com.techverito.sattv.consolreader.UserSelection;

public interface ConsolReader {

  public UserSelection read(UserSelection userSelection);
  
  public static String readLineUntilValid(Scanner scanner) {
    String input = scanner.nextLine();
    if (input == null || input.equals("")) {
      return readLineUntilValid(scanner);
    }
    return input;
  }
}
