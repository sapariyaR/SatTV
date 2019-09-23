package com.techverito.sattv.dao;

import java.util.List;
import java.util.Map;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;

public interface DataProvider {

  public Map<String, Channel> getChannels();
  public List<ChannelGroup> getPackages();
  
}
