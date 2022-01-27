package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ImageButton btn_sign_sfu = (ImageButton) findViewById(R.id.SFU_btn_SignInPage);
        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);


        //btn_signIn
        btn_sign_sfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) //TODO: add SignUp Page
            {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                //notification(username, password);

                if(username.equals("") && password.equals("")){
                    showMessage();
                }
                else if (username.equals("")){
                    showMessageU();
                }
                else if(password.equals("")){
                    showMessageP();
                }
                else {
                    toMainAct(username);
                }

            }
        });
    }

    private void showMessageU(){
        String s = "Please enter your username";
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    private void showMessageP(){
        String s = "Please enter your password";
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    private void showMessage() {
        String s = "Please enter your username and password";
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void toMainAct(String username)
    {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }
}