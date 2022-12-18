package com.example.cryeptos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Write extends AppCompatActivity {
    Button writeBtn;
    EditText dataEtv;
    private String password,newData;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        writeBtn = findViewById(R.id.writeBtn1);
        dataEtv = findViewById(R.id.add_data1);

        Intent intent = getIntent();
        password = intent.getStringExtra("pass");
        dataEtv.setText(AES.decrypt(readFile("log_proc.txt"),password));

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newData = dataEtv.getText().toString();
                writeFile("log_proc.txt",AES.encrypt(newData,password));
                Toast.makeText(Write.this, "file written", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String readFile(String filename) {
        String text = "";
        try{
            FileInputStream fis = openFileInput(filename);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            text = new String(buffer);
            fis.close();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }
    private void writeFile(String filename,String newData){
        try{
            FileOutputStream fos = openFileOutput(filename,MODE_PRIVATE);
            fos.write(newData.getBytes());
            fos.close();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}