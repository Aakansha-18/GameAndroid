package com.example.harimohan.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends AppCompatActivity {

   // private TextView tvdata;
    public TextView substring1 = (TextView) findViewById(R.id.subs1);
    public TextView substring2 = (TextView) findViewById(R.id.subs2);
    public TextView substring3 = (TextView) findViewById(R.id.subs3);
    public TextView substring4 = (TextView) findViewById(R.id.subs4);
    public TextView substring5 = (TextView) findViewById(R.id.subs5);
    public TextView substring6 = (TextView) findViewById(R.id.subs6);
    public TextView substring7 = (TextView) findViewById(R.id.subs7);
    public TextView substring8 = (TextView) findViewById(R.id.subs8);
    public TextView substring9 = (TextView) findViewById(R.id.subs9);
    public TextView substring10 = (TextView) findViewById(R.id.subs10);
    public TextView substring11 = (TextView) findViewById(R.id.subs11);
    public EditText blank1 =(EditText) findViewById(R.id.Blank1);
    public EditText blank2 =(EditText) findViewById(R.id.Blank2);
    public EditText blank3 =(EditText) findViewById(R.id.Blank3);
    public EditText blank4 =(EditText) findViewById(R.id.Blank4);
    public EditText blank5 =(EditText) findViewById(R.id.Blank5);
    public EditText blank6 =(EditText) findViewById(R.id.Blank6);
    public EditText blank7 =(EditText) findViewById(R.id.Blank7);
    public EditText blank8 =(EditText) findViewById(R.id.Blank8);
    public EditText blank9 =(EditText) findViewById(R.id.Blank9);
    public EditText blank10 =(EditText) findViewById(R.id.Blank10);
    String[] subs = new String[20] ;
    private int position = 0;
    String[] words = "batsmen domestically scored ranked appearances previously achievement announced recipient aviation".split(" ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // URL Address
       // String url = "https://en.wikipedia.org/w/api.php?action=query&titles=Sachin_Tendulkar&prop=revisions&rvprop=content&rvsection=1&format=json";
        final Button btnHit = (Button) findViewById(R.id.btnHit);
        //tvdata = (TextView) findViewById(R.id.tvJasonItem);
       // tvdata.setMovementMethod(new ScrollingMovementMethod());

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new jsontask(). execute("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext=&titles=Sachin_Tendulkar&formatversion=2&format=json");
              // new jsontask().execute("https://en.wikipedia.org/w/api.php?action=query&titles=Sachin_Tendulkar&prop=revisions&rvprop=content&rvsection=1&formatversion=2&format=json");
            }
        });

    }


    public class jsontask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
             HttpURLConnection connection = null;
             BufferedReader reader = null;
            try {
                URL url = null;
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line=reader.readLine())!= null) {

                    buffer.append(line);
                }

                String finaljson = buffer.toString();
                JSONObject parentObj = new JSONObject(finaljson);
                JSONObject query = parentObj.getJSONObject("query");

                JSONArray pages = query.getJSONArray("pages");
                JSONObject pagearray = pages.getJSONObject(0);


                String extract = pagearray.getString("extract");


                int lines = 0;
                int pos = 0;
                String print = null;
                int x=0;

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
                            else if(j==9){
                                int start = print.indexOf(words[j-1])+ words[j-1].length();
                                subs[j]= print.substring(start);
                                System.out.println(subs[j]);
                            }
                            else{
                                int start = print.indexOf(words[j-1])+ words[j-1].length();
                                System.out.println(print.indexOf(words[j-1])+"+" +words[j-1].length());
                                System.out.println("start"+start);
                                System.out.println("end"+print.indexOf(words[j]));
                                subs[j]= print.substring(start,print.indexOf(words[j]));

                            }
                            flag= true;
                            break;
                        }
                    }

                    if (flag == false){
                        System.out.println("doesnt found");
                    }

                }

                return subs[1];


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //tvdata.setText(result);

            substring1.setText(subs[1]);
            substring2.setText(subs[2]);
            substring3.setText(subs[3]);
            substring4.setText(subs[4]);
            substring5.setText(subs[5]);
            substring6.setText(subs[6]);
            substring7.setText(subs[7]);
            substring8.setText(subs[8]);
            substring9.setText(subs[9]);
            substring10.setText(subs[10]);
            substring11.setText(subs[11]);
            blank1.setText(words[1]);
            blank2.setText(words[2]);
            blank3.setText(words[3]);
            blank4.setText(words[4]);
            blank5.setText(words[5]);
            blank6.setText(words[6]);
            blank7.setText(words[7]);
            blank8.setText(words[8]);
            blank9.setText(words[9]);
            blank10.setText(words[10]);


        }
    }

}









