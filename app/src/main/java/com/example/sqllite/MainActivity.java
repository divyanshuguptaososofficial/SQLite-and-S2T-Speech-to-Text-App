package com.example.sqllite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
sqlite mydb1;
Button login_btn;
EditText email,password;
    public static final String SERVER_URL="http://10.0.2.2/poc/poc.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb1 = new sqlite(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login_btn = findViewById(R.id.button_login);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNetworkConnection()){

                                    String email_user = email.getText().toString().trim();
                                    String password_user = password.getText().toString().trim();


                                    validateEmail();
                                    if (TextUtils.isEmpty(password_user)) {
                                        password.setError("Password is required!");
                                    } else {
                                        boolean res = mydb1.checkUser(email_user, password_user);
                                        if (res == true) {

                                            Intent intent = new Intent(MainActivity.this, Home.class);
                                            intent.putExtra("email_user", email_user);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(MainActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                        }

                else{
                    Toast.makeText(MainActivity.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public void validateEmail(){
        String email_user1 = email.getText().toString();

        if (email_user1.isEmpty()) {
            email.setError("Email is required!");

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email_user1).matches()){
            email.setError("Invalid Email Id");

        }
        else {
            email.setError(null);

        }

    }
     public void signup(View view) {
        if(checkNetworkConnection()) {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
        }
                    }
    public void forget(View view) {
        if(checkNetworkConnection()) {
            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
        }

    }







    public  boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
                }
             

