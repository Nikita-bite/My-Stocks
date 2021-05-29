package com.example.mystocks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CalculatorActivity extends AppCompatActivity {

    private static final String LOG_TAG = "calc";
    private Api dataFromApi = new Api();
    private JSONObject jsonData;
    private JSONArray arrayData;
    private JSONObject jsonDataCompany;
    private final int numberCompany = 937;
    private boolean conToInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        conToInternet = isConnectedToInternet(this);
    }

    public void calc(View view){
        Intent calcI = new Intent(CalculatorActivity.this, CalculatorActivity.class);
        startActivity(calcI);
    }

    public void menu(View view){
        Intent menuI = new Intent(CalculatorActivity.this, MenuActivity.class);
        startActivity(menuI);
    }

    public void div(View view){
        Intent divI = new Intent(CalculatorActivity.this, MainActivity.class);
        startActivity(divI);
    }

    public void calculateDividend(View view) {
        if (conToInternet) {
            dataFromApi = new Api();

            Log.d(LOG_TAG, "truetrue123");

            dataFromApi.printJSON(LOG_TAG);
            EditText printticker = (EditText) findViewById(R.id.print_ticker);
            String textTicker = String.valueOf(printticker.getText()).toUpperCase();
            EditText printqty = (EditText) findViewById(R.id.qty);
            String qtyStr = String.valueOf(printqty.getText());
            Log.d(LOG_TAG, textTicker);
            Log.d(LOG_TAG, qtyStr);
            Log.d(LOG_TAG, "truetrue123");

            TextView countButton = (TextView) findViewById(R.id.countButton);

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(countButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            boolean showResult = false;
            boolean notData = false;

            String[] name = new String[numberCompany];
            String[] ticker = new String[numberCompany];
            String[] cap = new String[numberCompany];
            String[] price = new String[numberCompany];
            String[] div_year = new String[numberCompany];
            String[] div_year_q = new String[numberCompany];
            String[] ex_div_date = new String[numberCompany];

            try {
                jsonData = dataFromApi.getJSON();
                arrayData = dataFromApi.getCompanyArray();
                jsonDataCompany = dataFromApi.getCompany(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < numberCompany; i++) {
                try {
                    jsonDataCompany = dataFromApi.getCompany(i);
                    ticker[i] = dataFromApi.getCompanyField(i, "ticker");
                    if (textTicker.equals(ticker[i].toUpperCase())) {
                        ticker[i] = dataFromApi.getCompanyField(i, "ticker");
                        price[i] = dataFromApi.getCompanyField(i, "price");
                        div_year[i] = dataFromApi.getCompanyField(i, "div_year");
                        div_year_q[i] = dataFromApi.getCompanyField(i, "div_year_quarter");
                        Spinner countrySpinner = (Spinner) findViewById(R.id.spinner);
                        String country = countrySpinner.getSelectedItem().toString();
                        if (div_year[i].equals("Нет данных") || qtyStr.equals("")) {
                            showResult = true;
                            notData = true;
                            break;
                        }
                        double percent = 0;
                        if (country.equals("Россия")) {
                            percent = 0.87;
                        } else if (country.equals("США")) {
                            percent = 0.10;
                        } else if (country.equals("Германия")) {
                            percent = 0.75;
                        } else if (country.equals("Великобритания")) {
                            percent = 0.875;
                        } else if (country.equals("Япония")) {
                            percent = 0.85;
                        } else if (country.equals("Китай")) {
                            percent = 0.80;
                        } else if (country.equals("Гонконг")) {
                            percent = 1;
                        } else if (country.equals("Сингапур")) {
                            percent = 0.90;
                        } else {
                            percent = 0;
                        }
                        String priceStocksStr = price[i].split(" ")[0];
                        double priceStocks = Double.parseDouble(priceStocksStr);
                        int qty = Integer.parseInt(qtyStr);
                        double sumMoney = priceStocks * qty;
                        double colDiv = qty * Double.parseDouble(div_year[i].split(" ")[0]);
                        double colDiv_q = (colDiv / 4);
                        String perDivStr = div_year[i].split(" ")[1];
                        double perDiv_q;
                        String perDivWithTax = div_year[i].split("\\(")[1];
                        perDivWithTax = String.valueOf(perDivWithTax).split("%")[0];
                        perDiv_q = (Double.parseDouble(perDivWithTax) / 4);
                        double perDivWithTaxDouble = Double.parseDouble(perDivWithTax);
                        perDivWithTaxDouble = (perDivWithTaxDouble * percent);
                        double perDivWithTax_qDouble = perDivWithTaxDouble / 4;
                        double colDivWithTax = (colDiv * percent);
                        double colDivWithTax_q = (colDivWithTax / 4);

                        Log.d(LOG_TAG, "ТИКЕР: " + ticker[i]);
                        Log.d(LOG_TAG, "ЦЕНА: " + price[i]);
                        Log.d(LOG_TAG, "СУММА ДЕНЕГ: " + sumMoney + " $");
                        Log.d(LOG_TAG, "НАЛОГ: " + ((1 - percent) * 100) + " %");
                        Log.d(LOG_TAG, "ДИВИДЕНДЫ ЗА ГОД: " + colDiv + " " + perDivStr);
                        Log.d(LOG_TAG, "ДИВИДЕНДЫ ЗА КВАРТАЛ: " + colDiv_q + " (" + perDiv_q + "%)");
                        Log.d(LOG_TAG, "ДИВИДЕНДЫ ЗА ГОД С НАЛОГОМ: " + colDivWithTax + " (" + perDivWithTaxDouble + "%)");
                        Log.d(LOG_TAG, "ДИВИДЕНДЫ ЗА КВАРТАЛ С НАЛОГОМ: " + colDivWithTax_q + " (" + perDivWithTax_qDouble + "%)");

                        LinearLayout mainMenu = (LinearLayout) findViewById(R.id.main_menu);
                        mainMenu.setVisibility(View.GONE);
                        ScrollView scrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
                        scrollView.setVisibility(View.GONE);
                        TextView closeResult = (TextView) findViewById(R.id.close_res_calc);
                        closeResult.setVisibility(View.VISIBLE);
                        LinearLayout resMenu = (LinearLayout) findViewById(R.id.res_menu);
                        resMenu.setVisibility(View.VISIBLE);

                        TextView resTicker = (TextView) findViewById(R.id.res_ticker);
                        resTicker.setText("ТИКЕР: " + ticker[i]);
                        TextView resPrice = (TextView) findViewById(R.id.res_price);
                        resPrice.setText("ЦЕНА: " + price[i]);
                        TextView resSumMoney = (TextView) findViewById(R.id.res_sum_money);
                        resSumMoney.setText("СУММА ДЕНЕГ: " + roundAvoid(sumMoney, 2) + " $");
                        TextView resTax = (TextView) findViewById(R.id.res_tax);
                        resTax.setText("НАЛОГ: " + roundAvoid((1 - percent) * 100, 4) + " %");
                        TextView resDivYear = (TextView) findViewById(R.id.res_div_year);
                        resDivYear.setText("ДИВИДЕНДЫ ЗА ГОД: " + roundAvoid(colDiv, 2) + " " + perDivStr);
                        TextView resDivQ = (TextView) findViewById(R.id.res_div_q);
                        resDivQ.setText("ДИВИДЕНДЫ ЗА КВАРТАЛ: " + roundAvoid(colDiv_q, 2) + " (" + perDiv_q + "%)");
                        TextView resDivYearTax = (TextView) findViewById(R.id.res_div_year_tax);
                        resDivYearTax.setText("ДИВИДЕНДЫ ЗА ГОД С НАЛОГОМ: " + roundAvoid(colDivWithTax, 2) + " (" + roundAvoid(perDivWithTaxDouble, 2) + "%)");
                        TextView resDivQTax = (TextView) findViewById(R.id.res_div_q_tax);
                        resDivQTax.setText("ДИВИДЕНДЫ ЗА КВАРТАЛ С НАЛОГОМ: " + roundAvoid(colDivWithTax_q, 2) + " (" + roundAvoid(perDivWithTax_qDouble, 2) + "%)");
                        showResult = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (!showResult) {
                LinearLayout mainMenu = (LinearLayout) findViewById(R.id.main_menu);
                mainMenu.setVisibility(View.GONE);
                ScrollView scrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
                scrollView.setVisibility(View.GONE);
                TextView closeResult = (TextView) findViewById(R.id.close_res_calc);
                closeResult.setVisibility(View.VISIBLE);
                LinearLayout resMenu = (LinearLayout) findViewById(R.id.res_menu);
                resMenu.setVisibility(View.VISIBLE);
                TextView resTicker = (TextView) findViewById(R.id.res_ticker);
                resTicker.setText("НЕТ ТАКОГО ТИКЕРА ");
            } else if (notData) {
                LinearLayout mainMenu = (LinearLayout) findViewById(R.id.main_menu);
                mainMenu.setVisibility(View.GONE);
                ScrollView scrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
                scrollView.setVisibility(View.GONE);
                TextView closeResult = (TextView) findViewById(R.id.close_res_calc);
                closeResult.setVisibility(View.VISIBLE);
                LinearLayout resMenu = (LinearLayout) findViewById(R.id.res_menu);
                resMenu.setVisibility(View.VISIBLE);
                TextView resTicker = (TextView) findViewById(R.id.res_ticker);
                resTicker.setText("НЕТ ДИВИДЕНДОВ ");
            }
        } else {
            TextView noConnectText = (TextView) findViewById(R.id.notInt);
            noConnectText.setVisibility(View.VISIBLE);
        }


    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void closeResBut(View view) {
        Intent calcI = new Intent(CalculatorActivity.this, CalculatorActivity.class);
        startActivity(calcI);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getRootView().getWindowToken(), 0);
        return true;
    }
}