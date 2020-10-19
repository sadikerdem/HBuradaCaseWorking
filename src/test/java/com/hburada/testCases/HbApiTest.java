package com.hburada.testCases;

import com.hburada.base.TestBase;
import com.hburada.dataReader.TestUtil;
import com.hburada.pageClasses.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.*;

public class HbApiTest extends TestBase {

    Hashtable<String, String> data;
    JSONObject obj;
    List<String> list = new ArrayList<String>();
    public JSONArray array;

    public String language;
    public int id;

    ApiClass apiClass;

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void hbApiTest(Hashtable<String, String> data) throws InterruptedException {
        log.debug("Inside hepsiburada api Test");

        this.data = data;
        getJsonDataFromExcel();

        apiClass = new ApiClass(driver);
        apiClass.getMethodLangStatus(language);
        apiClass.postMethodLangStatus(id);

        log.debug("Hepsiburada api test successfully completed.");
    }

    private void getJsonDataFromExcel() {
        obj = new JSONObject(data.get("ProductInfo"));
        array = obj.getJSONArray("columns");
        for (int i = 0; i < array.length(); i++) {
            this.list.add(array.getJSONObject(i).getString("JsonArrayID"));
        }

        for (int i = 0; i < array.length(); i++) {

            String productID = array.getJSONObject(i).getString("JsonArrayID");

            language = array.getJSONObject(i).getString("language");
            id = array.getJSONObject(i).getInt("id");
        }
    }

}
