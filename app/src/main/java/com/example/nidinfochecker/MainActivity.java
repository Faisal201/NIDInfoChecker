package com.example.nidinfochecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String [] location;
    String [] word;
    private Spinner spinner1;
    private Spinner spinner2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = getResources().getStringArray(R.array.location);
        word = getResources().getStringArray(R.array.word);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        button = (Button) findViewById(R.id.button);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner1_view,R.id.spinner1Item,location);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner2_view,R.id.spinner2Item,word);
        spinner2.setAdapter(adapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinner1Value = spinner1.getSelectedItem().toString();
                String spinner2Value = spinner2.getSelectedItem().toString();

                if ((spinner1Value.equals("নির্বাচন করা নেই") && spinner2Value.equals("নির্বাচন করা নেই")) || (!spinner1Value.equals("নির্বাচন করা নেই") && !spinner2Value.equals("নির্বাচন করা নেই"))){
                    Toast toast=Toast.makeText(getApplicationContext(),"যে কোন একটি বিষয় নির্বাচন করুন",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }

                else if (!spinner1Value.equals("নির্বাচন করা নেই") && spinner2Value.equals("নির্বাচন করা নেই")){
                    if (spinner1Value.equals("তালাইমারি")){
                        Intent i = new Intent(MainActivity.this, Talaimari.class);
                        startActivity(i);
                    }
                    else {
                        Toast toast=Toast.makeText(getApplicationContext(),"দুঃখিত! ইন্টার্নাল কোন সমস্যা হয়েছে!",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                    }
                }

                else {
                    if (spinner2Value.equals("ওয়ার্ড ১")){
                        Intent i = new Intent(MainActivity.this, Word_1.class);
                        startActivity(i);
                    }
                    else {
                        Toast toast=Toast.makeText(getApplicationContext(),"দুঃখিত! ইন্টার্নাল কোন সমস্যা হয়েছে!",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                    }
                }

            }
        });

    }
}