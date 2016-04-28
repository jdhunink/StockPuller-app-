package org.somecompany.stockreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceView = (TextView) findViewById(R.id.stock_price);
        ParseYahooTask task = new ParseYahooTask();
        task.execute("AAPL", "", "");
    }

    public static String readURL(String address)
    {
        String result = "";
        try {
            URL url = new URL(address);
            result = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + address);
        }

        return result;
    }

  public static String getStockInfo(String symbol){
      String infos = readURL("http://104.131.82.149:7575/q?s=" + symbol);
      return infos;
  }

    //parsing a website in the background
    private class ParseYahooTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String price = getStockInfo(params[0]);
            return price;
        }

        protected void onPostExecute(String price) {
            //set the price field here
            priceView.setText(price);
        }
    }
}
