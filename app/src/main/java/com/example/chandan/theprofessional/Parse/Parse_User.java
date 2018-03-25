package com.example.chandan.theprofessional.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_User {
    public String [] uId=null;
    public String [] uName=null;
    public String [] uEmail=null;
    public String [] uPass=null;
    public String [] uAltmail=null;
    public String [] uAdd=null;
    public String [] uPhno=null;
    public String [] uImage=null;
    public final String ARRAY_NAME="result";
    public final String KEY_USER_ID="user_id";
    public final String KEY_USER_NAME="user_name";
    public final String KEY_USER_EMAIL="user_email";
    public final String KEY_USER_PASS="user_pass";
    public final String KEY_USER_ALTEMAIL="user_altmail";
    public final String KEY_USER_ADD="user_addr";
    public final String KEY_USER_PHNO="user_phno";
    public final String KEY_USER_IMAGE="user_img";
    static JSONArray jsonArray=null;
    static JSONObject jsonObject=null;
    public  static String json;
    public Parse_User(String json) {
        Parse_User.json =json;
    }
    public void getDataFromJson()
    {
        try {
            jsonObject =new JSONObject(json);
            jsonArray=jsonObject.getJSONArray(ARRAY_NAME);
            uId=new String[jsonArray.length()];
            uName=new String[jsonArray.length()];
            uEmail=new String[jsonArray.length()];
            uPass=new String[jsonArray.length()];
            uAltmail=new String[jsonArray.length()];
            uAdd=new String[jsonArray.length()];
            uPhno=new String[jsonArray.length()];
            uImage=new String[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jo=jsonArray.getJSONObject(i);
                uId[i]=jo.getString(KEY_USER_ID);
                uName[i]=jo.getString(KEY_USER_NAME);
                uEmail[i]=jo.getString(KEY_USER_EMAIL);
                uPass[i]=jo.getString(KEY_USER_PASS);
                uAltmail[i]=jo.getString(KEY_USER_ALTEMAIL);
                uAdd[i]=jo.getString(KEY_USER_ADD);
                uPhno[i]=jo.getString(KEY_USER_PHNO);
                uImage[i]=jo.getString(KEY_USER_IMAGE);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
