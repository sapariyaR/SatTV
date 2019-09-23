package com.techverito.sattv;

import java.util.Scanner;
import com.techverito.sattv.dao.DataProvider;
import com.techverito.sattv.dao.PreWrittenDataProviderImpl;
import com.techverito.sattv.service.ConsolMannagerService;
import com.techverito.sattv.service.DthDataProviderService;

public class SattvApplication {

    public static void main(String args[]) {
      DataProvider dataProvider = new PreWrittenDataProviderImpl();
      DthDataProviderService dataProviderService = new DthDataProviderService(dataProvider);
      new ConsolMannagerService(dataProviderService,new Scanner(System.in)).run();
    }
}
