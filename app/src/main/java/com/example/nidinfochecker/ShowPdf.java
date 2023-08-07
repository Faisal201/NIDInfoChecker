package com.example.nidinfochecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowPdf extends AppCompatActivity implements OnPageChangeListener {

    String fileName = "";
    String extractedText = "";
    FloatingActionButton nextWord;
    ImageButton btn_search;

    Integer j;
    List<Integer> aparitii = new ArrayList<>();
    EditText searchPdf;
    List<Integer> listapagini = new ArrayList<>();

    private PDFView pdfView;
    private TextView pdfTitleWithPageCount;
    Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);

        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        fileName = intent.getStringExtra("fileName");

        searchPdf = findViewById(R.id.searchPdf);
        nextWord = findViewById(R.id.nextWord);
        btn_search = findViewById(R.id.btn_search);
        nextWord.setVisibility(View.GONE);

        pdfView = findViewById(R.id.pdfView);
        pdfTitleWithPageCount = findViewById(R.id.pdfTitleWithPageCount);

        searchPdf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btn_search.setOnClickListener(v ->{
                    s.toString();
                    if(s.toString().trim().isEmpty()){
                        Toast.makeText(ShowPdf.this,"You have to enter a word!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(listapagini.size()==0){
                            matchWord(s.toString().toLowerCase(),extractedText);
                        }
                        else {
                            Toast.makeText(ShowPdf.this,"You have to go through all occurrences of the word already!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_search.setOnClickListener(v ->{
                    s.toString();
                    if(s.toString().trim().isEmpty()){
                        Toast.makeText(ShowPdf.this,"You have to enter a word!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(listapagini.size()==0){
                            matchWord(s.toString().toLowerCase(),extractedText);
                        }
                        else {
                            Toast.makeText(ShowPdf.this,"You have to go through all occurrences of the word already!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_search.setOnClickListener(v ->{
                    s.toString();
                    if(s.toString().trim().isEmpty()){
                        Toast.makeText(ShowPdf.this,"You have to enter a word!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(listapagini.size()==0){
                            matchWord(s.toString().toLowerCase(),extractedText);
                        }
                        else {
                            Toast.makeText(ShowPdf.this,"You have to go through all occurrences of the word already!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        extractTextPDF();

        pdfView.fromAsset(fileName)
                .onPageChange(this)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .autoSpacing(false)
                .pageFitPolicy(FitPolicy.WIDTH)
                .fitEachPage(false)
                .pageSnap(false)
                .pageFling(false)
                .nightMode(false)
                .load();

    }

    class RetrievePDffromUrl extends AsyncTask<String, Void, InputStream> implements com.example.nidinfochecker.RetrievePDffromUrl {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        public void onPostExecute() {

        }

        @Override
        public void onPostExecute(InputStream inputStream) {

        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        pdfTitleWithPageCount.setText(String.format("(%s) %s / %s", fileName, page+1, pageCount));

    }

    HashMap<Integer,String> map = new HashMap<>();

    public void extractTextPDF(){
        new Thread(()->{
            try {
                PdfReader reader = new PdfReader(fileName);
                int n = reader.getNumberOfPages();
                for(int i=0; i<n; i++){
                    String extractedText = PdfTextExtractor.getTextFromPage(reader, i+1).trim().toLowerCase();
                    extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i+1).trim().toLowerCase();
                }
                reader.close();
            }catch (Exception e){
                Log.d("Error: ", String.valueOf(e));
            }
        }).start();
    }

    private void matchWord(String cuvant, String extractedText){
        nextWord.setVisibility(View.VISIBLE);
        if(extractedText.contains(cuvant)){
            for(Map.Entry<Integer, String> integerStringEntry : map.entrySet()){
                String text = ((Map.Entry) integerStringEntry).getValue().toString();
                Integer pagina = (Integer) ((Map.Entry) integerStringEntry).getKey();
                if(text.contains(cuvant)){
                    listapagini.add(pagina);
                    aparitii.add(pagina);
                }
            }
        }
        if(listapagini.size()==1){
            Toast.makeText(this,"The search word has only one appearance.",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"The search word has "+listapagini.size()+" issues",Toast.LENGTH_LONG).show();
            nextWord.setOnClickListener(v->{
                if(aparitii.size() != 0 && listapagini.size() != 0){
                    j = 0;
                    pdfView.jumpTo(listapagini.get(j)-1);
                    if(aparitii.contains(listapagini.get(j))){
                        aparitii.remove(listapagini.get(j));
                        listapagini.remove(listapagini.get(j));
                    }
                }
                else {
                    Toast.makeText(this,"Look for another word.",Toast.LENGTH_LONG).show();
                    nextWord.setVisibility(View.GONE);
                }
            });
        }
    }

}