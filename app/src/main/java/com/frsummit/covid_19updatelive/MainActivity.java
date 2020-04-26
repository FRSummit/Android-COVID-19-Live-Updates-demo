package com.frsummit.covid_19updatelive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {

    TextView worldConfirm, worldRecovered, worldDeath, bdConfirm, bdRecovered, bdDeath, textView2;
    Button button;
    WebView webView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        worldConfirm = findViewById(R.id.worldConfirm);
        worldRecovered = findViewById(R.id.worldRecovered);
        worldDeath = findViewById(R.id.worldDeath);
        bdConfirm = findViewById(R.id.bdConfirm);
        bdRecovered = findViewById(R.id.bdRecovered);
        bdDeath = findViewById(R.id.bdDeath);
        textView2 = findViewById(R.id.textView2);

        webView = findViewById(R.id.webView);
        imageView = findViewById(R.id.imageView);

        button = findViewById(R.id.button);
        button.setText("Pull Text");

        webView.setWebViewClient(new MyBrowser());
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.loadUrl("https://www.worldometers.info/coronavirus");
        getCountryName();
    }

    public void btnClick(View view) {
        getBodyText();
    }

    private void getBodyText() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    String url = "https://www.google.com/search?sxsrf=ALeKk00y4Zf1BityFDvj5XpZYU_qc4aO0w:1587305896633&q=Coronavirus+Stats&stick=H4sIAAAAAAAAAONgFuLVT9c3NMwySk6OL8zJUULlPmL05hZ4-eOesJTTpDUnrzHacHEFZ-SXu-aVZJZUCulxsUFZKlyCUqg6NRik-LlQhXh2MXF7pCbmlGQElySWFC9iFXTOL8rPSyzLLCotVgCLAQCnsUMMkAAAAA&sxsrf=ALeKk00y4Zf1BityFDvj5XpZYU_qc4aO0w:1587305896633&biw=1920&bih=937";//your website url
                    Document doc = Jsoup.connect(url).get();
//                    System.out.println(doc);

                    Element body = doc.body();
                    System.out.println(body.getElementsByClass("yeRnY sz9i9").get(0));
                    System.out.println(body.getElementsByClass("yeRnY sz9i9").eachText().get(3));
                    System.out.println(body.getElementsByClass("yeRnY sz9i9").eachText().get(0));
                    System.out.println(body.getElementsByClass("GV3Hqc").eachText().get(0));
                    worldConfirm.setText("Worldwide Confirm    : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(3));
                    worldRecovered.setText("Worldwide Recovered  : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(4));
                    worldDeath.setText("Worldwide Death      : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(5));
                    bdConfirm.setText("Bangladesh Confirm   : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(0));
                    bdRecovered.setText("Bangladesh Recovered : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(1));
                    bdDeath.setText("Bangladesh Death     : " + body.getElementsByClass("yeRnY sz9i9").eachText().get(2));
//                    textView2.setText(body.getElementsByClass("yeRnY sz9i9").eachText().get(3));
                    Element body2 = doc.body();
                    System.out.println(body2.getElementsByClass("HsRHde").text());
                    builder.append(body2.getElementsByClass("HsRHde").text());
//                    builder.append(body.text());

                } catch (Exception e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView2.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void getCountryName() {
        String country_name = null;
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        System.out.println(lm);
        System.out.println(geocoder);
//        for(String provider: lm.getAllProviders()) {
//            @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(provider);
//            if(location!=null) {
//                try {
//                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                    if(addresses != null && addresses.size() > 0) {
//                        country_name = addresses.get(0).getCountryName();
//                        break;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Toast.makeText(getApplicationContext(), country_name, Toast.LENGTH_LONG).show();
    }
}
