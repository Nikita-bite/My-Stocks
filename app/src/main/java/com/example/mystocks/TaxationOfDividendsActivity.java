package com.example.mystocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TaxationOfDividendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxationofdividends);
        getSupportActionBar().hide();

    }

    public void calc(View view){
        Intent calcI = new Intent(TaxationOfDividendsActivity.this, CalculatorActivity.class);
        startActivity(calcI);
    }

    public void menu(View view){
        Intent menuI = new Intent(TaxationOfDividendsActivity.this, MenuActivity.class);
        startActivity(menuI);
    }

    public void div(View view){
        Intent divI = new Intent(TaxationOfDividendsActivity.this, MainActivity.class);
        startActivity(divI);
    }
}