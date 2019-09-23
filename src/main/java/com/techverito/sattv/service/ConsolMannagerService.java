package com.techverito.sattv.service;

import java.util.List;
import java.util.Scanner;
import com.techverito.sattv.consolreader.UserSelection;
import com.techverito.sattv.consolreader.reader.BasePackReader;
import com.techverito.sattv.consolreader.reader.ConsolReader;
import com.techverito.sattv.consolreader.reader.CustomChannelReader;
import com.techverito.sattv.consolreader.reader.MonthReader;
import com.techverito.sattv.consolreader.reader.RegionalPackReader;
import com.techverito.sattv.dao.Tranasation;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.utils.Utils;

public class ConsolMannagerService {

  private static final String DOTLINE = "----------------------------";
  private DthDataProviderService dataProviderService;
  private Scanner scanner;
  private Tranasation lastSuccessTranasaction = null;

  public ConsolMannagerService(DthDataProviderService dataProviderService,Scanner scanner) {  
    this.dataProviderService = dataProviderService;
    this.scanner = scanner;
  }

  public void run() {
    Utils.println(dataProviderService.getApplicationBanner());
    Utils.println(" ");
    Utils.println(DOTLINE);

    boolean isStillReading = true;
    while (isStillReading) {
      Utils.println("");
      Utils.println(dataProviderService.getIntroQuestions());
      Utils.print("Enter the choice: ");
      String choice = scanner.nextLine();
      if (choice != null && !choice.isEmpty()) {
        isStillReading = this.performActionOnUserInput(choice);
      }
    }
  }

  private Boolean performActionOnUserInput(String choice) {
    switch (choice) {
      case "1":
        Utils.println(DOTLINE);
        Utils.println("Available Packs");
        Utils.println(DOTLINE);
        List<ChannelGroup> retriveAvailablePackages = dataProviderService.retriveAvailablePackages();
        retriveAvailablePackages.forEach(eachItem -> Utils.println(eachItem));
        Utils.println(DOTLINE);
        return true;
      case "2":
        Utils.println(DOTLINE);
        Utils.println("Available Channels");
        Utils.println(DOTLINE);
        List<Channel> retriveAvailableChannels = dataProviderService.retriveAvailableChannels();
        retriveAvailableChannels.forEach(eachItem -> Utils.println(eachItem));
        Utils.println(DOTLINE);
        return true;
      case "3":
        Utils.println(DOTLINE);
        this.runChannelSelectionReader("3");
        Utils.println(DOTLINE);
        return true;
      case "4":
        Utils.println(DOTLINE);
        this.runChannelSelectionReader("4");
        Utils.println(DOTLINE);
        return true;
      case "5":
        Utils.println(DOTLINE);
        this.runChannelSelectionReader("5");
        Utils.println(DOTLINE);
        return true;
      case "6":
        Utils.println(DOTLINE);
        this.subscriptionDetails();
        Utils.println(DOTLINE);
        return true;
      default:
        scanner.close();
        Utils.println("Thanks :)");
        return false;
    }
  }
  
  private ConsolReader createConsolReaderBasedOnUserInput(String userInputType) {
    if(userInputType.equals("3")) {
      return new MonthReader(dataProviderService, scanner, new RegionalPackReader(dataProviderService, scanner, new BasePackReader(dataProviderService, scanner, null)));
    }else if(userInputType.equals("4")) {
      return new MonthReader(dataProviderService, scanner, new RegionalPackReader(dataProviderService, scanner, null));
    }else {
      return new MonthReader(dataProviderService, scanner, new CustomChannelReader(dataProviderService, scanner, null));
    }
  }
  
  private void runChannelSelectionReader(String userInputType) {
    ConsolReader consolReader = createConsolReaderBasedOnUserInput(userInputType);
    UserSelection userSelection = new UserSelection();
    consolReader.read(userSelection);
    this.lastSuccessTranasaction = dataProviderService.calculateBillDetails(userSelection.getNumberOfMonth(), 
        userSelection.getSelectedChannelGroup());
    Utils.println(this.lastSuccessTranasaction);
  }
  
  private void subscriptionDetails() {
    if (this.lastSuccessTranasaction != null) {
      Utils.println(this.lastSuccessTranasaction);
    } else {
      Utils.println("You have not Subscribe yet any pack or channels.");
    }
  }

}
