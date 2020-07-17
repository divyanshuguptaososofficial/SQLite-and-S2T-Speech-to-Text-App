package com.example.sqllite;


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



public class ResetPasswordActivity extends AppCompatActivity {
    Button reset_btn;
    EditText edEmail;
    sqlite mydb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mydb3 = new sqlite(this);

        edEmail = findViewById(R.id.edEmail);
        reset_btn=findViewById(R.id.button_reset);


        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetworkConnection()) {
                    String email_user1 = edEmail.getText().toString();
                    boolean res = validate_Email();
                    if (res == true) {
                        boolean res1 = mydb3.checkEmail(email_user1);
                        if (res1 == true) {

                            Intent intent = new Intent(ResetPasswordActivity.this, ResetPasswordActivity2.class);
                            intent.putExtra("email_user1", email_user1);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Email doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else{
                    Toast.makeText(ResetPasswordActivity.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
                }
            }


    });
    }


    public boolean validate_Email(){
            String email_user1 = edEmail.getText().toString();

            if (email_user1.isEmpty()) {
                edEmail.setError("Email is required!");
                return false;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email_user1).matches()){
                edEmail.setError("Invalid Email Id");
            return false;
            }
            else {
                edEmail.setError(null);
return true;

            }
    }

    public  boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}


