package com.techverito.sattv.consolreader.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.techverito.sattv.consolreader.UserSelection;
import com.techverito.sattv.service.DthDataProviderService;
import com.techverito.sattv.utils.Utils;

public class MonthReader extends ReaderDecorator {

  private ConsolReader consolReader;
  
  public MonthReader(DthDataProviderService dataProviderService,Scanner scanner,ConsolReader reader) {
    super(dataProviderService, scanner);
    this.consolReader = reader;
  }
  
  @Override
  public UserSelection read(UserSelection userSelection) {
    if(this.consolReader != null) {
      userSelection = this.consolReader.read(userSelection);
    }
    Integer monthValue = readMonthDetails();
    userSelection.setNumberOfMonth(monthValue);
    return userSelection;
  }
  
  private Integer readMonthDetails() {
    List<String> sampleMonthArray = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    Utils.print("Enter the months:");
    String monthValue = ConsolReader.readLineUntilValid(super.getScanner());
    boolean isValidMonthNumber = sampleMonthArray.stream().anyMatch(eachMonth -> eachMonth.equalsIgnoreCase(monthValue));
    if (!isValidMonthNumber) {
      Utils.println("*** ERROR : Error in Month Selection.");
      return readMonthDetails();
    }
    return Integer.parseInt(monthValue);
  }

}
