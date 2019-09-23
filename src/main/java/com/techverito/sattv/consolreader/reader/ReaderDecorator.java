package com.techverito.sattv.consolreader.reader;

import java.util.Scanner;
import com.techverito.sattv.service.DthDataProviderService;

abstract class ReaderDecorator implements ConsolReader {

  private DthDataProviderService dataProviderService;
  private Scanner scanner;
  
  public ReaderDecorator(DthDataProviderService dataProviderService,Scanner scanner) {
    this.dataProviderService = dataProviderService;
    this.scanner = scanner;
  }

  public DthDataProviderService getDataProviderService() {
    return dataProviderService;
  }

  public Scanner getScanner() {
    return scanner;
  }

}
