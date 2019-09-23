package com.techverito.sattv.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.entity.ChannelNameConstant;
import com.techverito.sattv.utils.Utils;

/**
 * @author Ravi Sapariya
 * This Implementation Data Provider class is use to provide Pre-Written data set.
 * You can create new DataProviderImpl if you wish to fetch/create from other external sources.
 */
public class PreWrittenDataProviderImpl implements DataProvider {

  private Map<String, Channel> channels;
  private List<ChannelGroup> packages;

  public PreWrittenDataProviderImpl() {
    this.channels = this.getChannelNameObjectMap();
    this.packages = this.prepareChannelGroup(this.channels);
  }

  @Override
  public Map<String, Channel> getChannels() {
    return channels;
  }
  
  @Override
  public List<ChannelGroup> getPackages() {
    return packages;
  }

  private List<ChannelGroup> prepareChannelGroup(Map<String, Channel> channels) {
    List<ChannelGroup> channelGroups = new ArrayList<>();

    ChannelGroup bronzePack = new ChannelGroup(Utils.generateRandomBasedUUID(), "Bronze Pack", false, "B", 60d);
    bronzePack.addChannel(channels.get(ChannelNameConstant.ZEE));
    bronzePack.addChannel(channels.get(ChannelNameConstant.SONY));
    bronzePack.addChannel(channels.get(ChannelNameConstant.STAR_PLUS));
    channelGroups.add(bronzePack);

    ChannelGroup silverPack = new ChannelGroup(Utils.generateRandomBasedUUID(), "Silver Pack", false, "S", 100d);
    silverPack.setChannels(bronzePack.getChannels());
    silverPack.addChannel(channels.get(ChannelNameConstant.DISCOVERY));
    silverPack.addChannel(channels.get(ChannelNameConstant.NAT_GEO));
    channelGroups.add(silverPack);

    ChannelGroup goldPack = new ChannelGroup(Utils.generateRandomBasedUUID(), "Gold Pack", false, "G", 150d);
    goldPack.setChannels(silverPack.getChannels());
    goldPack.addChannel(channels.get(ChannelNameConstant.MTV));
    goldPack.addChannel(channels.get(ChannelNameConstant.HBO));
    channelGroups.add(goldPack);

    ChannelGroup gujratiPack = new ChannelGroup(Utils.generateRandomBasedUUID(), "Gujarati Pack", true, "G", 20d);
    gujratiPack.addChannel(channels.get(ChannelNameConstant.ZEE_GUJRATI));
    gujratiPack.addChannel(channels.get(ChannelNameConstant.DD_GUJARATI));
    channelGroups.add(gujratiPack);

    ChannelGroup marathiPack = new ChannelGroup(Utils.generateRandomBasedUUID(), "Marathi Pack", true, "M", 10d);
    marathiPack.addChannel(channels.get(ChannelNameConstant.ZEE_MARATHI));
    marathiPack.addChannel(channels.get(ChannelNameConstant.COLOURS_MARATHI));
    channelGroups.add(marathiPack);

    return channelGroups;
  }

  private Map<String, Channel> getChannelNameObjectMap() {
    Map<String, Channel> channelNameObjectMap = new LinkedHashMap<>(); // To Maintain Insertation Order
    channelNameObjectMap.put(ChannelNameConstant.ZEE, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.ZEE, 30d));
    channelNameObjectMap.put(ChannelNameConstant.SONY, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.SONY, 20d));
    channelNameObjectMap.put(ChannelNameConstant.STAR_PLUS, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.STAR_PLUS, 30d));
    channelNameObjectMap.put(ChannelNameConstant.DISCOVERY, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.DISCOVERY, 30d));
    channelNameObjectMap.put(ChannelNameConstant.NAT_GEO, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.NAT_GEO, 20d));
    channelNameObjectMap.put(ChannelNameConstant.MTV, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.MTV, 20d));
    channelNameObjectMap.put(ChannelNameConstant.HBO, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.HBO, 20d));
    channelNameObjectMap.put(ChannelNameConstant.ZEE_GUJRATI, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.ZEE_GUJRATI, 20d));
    channelNameObjectMap.put(ChannelNameConstant.DD_GUJARATI, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.DD_GUJARATI, 10d));
    channelNameObjectMap.put(ChannelNameConstant.ZEE_MARATHI, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.ZEE_MARATHI, 20d));
    channelNameObjectMap.put(ChannelNameConstant.COLOURS_MARATHI, new Channel(Utils.generateRandomBasedUUID(), ChannelNameConstant.COLOURS_MARATHI, 10d));
    return channelNameObjectMap;
  }
}
