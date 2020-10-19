package com.hburada.pageClasses;

import com.hburada.base.TestBase;
import com.hburada.dataReader.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class ProductDetailPageClass extends TestBase {

    WebDriver driver;

    public ProductDetailPageClass(WebDriver driver) {
        this.driver = driver;
    }

    public void productDetailPageValidation() throws InterruptedException {
        String pageName_Xpath = "//script[@type='text/javascript'][contains(text(),'\"page_name\":\"Product Detail\"')]";

        Assert.assertTrue(isElementPresent(By.xpath(pageName_Xpath)), "Product Detail page opened succesfuly.");
        log.info("Product Detail page opened succesfuly.");
        Thread.sleep(500);
    }

    public void goToReviews() throws InterruptedException {
        String reviewTab_Xpath = "//td[@id='reviewsTabTrigger']";

        scrollAndClick(reviewTab_Xpath);
        log.info("Clicked reviews tab.");
        Thread.sleep(2000);
    }

    public boolean isReviewsExist() throws InterruptedException {
        String reviewsContainer_Xpath = "//div[@class='FiltersContainer-module-1mvyi']";
        //boolean reviewExistFlag;

        Assert.assertTrue(isElementPresent(By.xpath(reviewsContainer_Xpath)), "Review exists at least one.");
        //reviewExistFlag = true;
        log.info("Review exists at least one.");
        Thread.sleep(500);

        return true;
    }

    public void clickFirstReviewYesButton(Boolean reviewExistFlag) throws InterruptedException {
        String firstReviewYesButton_Xpath = "(//strong[text()=' Evet ']/ancestor::button[@type='button'])[1]";

        if(reviewExistFlag) {
            click(firstReviewYesButton_Xpath);
            log.info("First review helpful-yes button clicked.");
            Thread.sleep(500);
        } else {
            log.info("There is no review to show.");
            Thread.sleep(500);
        }
    }

    public void feedbackNoticeValidation() throws InterruptedException, IOException {
        String feedbackNotice_Xpath = "//div[@class='ReviewCard-module-uH6Em']//span[contains(text(),'Te') and contains(text(),'ekk') and contains(text(),'Ederiz.')]";

        Assert.assertTrue(isElementPresent(By.xpath(feedbackNotice_Xpath)), "Feedback notice: 'Tesekkur Ederiz.' has been successfuly shown.");
        TestUtil.captureScreenshot();
        Thread.sleep(500);
        log.info("Feedback notice: 'Tesekkur Ederiz.' has been successfuly shown.");
        Thread.sleep(500);
    }

}
