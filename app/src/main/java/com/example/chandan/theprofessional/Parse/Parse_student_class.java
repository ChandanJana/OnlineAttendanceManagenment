package com.example.chandan.theprofessional.Parse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SUBHADIP on 08-11-2017.
 */
public class Parse_student_class {
    public String [] sId=null;
    public String [] sClass = null;
    public String [] sBatch=null;
    public final String ARRAY_NAME="result";
    public final String KEY_STU_CLASS="stdclass";
    static JSONArray jsonArray=null;
    static JSONObject jsonObject=null;
    public  static String json;
    public Parse_student_class(String json) {
        Parse_student_class.json =json;
    }
    public void getDataFromJson() {
        try {
            jsonObject =new JSONObject(json);
            jsonArray=jsonObject.getJSONArray(ARRAY_NAME);
            sClass=new String[jsonArray.length()+1];
           // Log.i("Array: ", String.valueOf(sClass.length));
           // Log.i("json ", String.valueOf(jsonArray.length()));
            this.sClass[0] = "Class";
            for (int i=0; i<=jsonArray.length(); i++){
                    JSONObject jo=jsonArray.getJSONObject(i);
                    this.sClass[i+1] = jo.getString(KEY_STU_CLASS);
                Log.i("Array: ", String.valueOf(sClass[i]));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}