package com.example.mystocks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;



public class Api {


    private static final String LOG_TAG = "api1";
    private static final String LOG_TAG2 = "api2";

    String result = "JSON";
    JSONObject jsonApi;
    JSONArray jsonArray;
    JSONObject jsonCompany;
    String companyField;

    public void initApi(){
        Parser t = new Parser();
        t.execute();
        Log.d(LOG_TAG, String.valueOf(t.getStatus()));
    }


    public JSONObject getJSON() throws JSONException {
        initApi();
        while(true) {
            if(!result.equals("JSON")) {
                jsonApi = new JSONObject(result);
                return jsonApi;
            }
        }

    }

    public JSONArray getCompanyArray() throws JSONException {
        jsonArray = jsonApi.getJSONArray("data");
        return jsonArray;
    }

    public JSONObject getCompany(int i) throws JSONException {
        jsonCompany = jsonArray.getJSONObject(i);
        return jsonCompany;
    }

    public String getCompanyField(int i, String field) throws JSONException {
        companyField = jsonCompany.getString(field);
        return companyField;
    }

    public void printJSON(String LOG_TAG) {
        initApi();
        //Log.d(LOG_TAG, result);
        while(true) {
            //Log.d(LOG_TAG, result);
            if(!result.equals("JSON")) {
                Log.d(LOG_TAG, result);
                break;
            }
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        Log.d(LOG_TAG, String.valueOf(networkInfo));
        return networkInfo != null && networkInfo.isConnected();
    }


    public class Parser extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            //Log.d(LOG_TAG, result);
            result = aVoid;
            //Log.d(LOG_TAG2, result);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Document doc = null;
            //Log.d(LOG_TAG, isConnectedToInternet());
            try {
                doc = Jsoup.connect("https://mystocks-test-f.herokuapp.com/api").get();
                Elements str = doc.select("body");
                //Log.d(LOG_TAG2, doc.text());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.d(LOG_TAG, doc.text());
            if (doc != null) {
                result = doc.text();
            }
            //Log.d(LOG_TAG2, result);
            return result;
        }
    }
}
