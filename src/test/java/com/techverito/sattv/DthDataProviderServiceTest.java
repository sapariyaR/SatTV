package com.techverito.sattv;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import com.techverito.sattv.dao.DthDataProviderService;
import com.techverito.sattv.dao.Tranasation;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;

public class DthDataProviderServiceTest {

  
  @Test
  public void getChannelsDataValidation() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<Channel> channels = dataProviderService.retriveAvailableChannels();
    Assert.assertNotNull(channels);
    Assert.assertEquals(channels.size(),11);
  }
  
  @Test
  public void getChannelsTotalPriceDataValidation() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<Channel> channels = dataProviderService.retriveAvailableChannels();
    Assert.assertNotNull(channels);
    double sum = channels.stream().mapToDouble(eachItem -> eachItem.getPrice()).sum();
    Assert.assertNotNull(sum);
    Assert.assertEquals("Total Price doen't match with Channel Price.",sum, 230,0);
  }
  
  @Test
  public void getPackDataValidation() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> retriveAvailablePackages = dataProviderService.retriveAvailablePackages();
    Assert.assertNotNull(retriveAvailablePackages);
    boolean isRegionalPackVailable = retriveAvailablePackages.stream().anyMatch(eachItem -> eachItem.getIsComplimentary());
    Assert.assertEquals(isRegionalPackVailable,true);
    
    boolean isBasePackVailable = retriveAvailablePackages.stream().anyMatch(eachItem -> !eachItem.getIsComplimentary());
    Assert.assertEquals(isBasePackVailable,true);
    
    Optional<ChannelGroup> findAnyRegi = retriveAvailablePackages.stream().filter(eachItem -> eachItem.getIsComplimentary()).findAny();
    Assert.assertNotNull("No Any Regional Pack Avilable.",findAnyRegi.get());
    Assert.assertNotNull("No Channel Availble on Regional Pack",findAnyRegi.get().getChannels());
  }
  
  @Test
  public void getBasePackDataValidation() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> retrivePackagesBasedOnComplimentary = dataProviderService.retrivePackagesBasedOnComplimentary(false);
    Assert.assertNotNull("Base Pack Should not be null.",retrivePackagesBasedOnComplimentary);
  }
  
  @Test
  public void getRegionalPackDataValidation() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> retrivePackagesBasedOnComplimentary = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Assert.assertNotNull("Regional Pack should not be null.",retrivePackagesBasedOnComplimentary);
  }
  
  @Test
  public void calculateBasePackWithRegionalPackForOneMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    //Data Preparation
    List<ChannelGroup> basePacks = dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
    Optional<ChannelGroup> basePackOptional = basePacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("s")).findFirst();
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Back Pack not available.",basePackOptional.isPresent(), true);
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(1, Arrays.asList(basePackOptional.get(),regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 100d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 118d,0);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 0,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 118,0);
  }
  
  @Test
  public void calculateBasePackWithRegionalPackForThreeMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    //Data Preparation
    List<ChannelGroup> basePacks = dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
    Optional<ChannelGroup> basePackOptional = basePacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("s")).findFirst();
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Back Pack not available.",basePackOptional.isPresent(), true);
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(3, Arrays.asList(basePackOptional.get(),regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 100d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 354d,0);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 5,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 336,1);
  }
  
  @Test
  public void calculateBasePackWithRegionalPackForNineMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    //Data Preparation
    List<ChannelGroup> basePacks = dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
    Optional<ChannelGroup> basePackOptional = basePacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("s")).findFirst();
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Back Pack not available.",basePackOptional.isPresent(), true);
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(9, Arrays.asList(basePackOptional.get(),regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 100d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 1062d,0);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 955,1);
  }
  
  @Test
  public void calculateRegionalPackForOneMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    //Data Preparation
    List<ChannelGroup> regioPacks = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(1, Arrays.asList(regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 20d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 23d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 0,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 23d,1);
  }
  
  @Test
  public void calculateRegionalPackForTenMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    //Data Preparation
    List<ChannelGroup> regioPacks = dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(10, Arrays.asList(regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 20d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 236d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 212d,1);
  }
  
  @Test
  public void calculateCustomPackNegative() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> channels = dataProviderService.createCustomChannelGroup(Arrays.asList("test"));
    Assert.assertNull(channels);
  }
  
  @Test
  public void calculateCustomPackPositiveOneMonth() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> channels = dataProviderService.createCustomChannelGroup(Arrays.asList("Zee","Star Plus","Net Geo"));
    Assert.assertNotNull(channels);
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(1, channels);
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 80d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 94d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 0,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 94d,1);
  }
  
  @Test
  public void calculateCustomPackPositive() {
    DthDataProviderService dataProviderService = new DthDataProviderService();
    List<ChannelGroup> channels = dataProviderService.createCustomChannelGroup(Arrays.asList("Zee","Star Plus","Net Geo"));
    Assert.assertNotNull(channels);
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(12, channels);
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 80d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 1132d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 1019d,1);
  }

}
