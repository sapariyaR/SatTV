package com.techverito.sattv.consolreader.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.techverito.sattv.consolreader.UserSelection;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.service.DthDataProviderService;
import com.techverito.sattv.utils.Utils;

public class CustomChannelReader extends ReaderDecorator {

  private ConsolReader consolReader;
  
  public CustomChannelReader(DthDataProviderService dataProviderService,Scanner scanner,ConsolReader reader) {
    super(dataProviderService, scanner);
    this.consolReader = reader;
  }
  
  @Override
  public UserSelection read(UserSelection userSelection) {
    if(this.consolReader != null) {
      userSelection = this.consolReader.read(userSelection);
    }
    ChannelGroup customChannelDetails = readCustomChannelDetails();
    userSelection.setSelectedChannelGroup(customChannelDetails);
    return userSelection;
  }
  
  private ChannelGroup readCustomChannelDetails() {
    Utils.print("Enter channel names you wish to purchase separated by a comma:");
    String channelNames = ConsolReader.readLineUntilValid(super.getScanner());
    List<String> channelNameList = Arrays.asList(channelNames.split(","));
    ChannelGroup channelGroupsObj = super.getDataProviderService().createCustomChannelGroup(channelNameList);
    if (channelGroupsObj == null) {
      Utils.println("*** ERROR : Unable to find Channels");
      return readCustomChannelDetails();
    }
    return channelGroupsObj;
  }

}
