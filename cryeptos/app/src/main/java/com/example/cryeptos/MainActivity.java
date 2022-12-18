package com.example.cryeptos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {
    Button readBtn,writeBtn,initBtn;
    EditText passwordTxt;
    TextView data;
    private String filename = "log_proc.txt";
    String cipherText,plainText,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readBtn = findViewById(R.id.readBtn);
        writeBtn =  findViewById(R.id.writeBtn);
        initBtn =  findViewById(R.id.initBtn);
        passwordTxt =  findViewById(R.id.password);
        data = findViewById(R.id.scrolltext);


        writeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Write.class);
                intent.putExtra("pass",password);
                startActivity(intent);
            }
        });

        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Initialize.class);
                intent.putExtra("pass",password);
                startActivity(intent);
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                password = passwordTxt.getText().toString();
                plainText = AES.decrypt(readFile(filename),password);
                data.setText(plainText);
                //data.setText(read);
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
            File file = getFileStreamPath(filename);
            String s  = file.getAbsolutePath();
            Log.e("path",s);
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}