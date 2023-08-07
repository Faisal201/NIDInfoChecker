package com.example.nidinfochecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Talaimari extends AppCompatActivity {

    private CardView pdfTalaimari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talaimari);

        pdfTalaimari = (CardView) findViewById(R.id.pdfTalaimari);

        pdfTalaimari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Talaimari.this, ShowPdf.class);
                i.putExtra("fileName", "talaimari.pdf");
                startActivity(i);
            }
        });
    }
}