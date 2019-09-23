package com.techverito.sattv.consolreader;

import java.util.ArrayList;
import java.util.List;
import com.techverito.sattv.entity.ChannelGroup;

public class UserSelection {

  private List<ChannelGroup> selectedChannelGroup;
  private Integer numberOfMonth;
  public List<ChannelGroup> getSelectedChannelGroup() {
    return selectedChannelGroup;
  }
  public void setSelectedChannelGroup(List<ChannelGroup> selectedChannelGroup) {
    this.selectedChannelGroup = selectedChannelGroup;
  }
  public Integer getNumberOfMonth() {
    return numberOfMonth;
  }
  public void setNumberOfMonth(Integer numberOfMonth) {
    this.numberOfMonth = numberOfMonth;
  }
  public void setSelectedChannelGroup(ChannelGroup selectedChannelGroup) {
    if(this.selectedChannelGroup == null) {
      this.selectedChannelGroup = new ArrayList<>();
    }
    this.selectedChannelGroup.add(selectedChannelGroup);
  }
}
