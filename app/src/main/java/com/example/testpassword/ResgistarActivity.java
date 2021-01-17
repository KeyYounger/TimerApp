package com.example.testpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResgistarActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private Button registar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistar);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        registar = (Button)findViewById(R.id.registar);

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account==null || password==null || account.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(ResgistarActivity.this, "请先完成注册", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor preferences= getSharedPreferences("data", MODE_PRIVATE).edit();
                    preferences.putString("account",account.getText().toString());
                    preferences.putString("password",password.getText().toString());
                    preferences.apply();
                    Toast.makeText(ResgistarActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResgistarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}