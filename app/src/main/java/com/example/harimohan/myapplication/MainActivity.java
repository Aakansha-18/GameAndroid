package com.example.harimohan.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 }

    public void gotoNextPage(View view){
        Intent nextPage=new Intent(MainActivity.this,StartPage.class);
        startActivity(nextPage);
    }




}









