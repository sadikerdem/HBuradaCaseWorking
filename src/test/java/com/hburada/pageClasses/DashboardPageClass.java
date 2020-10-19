package com.hburada.pageClasses;

import com.hburada.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DashboardPageClass extends TestBase {

    WebDriver driver;

    public DashboardPageClass(WebDriver driver) {
        this.driver = driver;
    }

    public void dashboardPageValidation() throws InterruptedException {
        String pageName_Xpath = "//script[@type='text/javascript'][contains(text(),'\"page_type\":\"home\"')]";

        Assert.assertTrue(isElementPresent(By.xpath(pageName_Xpath)), "Home page opened succesfuly.");
        log.info("Home page opened succesfuly.");
        Thread.sleep(500);
    }

    public void enterSearch(String searchWord) throws InterruptedException {
        String enterSearch_Xpath = "//input[@type='text']";

        type(enterSearch_Xpath, searchWord);
        log.info("Search word entered.");
        Thread.sleep(1000);
    }

    public void clickSearchButton() throws InterruptedException {
        String enterSearchButton_Xpath = "//div[@class='SearchBoxOld-buttonContainer']";

        click(enterSearchButton_Xpath);
        log.info("Clicked search button.");
        Thread.sleep(2500);
    }

}
