package com.example.camper_van;

import Business.Calculate;
import Domain.CustomerList;
import Domain.Fleet;
import Foundation.DBConnection;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;


public class Testing {

    @BeforeClass
    public static void startTesting(){
        System.out.println("Start testing ...");
    }

    @AfterClass
    public static void endTesting(){
        System.out.print("... Done testing");
    }

    @Before
    public void beforeTest() throws Exception {
        System.out.println("... Next test");
    }

    @After
    public void afterTest() throws Exception{
        System.out.println("... No unexpected errors");
    }


    @Test
    public void Case1(){
        //Testing a EQ (Equivalense portition)
        System.out.println("Testing for price for startWeek 24, amountWeeks 3 (3 season), Luxury Camper (AA00001), Customer 10% discount (99999999)");
        CustomerList.getInstance();
        Fleet.getInstance();
        double expected = 3240;
        double actual = Calculate.camperPrice(Fleet.getInstance().getCamper("AA00001"), CustomerList.getInstance().getCustomer("99999999"), 3,24);
        assertEquals(expected,actual);
    }

    @Test
    public void Case2(){
        //Testing a EQ (Equivalense portition)
        System.out.println("Testing for price for startWeek 44, amountWeeks 2 (1 season, 1 non-season), Basic Camper (AA00005), Customer no discount (66666666)");
        CustomerList.getInstance();
        Fleet.getInstance();
        double expected = 1100;
        double actual = Calculate.camperPrice(Fleet.getInstance().getCamper("AA00005"),CustomerList.getInstance().getCustomer("66666666"),2,44);
        assertEquals(expected,actual);
    }

    @Test
    public void Case3(){
        // Make a connection (if not already made)
        DBConnection.getInstance();

        //Testing a EQ (Equivalense portition)
        System.out.println("This will return true");
        boolean actual = DBConnection.isConnected();
        assertEquals(true,actual);
    }

}
