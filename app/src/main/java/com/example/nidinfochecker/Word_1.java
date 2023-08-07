package com.example.nidinfochecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Word_1 extends AppCompatActivity {

    private CardView pdfWord1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_1);

        pdfWord1 = (CardView) findViewById(R.id.pdfWord1);

        pdfWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Word_1.this, ShowPdf.class);
                i.putExtra("fileName", "talaimari.pdf");
                startActivity(i);
            }
        });
    }
}