package com.hburada.pageClasses;

import com.hburada.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Random;

public class SearchResultPageClass extends TestBase {

    WebDriver driver;
    public SearchResultPageClass(WebDriver driver) {
        this.driver = driver;
    }

    public void searchResultPageValidation() throws InterruptedException {
        String pageName_Xpath = "//script[@type='text/javascript'][contains(text(),'\"page_type\":\"searchresults\"')]";

        Assert.assertTrue(isElementPresent(By.xpath(pageName_Xpath)), "Search Result page opened succesfuly.");
        log.info("Search Result page opened succesfuly.");
        Thread.sleep(500);
    }

    public void selectRandomProduct() throws InterruptedException {
        String productCountOnPage_Xpath = "//li[@class='search-item col lg-1 md-1 sm-1  custom-hover not-fashion-flex']";
        String isProductExist_Xpath = "//li[@class='search-item col lg-1 md-1 sm-1  custom-hover not-fashion-flex'][1]";
        String newProductCountOnPage_Xpath = "";
        String productCountOnPageNumbered = "";
        String randomProduct_Xpath = "";

        int count = 1;
        int min = 1;
        //Calculate number of items, listed on page.
        do {
            productCountOnPageNumbered = productCountOnPage_Xpath + "[" + count + "]";
            if (isElementPresent(By.xpath(productCountOnPageNumbered))) {
                count++;
            } else if (!isElementPresent(By.xpath(isProductExist_Xpath))){
                Assert.assertTrue(false, "There is no more product to list");
                //log.debug(count + " numbered product has been not presented!");
            }
            newProductCountOnPage_Xpath = productCountOnPage_Xpath + "[" + count + "]";
        }
        while (isElementPresent(By.xpath(newProductCountOnPage_Xpath)));

        Random r = new Random();
        int randomNumber = r.nextInt((count - min) - 1) + min;

        randomProduct_Xpath = productCountOnPage_Xpath + "[" + (randomNumber) + "]";
        click(randomProduct_Xpath);
        log.info("Clicked "+ randomNumber +". random product.");
        Thread.sleep(2500);
    }

}

