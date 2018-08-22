package com.example.dicks.mysmartapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserDash extends AppCompatActivity {
    //private static final String UPDATE_DB_URL = "http://192.168.137.36/update.php";
    //public static final String DATA_URL = "http://192.168.137.36/search.php?room=";
    private static final String UPDATE_DB_URL = "http://192.168.43.64/update.php";
    public static final String DATA_URL = "http://192.168.43.64/search.php?room=";
    public static final String JSON_ARRAY = "result";
    public static final String KEY_STATUS = "status";

    String status="";
    String statusresult="";
    String statusresult1="";
    String statusresult2="";
    String statusresult3="";
    String room;

    ToggleButton led1, led2,led3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdash);
        final String username=getIntent().getStringExtra("username");

        led1 = (ToggleButton) findViewById(R.id.toggleButton);
        led2 = (ToggleButton) findViewById(R.id.toggleButton2);
        led3 = (ToggleButton) findViewById(R.id.toggleButton3);


        getData();


        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    room="room1";
                    //status="led1=1";
                    status="1";
                    UpdateStatus(room,status,username);
                } else {
                    // The toggle is disabled
                    //status="led1=0";
                    status="0";
                    room="room1";
                    UpdateStatus(room,status,username);
                }
            }
        });
        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /* Toggle button is led 2 */
                    room="room2";
                    status="1";
                    UpdateStatus(room,status,username);
                } else {
                    room="room2";
                    status="0";
                    UpdateStatus(room,status,username);
                }
            }
        });
        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /* Toggle button is led 3 */
                    room="room3";
                    status="1";
                    UpdateStatus(room,status,username);
                } else {
                    room="room3";
                    status="0";
                    UpdateStatus(room,status,username);
                }
            }
        });

    }

    private void UpdateStatus(String room,String status,String username) {
        class UpdateStatusClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UserDash.this, "Updating entry", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("")){
                    Toast.makeText(UserDash.this,"Check your Network connection", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UserDash.this,s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("room", params[0]);
                data.put("status", params[1]);
                data.put("username",params[2]);


                String result = ruc.sendPostRequest(UPDATE_DB_URL, data);

                return result;
            }
        }
        UpdateStatusClass dc = new UpdateStatusClass();
        dc.execute(room,status,username);
    }

    private ProgressDialog loading;
    private void getData() {

        String room1="room1";
        String room2="room2";
        String room3="room3";
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url1 = DATA_URL+room1;
        String url2 = DATA_URL+room2;
        String url3 = DATA_URL+room3;

        StringRequest stringRequest = new StringRequest(url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                loading.dismiss();
                showJSON(response1);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDash.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });
        StringRequest stringRequest2 = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                loading.dismiss();
                showJSON2(response2);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDash.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        StringRequest stringRequest3 = new StringRequest(url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                loading.dismiss();
                showJSON3(response3);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDash.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest2);
        requestQueue.add(stringRequest3);
    }

    private void showJSON(String response1){


        try {

            JSONObject jsonObject = new JSONObject(response1);
            JSONArray result1 = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result1.getJSONObject(0);
            statusresult1 = collegeData.getString("status");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (statusresult1.equals("1")) {
            led1.setChecked(true);
        } else {
            led1.setChecked(false);
        }


    }
    private void showJSON2(String response2){


        try {

            JSONObject jsonObject = new JSONObject(response2);
            JSONArray result2 = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result2.getJSONObject(0);
            statusresult2 = collegeData.getString("status");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (statusresult2.equals("1")) {
            led2.setChecked(true);
        } else {
            led2.setChecked(false);
        }


    }

    private void showJSON3(String response3){


        try {

            JSONObject jsonObject = new JSONObject(response3);
            JSONArray result3 = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result3.getJSONObject(0);
            statusresult3 = collegeData.getString("status");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (statusresult3.equals("1")) {
            led3.setChecked(true);
        } else {
            led3.setChecked(false);
        }


    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("You will be Signed out")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent=new Intent(UserDash.this,ActivityLogin.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}
