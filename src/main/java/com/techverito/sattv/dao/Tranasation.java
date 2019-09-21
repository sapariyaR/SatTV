package com.techverito.sattv.dao;

import java.util.List;
import java.util.stream.Collectors;

public class Tranasation {

  private Double pricePerMonth;
  private Integer numberOfMonth;
  private Double totalPrice;
  private Double gstRate = 18d;
  private Double billingAmount;
  private Integer discount;
  private Double finalPrice;
  
  private List<String> packNames;
  private List<String> subscribedChannels;

  public Double getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(Double pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }

  public Integer getNumberOfMonth() {
    return numberOfMonth;
  }

  public void setNumberOfMonth(Integer numberOfMonth) {
    this.numberOfMonth = numberOfMonth;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Double getGstRate() {
    return gstRate;
  }

  public void setGstRate(Double gstRate) {
    this.gstRate = gstRate;
  }

  public Double getBillingAmount() {
    return billingAmount;
  }

  public void setBillingAmount(Double billingAmount) {
    this.billingAmount = billingAmount;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }

  public Double getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(Double finalPrice) {
    this.finalPrice = finalPrice;
  }

  public List<String> getSubscribedChannels() {
    return subscribedChannels;
  }

  public void setSubscribedChannels(List<String> subscribedChannels) {
    this.subscribedChannels = subscribedChannels;
  }

  public List<String> getPackNames() {
    return packNames;
  }

  public void setPackNames(List<String> packNames) {
    this.packNames = packNames;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Monthly price:"+this.pricePerMonth+" Rs. \n");
    stringBuilder.append("No of months:"+this.numberOfMonth+"\n");
    stringBuilder.append("Total Price:"+this.totalPrice+" Rs. \n");
    stringBuilder.append("GST:"+this.gstRate+"% \n");
    stringBuilder.append("Amount after applying GST:"+this.billingAmount+" Rs. \n");
    stringBuilder.append("Discount:"+this.discount+"% \n");
    stringBuilder.append("Final Amount:"+this.finalPrice+" Rs. \n");
    stringBuilder.append("Your Final Pack Name : "+String.join(",", this.packNames.stream()
        .collect(Collectors.toList()))+"\n");
    stringBuilder.append("Your Final Channel List : "+String.join(",", this.subscribedChannels.stream()
        .collect(Collectors.toList())));
    return stringBuilder.toString();
  }
  
  
}
