package com.example.mystocks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

    }

    public void calc(View view){
        Intent calcI = new Intent(AboutAppActivity.this, CalculatorActivity.class);
        startActivity(calcI);
    }

    public void menu(View view){
        Intent menuI = new Intent(AboutAppActivity.this, MenuActivity.class);
        startActivity(menuI);
    }

    public void div(View view){
        Intent divI = new Intent(AboutAppActivity.this, MainActivity.class);
        startActivity(divI);
    }

}
