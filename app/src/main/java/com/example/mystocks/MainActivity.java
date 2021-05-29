package com.example.mystocks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "api";

    private int counter = 0;


    ArrayList<Integer> searchResults = new ArrayList<>();


    private final int numberCompany = 937;
    private final int numberCompanyOnPage = 20;


    String[] nameAS = new String[numberCompany];
    String[] tickerAS = new String[numberCompany];
    String[] capAS = new String[numberCompany];
    String[] priceAS = new String[numberCompany];
    String[] div_yearAS = new String[numberCompany];
    String[] div_year_qAS = new String[numberCompany];
    String[] ex_div_dateAS = new String[numberCompany];

    TextView[] company = new TextView[numberCompany];
    TextView[] tickerArray = new TextView[numberCompany];
    TextView[] capArray = new TextView[numberCompany];
    TextView[] priceArray = new TextView[numberCompany];
    TextView[] div_yearArray = new TextView[numberCompany];
    TextView[] div_dateArray = new TextView[numberCompany];
    View[] TableRow = new View[numberCompanyOnPage];
    TextView ld;

    TableLayout tableLayout;
    LayoutInflater inflater;
    boolean conToInternet;
    private int count;
    private Api dataFromApi;
    private static JSONObject jsonDataCompany;

    private JSONObject jsonData;
    private JSONArray arrayData;
    private int countpercent;
    private long m;
    private int indexPage = 1;
    private boolean isSearching = false;
    private int lenSearchResults;
    private boolean isUpdate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = System.currentTimeMillis();
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)));
        getSupportActionBar().hide();
        tableLayout = (TableLayout) findViewById(R.id.table_div);
        inflater = getLayoutInflater();
        conToInternet = isConnectedToInternet(this);
        ld = (TextView) findViewById(R.id.act_bg);
        Log.d(LOG_TAG, String.valueOf(conToInternet));
        TextView ld = (TextView) findViewById(R.id.act_bg);
        m = System.currentTimeMillis();
        TextView sT = (TextView) findViewById(R.id.show_table);
        dataFromApi = new Api();
        Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)));
        dataDisplay();
        Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)));
    }


    public void loadData(View view) {
        cancelSearch();
        if (conToInternet) {
            parserJson();
            isUpdate = true;
            dataDisplay();
            isUpdate = false;
            TableRow mainRow = (TableRow) findViewById(R.id.main_row);
            mainRow.setVisibility(View.VISIBLE);
            TextView sT = (TextView) findViewById(R.id.show_table);
            sT.setText("Обновить таблицу");
            Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD1");
        } else {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
            mainLayout.setVisibility(View.GONE);
            TextView noConnectText = (TextView) findViewById(R.id.noConnectText);
            noConnectText.setVisibility(View.VISIBLE);
            LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
            TableRow mainRow = (TableRow) findViewById(R.id.main_row);
            LinearLayout futer = (LinearLayout) findViewById(R.id.futer);
            TextView divCalText = (TextView) findViewById(R.id.div_cal_text);
        }
    }


    public void dataDisplay() {
        if (conToInternet) {
            Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD3");
            if (!isUpdate) {
                Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD10");
                dataFromApi = new Api();

                JSONObject jsonData = null;
                JSONArray arrayData = null;
                JSONObject jsonDataCompany = null;

                String name = null;
                String ticker = null;
                String cap = null;
                String price = null;
                String div_year = null;
                String div_year_q = null;
                String ex_div_date = null;


                try {
                    Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD20");
                    jsonData = dataFromApi.getJSON();
                    Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD21");
                    arrayData = dataFromApi.getCompanyArray();
                    Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD22");
                    jsonDataCompany = dataFromApi.getCompany(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD4");

            for (int i = 0; i < numberCompany; i++) {
                try {
                    jsonDataCompany = dataFromApi.getCompany(i);
                    nameAS[i] = dataFromApi.getCompanyField(i, "name");
                    tickerAS[i] = dataFromApi.getCompanyField(i, "ticker");
                    capAS[i] = dataFromApi.getCompanyField(i, "cap");
                    priceAS[i] = dataFromApi.getCompanyField(i, "price");
                    div_yearAS[i] = dataFromApi.getCompanyField(i, "div_year");
                    div_year_qAS[i] = dataFromApi.getCompanyField(i, "div_year_quarter");
                    ex_div_dateAS[i] = dataFromApi.getCompanyField(i, "Ex_Dividend_Date");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD5");


            for (int i = 0; i < TableRow.length; i++) {

                if (!isUpdate) {
                    TableRow[i] = inflater.inflate(R.layout.table, null);

                    tableLayout.addView(TableRow[i]);
                    company[i] = (TextView) findViewById(R.id.companyv);
                    capArray[i] = (TextView) findViewById(R.id.capv);
                    priceArray[i] = (TextView) findViewById(R.id.pricev);
                    div_yearArray[i] = (TextView) findViewById(R.id.sizedivv);
                    div_dateArray[i] = (TextView) findViewById(R.id.divdatev);

                    company[i].setId(Integer.parseInt(String.valueOf(i)));
                    capArray[i].setId(Integer.parseInt(String.valueOf(i + TableRow.length)));
                    priceArray[i].setId(Integer.parseInt(String.valueOf(i + TableRow.length * 2)));
                    div_yearArray[i].setId(Integer.parseInt(String.valueOf(i + TableRow.length * 3)));
                    div_dateArray[i].setId(Integer.parseInt(String.valueOf(i + TableRow.length * 4)));
                }
                Log.d(LOG_TAG, String.valueOf((double) (System.currentTimeMillis() - m)) + "lD6");
                company[i].setText(nameAS[i] + ", " + tickerAS[i]);
                capArray[i].setText(capAS[i]);
                priceArray[i].setText(priceAS[i]);
                div_yearArray[i].setText(div_yearAS[i] + " / " + div_year_qAS[i]);
                div_dateArray[i].setText(ex_div_dateAS[i]);
            }
        } else {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
            mainLayout.setVisibility(View.GONE);
            TextView noConnectText = (TextView) findViewById(R.id.noConnectText);
            noConnectText.setVisibility(View.VISIBLE);
            LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
            TableRow mainRow = (TableRow) findViewById(R.id.main_row);
            LinearLayout futer = (LinearLayout) findViewById(R.id.futer);
            TextView divCalText = (TextView) findViewById(R.id.div_cal_text);
            menu.setVisibility(View.INVISIBLE);
            mainRow.setVisibility(View.INVISIBLE);
            futer.setVisibility(View.INVISIBLE);
            divCalText.setVisibility(View.INVISIBLE);
        }
    }


    public void calc(View view) {
        Intent calcI = new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(calcI);
    }


    public void menu(View view) {
        Intent menuI = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(menuI);
    }


    public void div(View view) {
        Intent divI = new Intent(MainActivity.this, MainActivity.class);
        startActivity(divI);
    }


    public void searchStart(View view) {
        TextView brand = (TextView) findViewById(R.id.brand);
        ImageView lupa = (ImageView) findViewById(R.id.lupa);
        TextView searchText = (TextView) findViewById(R.id.search_bar);
        TextView showTable = (TextView) findViewById(R.id.show_table);
        int searchTextWidth = 100;
        int searchTextHeight = 100;

        if (counter == 0) {
            searchTextWidth = brand.getWidth();
            searchTextHeight = brand.getHeight();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            brand.setLayoutParams(params);
            searchText.setHeight(searchTextWidth);
            searchText.setWidth(searchTextHeight);
            searchText.setVisibility(View.VISIBLE);
            counter++;
        } else if (counter == 1) {
            String requestSearch = String.valueOf(searchText.getText());
            for (int i = 0; i < numberCompany; i++) {
                String convertedText = nameAS[i] + ", " + tickerAS[i] + ", " + capAS[i] + ", " + priceAS[i] + ", " + div_yearAS[i] + " / " + div_year_qAS[i] + ", " + ex_div_dateAS[i];
                if (String.valueOf(convertedText).toLowerCase().contains(requestSearch.toLowerCase())) {
                    searchResults.add(i);
                } else {
                }
            }
            isSearching = true;
            if (numberCompanyOnPage > searchResults.size()) {
                lenSearchResults = searchResults.size();
            } else {
                lenSearchResults = numberCompanyOnPage;
            }
            Log.d(LOG_TAG, String.valueOf(lenSearchResults));
            Log.d(LOG_TAG, String.valueOf(numberCompanyOnPage));
            Log.d(LOG_TAG, String.valueOf(searchResults.size()));

            if (lenSearchResults < numberCompanyOnPage) {
                for (int i = lenSearchResults; i < numberCompanyOnPage; i++) {
                    Log.d(LOG_TAG, String.valueOf(i));
                    company[i].setText("");
                    capArray[i].setText("");
                    priceArray[i].setText("");
                    div_yearArray[i].setText("");
                    div_dateArray[i].setText("");
                }
            }

            for (int i = 0; i < lenSearchResults; i++) {
                Log.d(LOG_TAG, String.valueOf(company[i]));
                company[i].setText(nameAS[searchResults.get(i)] + ", " + tickerAS[searchResults.get(i)]);
                capArray[i].setText(capAS[searchResults.get(i)]);
                priceArray[i].setText(priceAS[searchResults.get(i)]);
                div_yearArray[i].setText(div_yearAS[searchResults.get(i)] + " / " + div_year_qAS[searchResults.get(i)]);
                div_dateArray[i].setText(ex_div_dateAS[searchResults.get(i)]);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            brand.setLayoutParams(params);

            searchText.setHeight(0);
            searchText.setWidth(0);
            searchText.setVisibility(View.GONE);

            showTable.setText("Отменить поиск");
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(lupa.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            counter--;
        }

    }


    public void cancelSearch() {
        for (int i = 0; i < TableRow.length; i++) {
            company[i].setText(nameAS[i] + ", " + tickerAS[i]);
            capArray[i].setText(capAS[i]);
            priceArray[i].setText(priceAS[i]);
            div_yearArray[i].setText(div_yearAS[i] + " / " + div_year_qAS[i]);
            div_dateArray[i].setText(ex_div_dateAS[i]);
        }
        searchResults.clear();
        isSearching = false;
        indexPage = 1;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getRootView().getWindowToken(), 0);
        return true;
    }


    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public void parserJson() {
        if (conToInternet) {

            String name = null;
            String ticker = null;
            String cap = null;
            String price = null;
            String div_year = null;
            String div_year_q = null;
            String ex_div_date = null;

            try {
                jsonData = dataFromApi.getJSON();
                arrayData = dataFromApi.getCompanyArray();
                jsonDataCompany = dataFromApi.getCompany(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
            mainLayout.setVisibility(View.GONE);
            TextView noConnectText = (TextView) findViewById(R.id.noConnectText);
            noConnectText.setVisibility(View.VISIBLE);
            LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
            TableRow mainRow = (TableRow) findViewById(R.id.main_row);
            LinearLayout futer = (LinearLayout) findViewById(R.id.futer);
            TextView divCalText = (TextView) findViewById(R.id.div_cal_text);
            menu.setVisibility(View.INVISIBLE);
            mainRow.setVisibility(View.INVISIBLE);
            futer.setVisibility(View.INVISIBLE);
            divCalText.setVisibility(View.INVISIBLE);
        }
    }


    public void left_page(View view) {
        if (!isSearching) {
            if (indexPage != 1) {
                indexPage--;
                if (!isSearching) {
                    for (int i = (indexPage - 1) * numberCompanyOnPage; i < indexPage * numberCompanyOnPage; i++) {
                        company[i - ((indexPage - 1) * numberCompanyOnPage)].setText(nameAS[i] + ", " + tickerAS[i]);
                        capArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(capAS[i]);
                        priceArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(priceAS[i]);
                        div_yearArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(div_yearAS[i] + " / " + div_year_qAS[i]);
                        div_dateArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(ex_div_dateAS[i]);
                    }
                } else {
                    for (int i = (indexPage - 1) * numberCompanyOnPage; i < indexPage * numberCompanyOnPage; i++) {
                        company[i - ((indexPage - 1) * numberCompanyOnPage)].setText(nameAS[searchResults.get(i)] + ", " + tickerAS[searchResults.get(i)]);
                        capArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(capAS[searchResults.get(i)]);
                        priceArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(priceAS[searchResults.get(i)]);
                        div_yearArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(div_yearAS[searchResults.get(i)] + " / " + div_year_qAS[searchResults.get(i)]);
                        div_dateArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(ex_div_dateAS[searchResults.get(i)]);
                    }
                }
            }
        }
    }


    public void right_page(View view) {
        if (!isSearching) {
            if (indexPage != 46) {
                indexPage++;
                if (!isSearching) {
                    for (int i = (indexPage - 1) * numberCompanyOnPage; i < indexPage * numberCompanyOnPage; i++) {
                        company[i - ((indexPage - 1) * numberCompanyOnPage)].setText(nameAS[i] + ", " + tickerAS[i]);
                        capArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(capAS[i]);
                        priceArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(priceAS[i]);
                        div_yearArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(div_yearAS[i] + " / " + div_year_qAS[i]);
                        div_dateArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(ex_div_dateAS[i]);
                    }
                } else {
                    for (int i = (indexPage - 1) * numberCompanyOnPage; i < indexPage * numberCompanyOnPage; i++) {
                        company[i - ((indexPage - 1) * numberCompanyOnPage)].setText(nameAS[searchResults.get(i)] + ", " + tickerAS[searchResults.get(i)]);
                        capArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(capAS[searchResults.get(i)]);
                        priceArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(priceAS[searchResults.get(i)]);
                        div_yearArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(div_yearAS[searchResults.get(i)] + " / " + div_year_qAS[searchResults.get(i)]);
                        div_dateArray[i - ((indexPage - 1) * numberCompanyOnPage)].setText(ex_div_dateAS[searchResults.get(i)]);
                    }
                }
            }
        }
    }

}

