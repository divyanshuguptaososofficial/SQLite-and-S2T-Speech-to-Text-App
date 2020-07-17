package com.example.sqllite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
public class Home extends AppCompatActivity {
    private TextView txvResult,word,name;
    sqlite mydb2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mydb2=new sqlite(this);

        Intent intent1 = getIntent();
        String email= intent1.getStringExtra("email_user");
        txvResult = (TextView) findViewById(R.id.txvResult);
        word= findViewById(R.id.word);
        name =findViewById(R.id.name);

        Cursor cursor1 =mydb2.ViewName(email);
        StringBuilder stringBuilder1= new StringBuilder();
        while(cursor1.moveToNext()){
            stringBuilder1.append(" "+cursor1.getString(0));
            name.setText(stringBuilder1);
        }

        Cursor cursor =mydb2.ViewData();
        StringBuilder stringBuilder= new StringBuilder();
        while(cursor.moveToNext()){
            stringBuilder.append(" "+cursor.getString(1));
            word.setText(stringBuilder);
        }


    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(" "+result.get(0));
                    String spoken= (String) txvResult.getText();

                    if (!word.getText().toString().isEmpty()) {
                        if (word.getText().toString().length() >= 0) {
                            boolean res= word.getText().toString().equals(spoken);
                            if(res == true){
                                Toast.makeText(Home.this,"Hurray! You said the word right",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(Home.this,"Oops! You said the word wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        name.setError("Plz Speak");
                    }
                }


                break;
        }
    }

}
