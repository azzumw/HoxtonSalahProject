package com.example.azzumwaqar.hoxtonsalahproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SalaatListAdapter salaatListAdapter;
    private final String SALAT_URL= "https://www.londonprayertimes.com/api/times/?format=json&key=a79ccf08-83b7-4e14-9d6e-8b9f5a4ff69d";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new QueryUtils(getApplicationContext());

        ListView listView = findViewById(R.id.list);

        salaatListAdapter = new SalaatListAdapter(this,new ArrayList<Salaat>());

        listView.setAdapter(salaatListAdapter);

        SalaatAsyncTask task = new SalaatAsyncTask();
        task.execute(SALAT_URL);
    }

    private class SalaatAsyncTask extends AsyncTask<String,Void,List<Salaat>>{

        @Override
        protected List<Salaat> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]==null) return null;

            List<Salaat> list = QueryUtils.fetchSalaatData(urls[0]);
            return  list;
        }

        @Override
        protected void onPostExecute(List<Salaat> salaats) {
            salaatListAdapter.clear();
            if (salaats!=null && !salaats.isEmpty()){
                salaatListAdapter.addAll(salaats);
            }
        }
    }
}
