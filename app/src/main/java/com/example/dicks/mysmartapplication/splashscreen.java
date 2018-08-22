package com.example.dicks.mysmartapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        Thread myThread=new Thread(){
            @Override

            public void run(){
                try {
                    sleep(3000);
                    Intent intent=new Intent(getApplicationContext(),ActivityLogin.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
