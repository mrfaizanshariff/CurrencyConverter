package com.aelinstudios.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {
   private EditText editText;
    private  TextView textView;
    private  Button euro,dollar,pound,yen,dinar,bitcoin,aussy,canadian,rial;
    private double us_dollar,euro_val,pound_value,yen_value,dinar_value,bitcoin_val,aussy_val,can_val,rial_val;
    private RequestQueue requestQueue;
    String url = "https://v6.exchangerate-api.com/v6/API_KEY/latest/INR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue=VolleySingleton.getInstance(this).getRequestQueue();
        editText = findViewById(R.id.edittext);
        textView = findViewById(R.id.textView);
        euro = findViewById(R.id.euro);
        pound = findViewById(R.id.pound);
        yen = findViewById(R.id.yen);
        dollar = findViewById(R.id.usdollar);
        dinar = findViewById(R.id.dinar);
        bitcoin = findViewById(R.id.bitcoin);
        aussy = findViewById(R.id.australian);
        rial = findViewById(R.id.rial);
        canadian = findViewById(R.id.canadian);
        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                euroClick();
            }
        });
        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poundClick();
            }
        });
        yen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yenCick();
            }
        });
        dollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dollarClick();
            }
        });
        dinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dinarClick();
            }
        });
        rial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rialClick();
            }
        });
        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitcoinClick();
            }
        });
        aussy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                australianClick();
            }
        });
        canadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canadianClick();
            }
        });
    }

    public void sendApiRequest() {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject= null;
                        try {
                            jsonObject=response.getJSONObject("conversion_rates");
                            double usd = jsonObject.getDouble("USD");
                            double cad = jsonObject.getDouble("CAD");
                            double aud = jsonObject.getDouble("AUD");
                            double eur = jsonObject.getDouble("EUR");
                            double pound = jsonObject.getDouble("GBP");
                            double saudi_riyal = jsonObject.getDouble("SAR");
                            double dinar = jsonObject.getDouble("AED");
                            double yen = jsonObject.getDouble("JPY");
                            us_dollar=usd;
                            can_val=cad;
                            aussy_val=aud;
                            euro_val=eur;
                            pound_value=pound;
                            rial_val=saudi_riyal;
                            dinar_value=dinar;
                            yen_value=yen;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                });

        // api request for bitcoin
        String bitcoin_url="https://api.coindesk.com/v1/bpi/currentprice/INR.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, bitcoin_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject=null;
                        try {
                            jsonObject=response.getJSONObject("INR");
                            double bitcoin = jsonObject.getDouble("rate");
                            bitcoin_val=bitcoin;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

      requestQueue.add(request);
      requestQueue.add(jsonObjectRequest);
    }

    public void dollarClick(){
        sendApiRequest();
        String z = editText.getText().toString();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty User Input");
        else{
            Double input,res;
            input = Double.parseDouble(z);
            Formatter formatter= new Formatter();// this was declared but not used by hitesh sir
            //multiplying with the value recived from the API
            res = input *us_dollar;


//            DecimalFormat numberFormat = new DecimalFormat("#.00");// this method was used in the course vdo
//            textView.setText(""+numberFormat.format(res));
            textView.setText(""+formatter.format("%.2f $",res));// to use the Formatter class i implemented this on my own by studying the documentation(same results)
//            textView.setText(""+res); here it can take any number of decimal points


        }
    }
    public void euroClick(){
        sendApiRequest();
        String z = editText.getText().toString();
        Formatter formatter=new Formatter();
//        DecimalFormat numberformat = new DecimalFormat("#.00");

        if(TextUtils.isEmpty(z))
            editText.setError("Empty user input");
        else {
            Double inp,res;
            inp=Double.parseDouble(z);
            res= inp *euro_val;
            textView.setText(""+formatter.format("%.2f €",res));
        }
    }
    public void poundClick(){
        sendApiRequest();
        String z = editText.getText().toString();
        Formatter formatter=new Formatter();
//        DecimalFormat format= new DecimalFormat("#.00");
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else{
            Double inp,res;
            inp = Double.parseDouble(z);
            res = inp * pound_value;
//            textView.setText(""+format.format(res));
            textView.setText(""+formatter.format("%.2f £",res));
        }
    }
    public void yenCick(){
        sendApiRequest();
        String z = editText.getText().toString();
        Formatter formatter=new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else{
            Double inp,res;
            inp=Double.parseDouble(z);
            res=inp*yen_value;
            textView.setText(""+formatter.format("%.2f ¥",res));

        }
    }
    public void dinarClick(){
        sendApiRequest();
        String z = editText.getText().toString();
        Formatter formatter= new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else{
            Double i,r;
            i=Double.parseDouble(z);
            r=i*dinar_value;
            textView.setText(""+formatter.format("%.2f د.ك",r));
        }
    }
    public void rialClick(){
        sendApiRequest();
        String z=editText.getText().toString();
        Formatter formatter=new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else {
            Double i,r;
            i=Double.parseDouble(z);
            r=i*rial_val;
            textView.setText(""+formatter.format("%.2f ﷼",r));
        }
    }
    public void bitcoinClick(){
        String z=editText.getText().toString();
        Formatter formatter= new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else{
            Double i,r;
            i=Double.parseDouble(z);
            r=i*0.0000012;
            textView.setText(""+formatter.format("%.2f ₿",r));
        }
    }
    public void australianClick(){
        sendApiRequest();
        String z=editText.getText().toString();
        Formatter formatter= new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty input");
        else {
            Double i,r;
            i=Double.parseDouble(z);
            r=i*aussy_val;
            textView.setText(""+formatter.format("%.2f AU$",r));
        }
    }
    public void canadianClick(){
        sendApiRequest();
        String z=editText.getText().toString();
        Formatter formatter=new Formatter();
        if(TextUtils.isEmpty(z))
            editText.setError("Empty Input");
        else {
            Double i,r;
            i=Double.parseDouble(z);
            r=i*can_val;
            textView.setText(""+formatter.format("%.2f CAN$",r));
        }
    }

}
