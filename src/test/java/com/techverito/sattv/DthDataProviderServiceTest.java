package com.techverito.sattv;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.techverito.sattv.dao.DataProvider;
import com.techverito.sattv.dao.PreWrittenDataProviderImpl;
import com.techverito.sattv.dao.Tranasation;
import com.techverito.sattv.entity.Channel;
import com.techverito.sattv.entity.ChannelGroup;
import com.techverito.sattv.service.DthDataProviderService;

public class DthDataProviderServiceTest {

  public DthDataProviderService dataProviderService;
  
  @Before
  public void init() {
    DataProvider dataProvider = new PreWrittenDataProviderImpl();
    this.dataProviderService = new DthDataProviderService(dataProvider);
  }
  
  @Test
  public void getChannelsDataValidation() {
    List<Channel> channels = this.dataProviderService.retriveAvailableChannels();
    Assert.assertNotNull(channels);
    Assert.assertEquals(channels.size(),11);
  }
  
  @Test
  public void getChannelsTotalPriceDataValidation() {
    List<Channel> channels = this.dataProviderService.retriveAvailableChannels();
    Assert.assertNotNull(channels);
    double sum = channels.stream().mapToDouble(eachItem -> eachItem.getPrice()).sum();
    Assert.assertNotNull(sum);
    Assert.assertEquals("Total Price doen't match with Channel Price.",sum, 230,0);
  }
  
  @Test
  public void getPackDataValidation() {
    List<ChannelGroup> retriveAvailablePackages = this.dataProviderService.retriveAvailablePackages();
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
    List<ChannelGroup> retrivePackagesBasedOnComplimentary = this.dataProviderService.retrivePackagesBasedOnComplimentary(false);
    Assert.assertNotNull("Base Pack Should not be null.",retrivePackagesBasedOnComplimentary);
  }
  
  @Test
  public void getRegionalPackDataValidation() {
    List<ChannelGroup> retrivePackagesBasedOnComplimentary = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Assert.assertNotNull("Regional Pack should not be null.",retrivePackagesBasedOnComplimentary);
  }
  
  @Test
  public void calculateBasePackWithRegionalPackForOneMonth() {
    //Data Preparation
    List<ChannelGroup> basePacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
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
    //Data Preparation
    List<ChannelGroup> basePacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
    Optional<ChannelGroup> basePackOptional = basePacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("s")).findFirst();
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Back Pack not available.",basePackOptional.isPresent(), true);
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = this.dataProviderService.calculateBillDetails(3, Arrays.asList(basePackOptional.get(),regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 100d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 354d,0);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 5,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 336,1);
  }
  
  @Test
  public void calculateBasePackWithRegionalPackForNineMonth() {
    //Data Preparation
    List<ChannelGroup> basePacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(false);
    List<ChannelGroup> regioPacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    
    Optional<ChannelGroup> basePackOptional = basePacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("s")).findFirst();
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Back Pack not available.",basePackOptional.isPresent(), true);
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = this.dataProviderService.calculateBillDetails(9, Arrays.asList(basePackOptional.get(),regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 100d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 1062d,0);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 955,1);
  }
  
  @Test
  public void calculateRegionalPackForOneMonth() {
    //Data Preparation
    List<ChannelGroup> regioPacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = this.dataProviderService.calculateBillDetails(1, Arrays.asList(regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 20d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 23d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 0,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 23d,1);
  }
  
  @Test
  public void calculateRegionalPackForTenMonth() {
    //Data Preparation
    List<ChannelGroup> regioPacks = this.dataProviderService.retrivePackagesBasedOnComplimentary(true);
    Optional<ChannelGroup> regioPackOptional = regioPacks.stream().filter(eachPack -> eachPack.getAlias().equalsIgnoreCase("g")).findFirst();
    Assert.assertEquals("Regional Pack not available.",regioPackOptional.isPresent(), true);
    
    Tranasation calculateBillDetails = this.dataProviderService.calculateBillDetails(10, Arrays.asList(regioPackOptional.get()));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 20d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 236d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 212d,1);
  }
  
  @Test
  public void calculateCustomPackNegative() {
    ChannelGroup channel = this.dataProviderService.createCustomChannelGroup(Arrays.asList("test"));
    Assert.assertNull(channel);
  }
  
  @Test
  public void calculateCustomPackPositiveOneMonth() {
    ChannelGroup channel = this.dataProviderService.createCustomChannelGroup(Arrays.asList("Zee","Star Plus","Net Geo"));
    Assert.assertNotNull(channel);
    Tranasation calculateBillDetails = dataProviderService.calculateBillDetails(1, Arrays.asList(channel));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 80d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 94d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 0,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 94d,1);
  }
  
  @Test
  public void calculateCustomPackPositive() {
    ChannelGroup channel = this.dataProviderService.createCustomChannelGroup(Arrays.asList("Zee","Star Plus","Net Geo"));
    Assert.assertNotNull(channel);
    Tranasation calculateBillDetails = this.dataProviderService.calculateBillDetails(12, Arrays.asList(channel));
    Assert.assertNotNull("Fail to calculate Bill Details",calculateBillDetails);
    Assert.assertEquals("Condition fail to PPM",calculateBillDetails.getPricePerMonth(), 80d,0);
    Assert.assertEquals("Condition fail to AAGST",calculateBillDetails.getBillingAmount(), 1132d,1);
    Assert.assertEquals("Condition fail for Discount",calculateBillDetails.getDiscount(), 10,0);
    Assert.assertEquals("Condition fail for Final Price",calculateBillDetails.getFinalPrice(), 1019d,1);
  }
  
}
