package com.example.sqllite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
sqlite mydb1;
Button login_btn;
EditText email,password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb1=new sqlite(this);

        email = findViewById(R.id.email);
        password=findViewById(R.id.pass);
login_btn=findViewById(R.id.button_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_user= email.getText().toString().trim();
                String password_user= password.getText().toString().trim();


                boolean res = mydb1.checkUser(email_user,password_user);
                if(res == true){

                    Intent intent = new Intent(Login.this, Home.class);
                        intent.putExtra("email_user",email_user);
                        startActivity(intent);

                }
                else{
                    Toast.makeText(Login.this,"Invalid Email/Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
