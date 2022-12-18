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

public class Initialize extends AppCompatActivity {
    Button initBtn1;
    EditText newDataTxt;
    String newData;
    private String filename = "log_proc.txt",password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        initBtn1 = findViewById(R.id.initBtn1);
        newDataTxt = findViewById(R.id.add_data);
        Intent intent = getIntent();
        password = intent.getStringExtra("pass");

        initBtn1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                newData = newDataTxt.getText().toString();

                writeFile(filename, AES.encrypt(newData,password));
                Toast.makeText(Initialize.this, "encrytion complete", Toast.LENGTH_SHORT).show();

            }
        });
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