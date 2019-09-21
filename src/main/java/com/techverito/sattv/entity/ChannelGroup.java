package com.techverito.sattv.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChannelGroup {

  private String id;
  private String groupName;
  private String alias;
  private Set<Channel> channels;
  private Boolean isComplimentary = false;
  private Double price;
  
  public ChannelGroup(String id, String groupName, Boolean isComplimentary, String alias,Double price) {
    super();
    this.id = id;
    this.groupName = groupName;
    this.isComplimentary = isComplimentary;
    this.alias = alias;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Set<Channel> getChannels() {
    return channels;
  }

  public void setChannels(Set<Channel> channels) {
    this.channels = channels;
  }
  
  public void addChannel(Channel channel) {
    if(this.channels == null) {
      this.channels = new HashSet<>();
    }
    this.channels.add(channel);
  }

  public Boolean getIsComplimentary() {
    return isComplimentary;
  }

  public void setIsComplimentary(Boolean isComplimentary) {
    this.isComplimentary = isComplimentary;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.groupName);
    stringBuilder.append(" : ");
    String commaSeperatedChannels = String.join(",", this.channels.stream()
        .map(eachChannel -> eachChannel.getName())
        .collect(Collectors.toList()));
    stringBuilder.append(commaSeperatedChannels);
    /*double sum = this.channels.stream().mapToDouble(eachChannel -> eachChannel.getPrice()).sum();*/
    stringBuilder.append(" : ");
    stringBuilder.append(this.price + " RS");
    return stringBuilder.toString();
  }
  
}
