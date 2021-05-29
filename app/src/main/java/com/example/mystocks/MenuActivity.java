package com.example.mystocks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

    }
    public void click(View view){
        TextView show = (TextView) findViewById(R.id.show);
        LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }



    public void calc(View view){
        Intent calcI = new Intent(MenuActivity.this, CalculatorActivity.class);
        startActivity(calcI);
    }

    public void menu(View view){
        Intent menuI = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(menuI);
    }

    public void div(View view){
        Intent divI = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(divI);
    }



    public void mDiv(View view) {
        Intent mDivI = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(mDivI);
    }

    public void mCalc(View view) {
        Intent mCalcI = new Intent(MenuActivity.this, CalculatorActivity.class);
        startActivity(mCalcI);
    }


    public void mTax(View view) {
        Intent mHowUseAppI = new Intent(MenuActivity.this, TaxationOfDividendsActivity.class);
        startActivity(mHowUseAppI);
    }

    public void mAboutApp(View view) {
        Intent mAboutAppI = new Intent(MenuActivity.this, AboutAppActivity.class);
        startActivity(mAboutAppI);
    }

    public void open_site(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mystocks-test-f.herokuapp.com/"));
        startActivity(browserIntent);
    }
}
