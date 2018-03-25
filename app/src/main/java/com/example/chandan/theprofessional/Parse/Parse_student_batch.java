package com.example.chandan.theprofessional.Parse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SUBHADIP on 08-11-2017.
 */
public class Parse_student_batch {
    public String [] sId=null;
    public String [] sClass = null;
    public String [] sBatch=null;
    public final String ARRAY_NAME="result";
    public final String KEY_STU_BATCH="stdbatch";
    static JSONArray jsonArray=null;
    static JSONObject jsonObject=null;
    public  static String json;
    public Parse_student_batch(String json) {
        Parse_student_batch.json =json;
    }
    public void getDataFromJson() {
        try {
            jsonObject =new JSONObject(json);
            jsonArray=jsonObject.getJSONArray(ARRAY_NAME);
            sBatch=new String[jsonArray.length()+1];
            Log.i("Array: ", String.valueOf(sBatch.length));
            Log.i("json ", String.valueOf(jsonArray.length()));
            this.sBatch[0] = "Batch";
            for (int i=0; i<=jsonArray.length(); i++){
                JSONObject jo=jsonArray.getJSONObject(i);
                this.sBatch[i+1] = jo.getString(KEY_STU_BATCH);
                Log.i("Array: ", String.valueOf(sBatch[i]));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}