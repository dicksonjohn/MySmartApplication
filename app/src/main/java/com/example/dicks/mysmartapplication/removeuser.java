package com.example.dicks.mysmartapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class removeuser extends AppCompatActivity implements View.OnClickListener{

    //final String username=getIntent().getStringExtra("username");

    //private static final String URL_DELETE = "http://192.168.137.36/delete.php?username=";
    private static final String URL_DELETE = "http://192.168.43.64/delete.php?username=";
    private EditText editTextUserName;
    private Button buttonRemoveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeuser);
        buttonRemoveUser=(Button) findViewById(R.id.buttonRemoveUser);
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);

        buttonRemoveUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRemoveUser){
            confirmDeleteEntry();
        }
    }

    private void deleteEntry(){
        final String username=editTextUserName.getText().toString().trim().toLowerCase();

        class DeleteEntry extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(removeuser.this, "Deleting...", "Wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("")){
                    Toast.makeText(removeuser.this,"Check your Network connection", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(removeuser.this,s, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                RegisterUserClass ruc= new RegisterUserClass();
                String s = ruc.sendGetRequestParam(URL_DELETE, username);
                return s;
            }
        }

        DeleteEntry de = new DeleteEntry();
        de.execute();
        editTextUserName.setText("");
    }

    private void confirmDeleteEntry(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to remove this user?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEntry();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(removeuser.this,MainActivity.class);
        startActivity(intent);
        //intent.putExtra("username",username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}
