package com.example.final_group_expenser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class share extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        button = findViewById(R.id.genpdf);


        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument mypdfdocument = new PdfDocument();
                Paint myPaint  = new Paint();

                PdfDocument.PageInfo myPaintinfo1 = new PdfDocument.PageInfo.Builder(250,400,1).create();
                PdfDocument.Page myPage1 = mypdfdocument.startPage(myPaintinfo1);

                Canvas canvas =myPage1.getCanvas();

                canvas.drawText("Welcome to trip expenser",40,50,myPaint);
                mypdfdocument.finishPage(myPage1);

                File file = new File(Environment.getExternalStorageDirectory(),"/FGE1.pdf");

                try {
                    mypdfdocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mypdfdocument.close();
                Toast.makeText(share.this, "Pdf Stored", Toast.LENGTH_SHORT).show();

            }

        });
    }
}