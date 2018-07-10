package com.example.azzumwaqar.hoxtonsalahproject;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils{

   private static Context context;

    //    private static final String SAMPLE_JSON_RESPONSE = "{\"city\":\"london\",\"date\":\"2018-06-30\",\"fajr\":\"02:47\",\"fajr_jamat\":\"03:17\",\"sunrise\":\"04:44\",\"dhuhr\":\"01:09\",\"dhuhr_jamat\":\"01:30\",\"asr\":\"05:26\",\"asr_2\":\"06:41\",\"asr_jamat\":\"07:00\",\"magrib\":\"09:24\",\"magrib_jamat\":\"09:29\",\"isha\":\"10:37\",\"isha_jamat\":\"11:00\"}";
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(Context context){
        this.context = context;
    }

    public static List<Salaat> fetchSalaatData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG,"Error closing input stream",e);
        }

        List<Salaat> salaatTimes = extractFeatureFromJson(jsonResponse);

        return salaatTimes;
    }

    private static ArrayList<Salaat> extractFeatureFromJson(String salaatJSON) {
        ArrayList<Salaat> salaatTimesList = new ArrayList<>();

        if(TextUtils.isEmpty(salaatJSON)) return null;

        try {
            JSONObject root = new JSONObject(salaatJSON);

            String fajarStartTime = root.optString("fajr");
            String fajarJamaatTime = root.getString("fajr_jamat");

            String zoharStartTime = root.optString("dhuhr");
            String zoharJamaatTime = root.optString("dhuhr_jamat");

            String asrStartTime = root.optString("asr");
            String asrJamaatTime = root.optString("asr_jamat");

            String asrSecond_StartTime = root.optString("asr_2");
            String asrSecond_JamaatTime = root.optString("asr_jamat");

            String maghribStartTime = root.optString("magrib");
            String maghribJamaatTime = root.optString("magrib_jamat");

            String ishaStartTime = root.optString("isha");
            String ishaJamaatTime = root.optString("isha_jamat");

            salaatTimesList.add(new Salaat(context.getString(R.string.fajar),fajarStartTime,fajarJamaatTime));
            salaatTimesList.add(new Salaat(context.getString(R.string.zohar),zoharStartTime,zoharJamaatTime));
            salaatTimesList.add(new Salaat(context.getString(R.string.asr),asrStartTime,asrJamaatTime));
            salaatTimesList.add(new Salaat(context.getString(R.string.maghrib),maghribStartTime,maghribJamaatTime));
            salaatTimesList.add(new Salaat(context.getString(R.string.isha),ishaStartTime,ishaJamaatTime));

        }catch (JSONException je){
            Log.e(LOG_TAG,"Problem parsing salaat JSON reuslts", je);
        }

        return salaatTimesList;
    }

    private static URL createURL(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,"Error with creating url", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"ERROR RESPONSE CODE: "+ urlConnection.getResponseCode());
            }

        }catch(IOException io){
            Log.e(LOG_TAG,"Problem retreiving Salaat Timings JSON results",io);
        }
        finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = reader.readLine();
            }

        }

        return stringBuilder.toString();
    }
}
