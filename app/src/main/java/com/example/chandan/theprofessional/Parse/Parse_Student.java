package com.example.chandan.theprofessional.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SUBHADIP on 25-09-2017.
 */
public class Parse_Student {
    public String[] sName = null;
    public String[] sId = null;
    public String[] sClass = null;
    public String[] sBatch = null;
    public String[] sImage = null;
    public String[] sNumber = null;
    public String[] sEmail = null;
    public String[] sAdd = null;
    public String[] sFees = null;

    public final String ARRAY_NAME = "result";
    public final String KEY_STU_NAME = "stdname";
    public final String KEY_STU_CLASS = "stdclass";
    public final String KEY_STU_BATCH = "stdbatch";
    public final String KEY_STU_IMAGE = "stdimage";
    public final String KEY_STU_NUMBER = "stdmob";
    public final String KEY_STU_ID = "id";
    public final String KEY_STU_EMAIL = "stu_email";
    public final String KEY_STU_ADDR = "stu_addr";
    public final String KEY_STU_FEES = "stu_addr";

    static JSONArray jsonArray = null;
    static JSONObject jsonObject = null;
    public static String json;

    public Parse_Student(String json) {
        Parse_Student.json = json;
    }

    public void getDataFromJson() {
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(ARRAY_NAME);
            sName = new String[jsonArray.length()];
            sId = new String[jsonArray.length()];
            sClass = new String[jsonArray.length()];
            sBatch = new String[jsonArray.length()];
            sImage = new String[jsonArray.length()];
            sNumber = new String[jsonArray.length()];
            sEmail = new String[jsonArray.length()];
            sAdd = new String[jsonArray.length()];
            sFees = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                sName[i] = jo.getString(KEY_STU_NAME);
                sClass[i] = jo.getString(KEY_STU_CLASS);
                sBatch[i] = jo.getString(KEY_STU_BATCH);
                sImage[i] = jo.getString(KEY_STU_IMAGE);
                sNumber[i] = jo.getString(KEY_STU_NUMBER);
                sId[i] = jo.getString(KEY_STU_ID);
                sEmail[i] = jo.getString(KEY_STU_EMAIL);
                sAdd[i] = jo.getString(KEY_STU_ADDR);
                sFees[i] = jo.getString(KEY_STU_FEES);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}