package com.techverito.sattv.consolreader.reader;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.techverito.sattv.consolreader.UserSelection;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.service.DthDataProviderService;
import com.techverito.sattv.utils.Utils;

public class BasePackReader extends ReaderDecorator {

  private ConsolReader consolReader;
  
  public BasePackReader(DthDataProviderService dataProviderService,Scanner scanner, ConsolReader reader) {
    super(dataProviderService, scanner);
    this.consolReader = reader;
  }
  
  @Override
  public UserSelection read(UserSelection userSelection) {
    if(this.consolReader != null) {
      userSelection = this.consolReader.read(userSelection);
    }
    ChannelGroup readBasePackDetails = readBasePackDetails();
    userSelection.setSelectedChannelGroup(readBasePackDetails);
    return userSelection;
  }
  
  private ChannelGroup readBasePackDetails() {
    List<ChannelGroup> basePack = super.getDataProviderService().retrivePackagesBasedOnComplimentary(false);
    String commaSeperatedBasePack = String.join(",", basePack.stream()
        .map(eachItem -> eachItem.getGroupName() + ":'" + eachItem.getAlias() + "'")
        .collect(Collectors.toList()));

    Utils.print("Enter the Pack you wish to purchase: (" + commaSeperatedBasePack + ") : ");
    String packValue = ConsolReader.readLineUntilValid(super.getScanner());
    boolean isValidPackSelection = basePack.stream().map(eachItem -> eachItem.getAlias()).anyMatch(eachAlias -> eachAlias.equalsIgnoreCase(packValue));
    if (!isValidPackSelection) {
      Utils.println("*** ERROR : Error in Base pack Selection.");
      return readBasePackDetails();
    }else {
      Optional<ChannelGroup> basePackObj = basePack.stream().filter(eachItem -> eachItem.getAlias().equalsIgnoreCase(packValue)).findFirst();
      return basePackObj.get();
    }
  }

}
