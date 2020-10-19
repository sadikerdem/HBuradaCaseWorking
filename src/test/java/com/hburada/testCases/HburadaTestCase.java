package com.hburada.testCases;

import com.hburada.base.TestBase;
import com.hburada.dataReader.TestUtil;
import com.hburada.pageClasses.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HburadaTestCase extends TestBase {

    Hashtable<String, String> data;

    JSONObject obj;
    List<String> list = new ArrayList<String>();
    public JSONArray array;
    public String searchWord;

    DashboardPageClass dashboardPageClass;
    SearchResultPageClass searchResultPageClass;
    ProductDetailPageClass productDetailPageClass;

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void hburadaTestCase(Hashtable<String, String> data) throws InterruptedException, IOException {
        log.debug("Inside hepsiburada Test");

        this.data = data;
        getJsonDataFromExcel();

        dashboardPageClass = new DashboardPageClass(driver);
        dashboardPageClass.dashboardPageValidation();
        dashboardPageClass.enterSearch(searchWord);
        dashboardPageClass.clickSearchButton();

        searchResultPageClass = new SearchResultPageClass(driver);
        searchResultPageClass.searchResultPageValidation();
        searchResultPageClass.selectRandomProduct();

        productDetailPageClass = new ProductDetailPageClass(driver);
        productDetailPageClass.productDetailPageValidation();
        productDetailPageClass.goToReviews();
        Boolean isReviewExist = productDetailPageClass.isReviewsExist();
        productDetailPageClass.clickFirstReviewYesButton(isReviewExist);
        productDetailPageClass.feedbackNoticeValidation();

        log.debug("Hepsiburada test successfully completed.");
    }

    private void getJsonDataFromExcel() {
        obj = new JSONObject(data.get("ProductInfo"));
        array = obj.getJSONArray("columns");
        for (int i = 0; i < array.length(); i++) {
            this.list.add(array.getJSONObject(i).getString("ProductID"));
        }

        for (int i = 0; i < array.length(); i++) {

            String productID = array.getJSONObject(i).getString("ProductID");

            searchWord = array.getJSONObject(i).getString("searchWord");
        }
    }

}

