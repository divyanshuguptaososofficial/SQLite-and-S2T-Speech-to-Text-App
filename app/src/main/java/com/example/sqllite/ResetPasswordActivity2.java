package com.example.sqllite;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity2 extends AppCompatActivity {
    Button reset2;
    EditText new_pass,confirm_new_pass;
    sqlite mydb4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);
        mydb4 = new sqlite(this);

        Intent i1=getIntent();
         final String emailuser= i1.getStringExtra("email_user1");

        reset2= findViewById(R.id.button_reset);
        new_pass=findViewById(R.id.new_pass);
        confirm_new_pass= findViewById(R.id.pass);

        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNetworkConnection()) {
                    String password1 = new_pass.getText().toString();
                    String password2 = confirm_new_pass.getText().toString();
                    if (password1.isEmpty()) {
                        new_pass.setError("Password is required!");
                    } else if (password2.isEmpty()) {
                        confirm_new_pass.setError("Password is required!");
                    } else if (!password1.isEmpty() && !password2.isEmpty()) {
                        if (password1.equals(password2)) {
                            boolean res1 = mydb4.UpdatePassword(emailuser, password1);
                            if (res1 == true) {
                                Toast.makeText(ResetPasswordActivity2.this, "Password change successful", Toast.LENGTH_SHORT).show();
                                Intent i1 = new Intent(ResetPasswordActivity2.this, MainActivity.class);
                                startActivity(i1);

                            } else {
                                Toast.makeText(ResetPasswordActivity2.this, "Password change failed", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            confirm_new_pass.setError("Password doesn't match!");
                        }
                    }
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity2.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    public  boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    }
