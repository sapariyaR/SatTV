package com.techverito.sattv.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.techverito.sattv.dao.DthDataProviderService;
import com.techverito.sattv.dao.Tranasation;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.utils.Utils;

public class DTHConsolMannager {

  private static final String DOTLINE = "----------------------------";
  private DthDataProviderService dataProviderService;
  private Scanner scanner;
  private List<String> sampleMonthArray = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
  private Tranasation lastSuccessTranasaction = null;

  public DTHConsolMannager() {
    dataProviderService = new DthDataProviderService();
    scanner = new Scanner(System.in);
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
        this.mannageBasePackageSelection();
        Utils.println(DOTLINE);
        return true;
      case "4":
        Utils.println(DOTLINE);
        this.mannageRegionalPackageSelection();
        Utils.println(DOTLINE);
        return true;
      case "5":
        Utils.println(DOTLINE);
        this.mannageCustomChannelSelection();
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

  private void mannageBasePackageSelection() {
    List<ChannelGroup> basePack = this.dataProviderService.retrivePackagesBasedOnComplimentary(false);
    String commaSeperatedBasePack = String.join(",", basePack.stream()
        .map(eachItem -> eachItem.getGroupName() + ":'" + eachItem.getAlias() + "'")
        .collect(Collectors.toList()));

    Utils.print("Enter the Pack you wish to purchase: (" + commaSeperatedBasePack + ") : ");
    String packValue = readLineUntilValid();
    boolean isValidPackSelection = basePack.stream().map(eachItem -> eachItem.getAlias()).anyMatch(eachAlias -> eachAlias.equalsIgnoreCase(packValue));
    if (!isValidPackSelection) {
      Utils.println("*** ERROR : Error in Base pack Selection.");
      this.performActionOnUserInput("3");
    } else {
      
      List<ChannelGroup> regionalPack = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
      String commaSeperatedReginalPack = String.join(",", regionalPack.stream()
          .map(eachItem -> eachItem.getGroupName() + ":'" + eachItem.getAlias() + "'")
          .collect(Collectors.toList()));
      Utils.print("Enter the regional pack you wish to select: (" + commaSeperatedReginalPack + ") : ");
      String reginalPackValue = readLineUntilValid();
      boolean isValidRegionalPackSelection = regionalPack.stream().map(eachItem -> eachItem.getAlias()).anyMatch(eachAlias -> eachAlias.equalsIgnoreCase(reginalPackValue));
      if (!isValidRegionalPackSelection) {
        Utils.println("*** ERROR : Error in Regional pack Selection.");
        this.performActionOnUserInput("3");
      
      } else {
        Utils.print("Enter the months:");
        String monthValue = readLineUntilValid();
        boolean isValidMonthNumber = this.sampleMonthArray.stream().anyMatch(eachMonth -> eachMonth.equalsIgnoreCase(monthValue));
        if (!isValidMonthNumber) {
          Utils.println("*** ERROR : Error in Month Selection.");
          this.performActionOnUserInput("3");
        } else {
          Optional<ChannelGroup> basePackObj = basePack.stream().filter(eachItem -> eachItem.getAlias().equalsIgnoreCase(packValue)).findFirst();
          Optional<ChannelGroup> regionalPackObj = regionalPack.stream().filter(eachItem -> eachItem.getAlias().equalsIgnoreCase(reginalPackValue)).findFirst();
          Utils.println("You have successfully purchased the following packs : " + basePackObj.get().getGroupName() + "-" + regionalPackObj.get().getGroupName());
          this.lastSuccessTranasaction = dataProviderService.calculateBillDetails(Integer.parseInt(monthValue), Arrays.asList(basePackObj.get(), regionalPackObj.get()));
          Utils.println(this.lastSuccessTranasaction);
        }
      }
    }
  }

  private void mannageRegionalPackageSelection() {
    List<ChannelGroup> regionalPack = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    String commaSeperatedReginalPack = String.join(",", regionalPack.stream()
        .map(eachItem -> eachItem.getGroupName() + ":'" + eachItem.getAlias() + "'")
        .collect(Collectors.toList()));
    Utils.print("Enter the regional pack you wish to select: (" + commaSeperatedReginalPack + ") : ");
    String reginalPackValue = readLineUntilValid();
    boolean isValidRegionalPackSelection = regionalPack.stream().map(eachItem -> eachItem.getAlias()).anyMatch(eachAlias -> eachAlias.equalsIgnoreCase(reginalPackValue));
    if (!isValidRegionalPackSelection) {
      Utils.println("*** ERROR : Error in Regional pack Selection.");
      this.performActionOnUserInput("4");
    } else {
      Utils.print("Enter the months:");
      String monthValue = readLineUntilValid();
      boolean isValidMonthNumber = this.sampleMonthArray.stream().anyMatch(eachMonth -> eachMonth.equalsIgnoreCase(monthValue));
      if (!isValidMonthNumber) {
        Utils.println("*** ERROR : Error in Month Selection.");
        this.performActionOnUserInput("4");
      } else {
        // All value enter by user is correct
        Optional<ChannelGroup> regionalPackObj = regionalPack.stream().filter(eachItem -> eachItem.getAlias().equalsIgnoreCase(reginalPackValue)).findFirst();
        Utils.println("You have successfully purchased the following packs : " + regionalPackObj.get().getGroupName());
        this.lastSuccessTranasaction = dataProviderService.calculateBillDetails(Integer.parseInt(monthValue), Arrays.asList(regionalPackObj.get()));
        Utils.println(this.lastSuccessTranasaction);
      }
    }
  }

  private void mannageCustomChannelSelection() {
    Utils.print("Enter channel names you wish to purchase separated by a comma:");
    String channelNames = readLineUntilValid();
    List<String> channelNameList = Arrays.asList(channelNames.split(","));
    List<ChannelGroup> channelGroupsObj = dataProviderService.createCustomChannelGroup(channelNameList);
    if (channelGroupsObj == null) {
      Utils.println("*** ERROR : Unable to find Channels");
      this.performActionOnUserInput("5");
    } else {
      Utils.print("Enter the months:");
      String monthValue = readLineUntilValid();
      boolean isValidMonthNumber = this.sampleMonthArray.stream().anyMatch(eachMonth -> eachMonth.equalsIgnoreCase(monthValue));
      if (!isValidMonthNumber) {
        Utils.println("*** ERROR : Error in Month Selection.");
        this.performActionOnUserInput("5");
      } else {
        // All value enter by user is correct
        Utils.println("You have successfully purchased the following packs : " + channelGroupsObj.get(0).getGroupName());
        this.lastSuccessTranasaction = dataProviderService.calculateBillDetails(Integer.parseInt(monthValue), channelGroupsObj);
        Utils.println(this.lastSuccessTranasaction);
      }
    }
  }

  private void subscriptionDetails() {
    if (this.lastSuccessTranasaction != null) {
      Utils.println(this.lastSuccessTranasaction);
    } else {
      Utils.println("You have not Subscribe yet any pack or channels.");
    }
  }

  private String readLineUntilValid() {
    String input = scanner.nextLine();
    if (input == null || input.equals("")) {
      return this.readLineUntilValid();
    }
    return input;
  }
}
