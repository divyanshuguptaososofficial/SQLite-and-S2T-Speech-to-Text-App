package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;


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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
sqlite mydb;

Button signup,login;
EditText text_name,text_email,text_password,text_phone_no,text_college,text_city,text_state,text_country;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new sqlite(this);
        mydb.getWritableDatabase();


        signup = findViewById(R.id.button);

        text_name = findViewById(R.id.edName);
        text_email = findViewById(R.id.edEmail);
        text_password = findViewById(R.id.edPassword);
        text_phone_no = findViewById(R.id.phoneno);
        text_city = findViewById(R.id.city);
        text_college = findViewById(R.id.collegeid);
        text_state = findViewById(R.id.state);
        text_country = findViewById(R.id.country);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_user1 = text_email.getText().toString();

                if (TextUtils.isEmpty(text_name.getText())) {
                    text_name.setError("Name is required!");
                } else if (email_user1.isEmpty()) {
                    text_email.setError("Email is required!");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email_user1).matches()) {
                    text_email.setError("Invalid Email Id");
                } else if (TextUtils.isEmpty(text_password.getText())) {
                    text_password.setError("Password is required!");
                } else if (TextUtils.isEmpty(text_phone_no.getText())) {
                    text_phone_no.setError("Phone Number is required!");
                } else if (TextUtils.isEmpty(text_city.getText())) {
                    text_city.setError("City is required!");
                } else if (TextUtils.isEmpty(text_college.getText())) {
                    text_college.setError("College is required!");
                } else if (TextUtils.isEmpty(text_state.getText())) {
                    text_state.setError("State is required!");
                } else if (TextUtils.isEmpty(text_country.getText())) {
                    text_country.setError("Country is required!");
                } else {
                    saveToLocalDatabase(text_name.getText().toString(),text_email.getText().toString(),text_password.getText().toString(),text_phone_no.getText().toString(),text_college.getText().toString(),text_city.getText().toString(),text_state.getText().toString(),text_country.getText().toString());
                }


            }
        });
    }


    public void saveToLocalDatabase(final String  name, final String email, final String pass, final String phone, final String college, final String city, final String state, final String country){

    if (checkNetworkConnection()) {
        boolean inserted1 = mydb.insert("hello");
        boolean inserted = mydb.insertData(text_name.getText().toString(), text_email.getText().toString(), text_password.getText().toString(), text_phone_no.getText().toString(), text_college.getText().toString(), text_city.getText().toString(), text_state.getText().toString(), text_country.getText().toString());
        if (inserted == true && inserted1 == true) {
            Toast.makeText(Signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(Signup.this, "Unable to signup", Toast.LENGTH_SHORT).show();
        }


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    if (Response.equals("OK")) {
                        Toast.makeText(Signup.this, "Signing In", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Signup.this, "Connect to Internet & try again", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Signup.this, "Connect to Internet & try again", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("emailid", email);
                params.put("password", pass);
                params.put("phoneno", String.valueOf(phone));
                params.put("college", college);
                params.put("city", city);
                params.put("state", state);
                params.put("country", country);
                return params;

            }
        };
        MySingleton.getInstance(Signup.this).addToRequestQue(stringRequest);
    } else {
        Toast.makeText(Signup.this, "Connect to Internet & try again", Toast.LENGTH_LONG).show();
    }


}



    public void login(View view) {
        if(checkNetworkConnection()) {
            Intent intent = new Intent(Signup.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Signup.this,"Connect to Internet & try again",Toast.LENGTH_LONG).show();
        }
    }

    public  boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
