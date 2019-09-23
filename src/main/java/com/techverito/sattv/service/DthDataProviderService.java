package com.techverito.sattv.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.techverito.sattv.dao.DataProvider;
import com.techverito.sattv.dao.Tranasation;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.utils.Utils;

/**
 * @author Ravi Sapariya
 * This Service fetch data from Data Provider and perform some computation on it.
 */
public class DthDataProviderService {

  private DataProvider dataProvider;
  
  public DthDataProviderService(DataProvider dataProvider) {
    this.dataProvider = dataProvider;
  }
  
  public String getApplicationBanner() {
    String banner = "__          __  _                            _           _____       _ _________      __\r\n" + 
        "\\ \\        / / | |                          | |         / ____|     | |__   __\\ \\    / /\r\n" + 
        " \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___  | |_ ___   | (___   __ _| |_ | |   \\ \\  / / \r\n" + 
        "  \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\   \\___ \\ / _` | __|| |    \\ \\/ /  \r\n" + 
        "   \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) |  ____) | (_| | |_ | |     \\  /   \r\n" + 
        "    \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/  |_____/ \\__,_|\\__||_|      \\/    ";
    return banner;
  }
  
  public String getIntroQuestions() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("**************************************\n");
    stringBuilder.append("1. View available packs \n");
    stringBuilder.append("2. View available channels \n");
    stringBuilder.append("3. Purchase base pack \n");
    stringBuilder.append("4. Purchase regional pack \n");
    stringBuilder.append("5. Purchase Al-la-carte \n");
    stringBuilder.append("6. View current subscription details \n");
    stringBuilder.append("7. Exit \n");
    stringBuilder.append("**************************************");
    stringBuilder.append(" \n");
    stringBuilder.append(" \n");
    return stringBuilder.toString();
  }
  
  
  /**
   * @return List of <ChannelGroup> all available TV channel Packages
   */
  public List<ChannelGroup> retriveAvailablePackages(){
    return this.dataProvider.getPackages();
  }
  
  /**
   * @param isComplimentary = true Return Regional packages
   * @param isComplimentary = false Return Base packages
   * @return List of <ChannelGroup> available Packages based on @param
   */
  public List<ChannelGroup> retrivePackagesBasedOnComplimentary(Boolean isComplimentary) {

    if (isComplimentary) {
      return this.dataProvider.getPackages().stream()
          .filter(eachPackage -> eachPackage.getIsComplimentary())
          .collect(Collectors.toList());
    }
    return this.dataProvider.getPackages().stream()
        .filter(eachPackage -> !eachPackage.getIsComplimentary())
        .collect(Collectors.toList());
  }
  
  public List<Channel> retriveAvailableChannels(){
    return new ArrayList<>(this.dataProvider.getChannels().values());
  }
  
  /**
   * @param numberOfMonth
   * @param packages
   * @return Tranasation Object
   * This Service is responsible to calculate Billing details based on @param numberOfMonth, packages.
   * In below calculation Default GST rate is 15%
   * If user subscribe for 6 or more Rate of Discount is 10% and for 3 or more Rate of Discount is 5%
   */
  public Tranasation calculateBillDetails(Integer numberOfMonth,List<ChannelGroup> packages) {
    Tranasation tranasation = new Tranasation();
    Double totalPrice = null;
    if(packages.size() > 1) {
      totalPrice = packages.stream().filter(eachPack -> !eachPack.getIsComplimentary()).mapToDouble(eachPack -> eachPack.getPrice()).sum();
    }else {
      totalPrice = packages.stream().mapToDouble(eachPack -> eachPack.getPrice()).sum();
    }
    tranasation.setPackNames(packages.stream().map(eachItem -> eachItem.getGroupName()).collect(Collectors.toList()));
    tranasation.setSubscribedChannels(packages.stream().flatMap(eachPack -> eachPack.getChannels().stream()).map(each -> each.getName()).collect(Collectors.toList()));
    tranasation.setPricePerMonth(totalPrice);
    tranasation.setNumberOfMonth(numberOfMonth);
    tranasation.setTotalPrice(numberOfMonth * totalPrice);
    Double gstRateInRs = (tranasation.getTotalPrice() * tranasation.getGstRate()) / 100;
    tranasation.setBillingAmount(gstRateInRs+tranasation.getTotalPrice());
    tranasation.setDiscount(numberOfMonth >= 6 ? 10 : numberOfMonth >=3 ? 5:0);
    if(tranasation.getDiscount() > 0) {
      Double effectiveFinalAmount = (tranasation.getBillingAmount() * tranasation.getDiscount()) / 100;
      tranasation.setFinalPrice(tranasation.getBillingAmount() - effectiveFinalAmount);
    }else {
      tranasation.setFinalPrice(tranasation.getBillingAmount());
    }
    return tranasation;
  }
  
  
  /**
   * @param channelNames
   * @return
   * This Service Create Custom Channel Group based on channel name list.
   * If not channel name match with actual channel this Service return null.
   */
  public ChannelGroup createCustomChannelGroup(List<String> channelNames){
    List<Channel> availableChannels = retriveAvailableChannels();
    Map<String, Channel> nameChannelMap = availableChannels.stream().collect(Collectors.toMap(eachKey -> eachKey.getName(), value->value));
    List<Channel> channels = findChannelByName(nameChannelMap,channelNames);
    if(channels == null) {
      return null;
    }
    ChannelGroup customChannelGroup = new ChannelGroup(Utils.generateRandomBasedUUID(), "Al-la-carte", false, "AI", channels.stream().mapToDouble(eachItem -> eachItem.getPrice()).sum());
    customChannelGroup.setChannels(new HashSet<>(channels));
    return customChannelGroup;
  }
  
  private List<Channel> findChannelByName(Map<String, Channel> nameChannelMap,List<String> channelNames){
    List<Channel> channels = new ArrayList<>();
    channelNames.forEach(eachName -> {
      if(nameChannelMap.get(eachName) != null) {
        channels.add(nameChannelMap.get(eachName));
      }
    });
    return !channels.isEmpty() ? channels : null;
  }
  
}
