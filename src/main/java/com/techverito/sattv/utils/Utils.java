package com.techverito.sattv.utils;

import java.util.UUID;

public class Utils {

  private Utils() {}
  
  public static String generateRandomBasedUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
  
  public static void print(Object input) {
    System.out.print(input);
  }
  
  public static void println(Object input) {
    System.out.println(input);
  }

}
