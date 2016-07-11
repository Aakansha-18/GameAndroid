package com.example.harimohan.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StartPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView substring1;
    TextView substring2;
    TextView substring3;
    TextView substring4;
    TextView substring5;
    TextView substring6;
    TextView substring7;
    TextView substring8;
    TextView substring9;
    TextView substring10;
    TextView substring11;


    String[] subs = new String[20] ;

    String[] words = "batsmen domestically scored ranked appearances previously outstanding announced recipient aviation".split(" ");
    String[] ans = new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Spinner blank1 = (Spinner) findViewById(R.id.Blank1);
        Spinner blank2 = (Spinner) findViewById(R.id.Blank2);
        Spinner blank3 = (Spinner) findViewById(R.id.Blank3);
        Spinner blank4 = (Spinner) findViewById(R.id.Blank4);
        Spinner blank5 = (Spinner) findViewById(R.id.Blank5);
        Spinner blank6 = (Spinner) findViewById(R.id.Blank6);
        Spinner blank7 = (Spinner) findViewById(R.id.Blank7);
        Spinner blank8 = (Spinner) findViewById(R.id.Blank8);
        Spinner blank9 = (Spinner) findViewById(R.id.Blank9);
        Spinner blank10 = (Spinner) findViewById(R.id.Blank10);

        blank1.setOnItemSelectedListener( this);
        blank2.setOnItemSelectedListener( this);
        blank3.setOnItemSelectedListener( this);
        blank4.setOnItemSelectedListener( this);
        blank5.setOnItemSelectedListener( this);
        blank6.setOnItemSelectedListener( this);
        blank7.setOnItemSelectedListener( this);
        blank8.setOnItemSelectedListener( this);
        blank9.setOnItemSelectedListener( this);
        blank10.setOnItemSelectedListener( this);

        substring1 = (TextView) findViewById(R.id.subs1);
        substring2 = (TextView) findViewById(R.id.subs2);
        substring3 = (TextView) findViewById(R.id.subs3);
        substring4 = (TextView) findViewById(R.id.subs4);
        substring5 = (TextView) findViewById(R.id.subs5);
        substring6 = (TextView) findViewById(R.id.subs6);
        substring7 = (TextView) findViewById(R.id.subs7);
        substring8 = (TextView) findViewById(R.id.subs8);
        substring9 = (TextView) findViewById(R.id.subs9);
        substring10= (TextView) findViewById(R.id.subs10);
        substring11= (TextView) findViewById(R.id.subs11);


        new jsontask(). execute("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext=&titles=Sachin_Tendulkar&formatversion=2&format=json");

        List<String> categories = new ArrayList<String>();
        categories.add("Select Answer");
        categories.add(words[8]);
        categories.add(words[1]);
        categories.add(words[4]);
        categories.add(words[3]);
        categories.add(words[7]);
        categories.add(words[5]);
        categories.add(words[6]);
        categories.add(words[0]);
        categories.add(words[2]);
        categories.add(words[9]);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blank1.setAdapter(dataAdapter);
        blank2.setAdapter(dataAdapter);
        blank3.setAdapter(dataAdapter);
        blank4.setAdapter(dataAdapter);
        blank5.setAdapter(dataAdapter);
        blank6.setAdapter(dataAdapter);
        blank7.setAdapter(dataAdapter);
        blank8.setAdapter(dataAdapter);
        blank9.setAdapter(dataAdapter);
        blank10.setAdapter(dataAdapter);



        ans[0] = blank1.getSelectedItem().toString();
        ans[1] = blank2.getSelectedItem().toString();
        ans[2] = blank3.getSelectedItem().toString();
        ans[3]= blank4.getSelectedItem().toString();
        ans[4] = blank5.getSelectedItem().toString();
        ans[5] = blank6.getSelectedItem().toString();
        ans[6] = blank7.getSelectedItem().toString();
        ans[7] = blank8.getSelectedItem().toString();
        ans[8] = blank9.getSelectedItem().toString();
        ans[9] = blank10.getSelectedItem().toString();



     }


    public int Score( String[] WordString,  String[] AnsString){

        int scores=0;
        for (int i=0;i<WordString.length;i++){

            if (WordString[i].contains(AnsString[i])) {
                scores++;
            }
        }
        return scores;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class jsontask extends AsyncTask<String, String, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line ;
                while ((line=reader.readLine())!= null) {

                    buffer.append(line);
                }

                String json = buffer.toString();
                JSONObject parentObj = new JSONObject(json);
                JSONObject query = parentObj.getJSONObject("query");

                JSONArray pages = query.getJSONArray("pages");
                JSONObject pagearray = pages.getJSONObject(0);


                String extract = pagearray.getString("extract");


                int lines = 0;
                int pos = 0;
                String print = null;
                int x;

                while ((pos = extract.indexOf(".", pos) + 1) != 0) {
                    lines++;
                    if(lines==10){
                        x= extract.indexOf("\n",pos);
                        print = extract.substring(0,x);
                        break;
                    }
                }
                String[] sentence = print.split(" ");



                for (int j = 0 ; j < words.length;j++){

                    boolean flag= false;

                    for (int i = 0; i < sentence.length ; i++) {
                        if (sentence[i].equals(words[j])) {
                            System.out.println(words[j]+" is found at "+ i);
                            System.out.println("size of "+words[j]+"="+words[j].length());

                            if (j==0){
                                System.out.println("end "+print.indexOf(words[j]));
                                System.out.println(print.substring(0,i));
                                subs[j]= print.substring(0,print.indexOf(words[j]));
                                System.out.println(print.indexOf(words[j]));


                            }
                            else{
                                int start = print.indexOf(words[j-1])+ words[j-1].length();
                                System.out.println(print.indexOf(words[j-1])+"+" +words[j-1].length());
                                System.out.println("start"+start);
                                System.out.println("end"+print.indexOf(words[j]));
                                subs[j]= print.substring(start,print.indexOf(words[j]));
                                if(j==9){
                                    int n =print.indexOf(words[j])+ words[j].length();
                                    subs[10]=print.substring(n);
                                }

                            }
                            flag= true;
                            break;
                        }
                    }

                    if (!flag ){
                        System.out.println("doesnt found");
                    }

                }

                return subs;


            } catch (MalformedURLException e1) {
                e1.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            substring1.setText(subs[0]);
            substring2.setText(subs[1]);
            substring3.setText(subs[2]);
            substring4.setText(subs[3]);
            substring5.setText(subs[4]);
            substring6.setText(subs[5]);
            substring7.setText(subs[6]);
            substring8.setText(subs[7]);
            substring9.setText(subs[8]);
            substring10.setText(subs[9]);
            substring11.setText(subs[10]);

             }
    }

    public void gotoScorePage(View view){
        Score(words,ans);
        Intent nextPage=new Intent(StartPage.this,ScorePage.class);
        nextPage.putExtra("Score",Score(words,ans));
        startActivity(nextPage);
    }


}
