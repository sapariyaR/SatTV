package com.techverito.sattv.consolreader.reader;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.techverito.sattv.consolreader.UserSelection;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.service.DthDataProviderService;
import com.techverito.sattv.utils.Utils;

public class RegionalPackReader extends ReaderDecorator {
  
  private ConsolReader consolReader;
  
  public RegionalPackReader(DthDataProviderService dataProviderService,Scanner scanner,ConsolReader reader) {
    super(dataProviderService, scanner);
    this.consolReader = reader;
  }
  
  @Override
  public UserSelection read(UserSelection userSelection) {
    if(this.consolReader != null) {
      userSelection = this.consolReader.read(userSelection);
    }
    ChannelGroup readRegionalPackDetails = readRegionalPackDetails();
    userSelection.setSelectedChannelGroup(readRegionalPackDetails);
    return userSelection;
  }
  
  private ChannelGroup readRegionalPackDetails() {
    List<ChannelGroup> regionalPack = super.getDataProviderService().retrivePackagesBasedOnComplimentary(true);
    String commaSeperatedReginalPack = String.join(",", regionalPack.stream()
        .map(eachItem -> eachItem.getGroupName() + ":'" + eachItem.getAlias() + "'")
        .collect(Collectors.toList()));
    Utils.print("Enter the regional pack you wish to select: (" + commaSeperatedReginalPack + ") : ");
    String reginalPackValue = ConsolReader.readLineUntilValid(super.getScanner());
    boolean isValidRegionalPackSelection = regionalPack.stream().map(eachItem -> eachItem.getAlias()).anyMatch(eachAlias -> eachAlias.equalsIgnoreCase(reginalPackValue));
    if (!isValidRegionalPackSelection) {
      Utils.println("*** ERROR : Error in Regional pack Selection.");
      return this.readRegionalPackDetails();
    } else {
      Optional<ChannelGroup> regionalPackObj = regionalPack.stream().filter(eachItem -> eachItem.getAlias().equalsIgnoreCase(reginalPackValue)).findFirst();
      return regionalPackObj.get();
    }
  }
  
}
