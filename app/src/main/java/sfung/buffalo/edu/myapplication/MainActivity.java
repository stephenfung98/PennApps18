package sfung.buffalo.edu.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import static sfung.buffalo.edu.myapplication.MapsActivity.*;

import java.text.NumberFormat;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //change the Lyft section's price
        if (lyftPrice != 0) {
            ((TextView) findViewById(R.id.lyftPrice)).setText(formatter.format(lyftPrice));
        }
        if (lyftPlusPrice != 0) {
            ((TextView) findViewById(R.id.lyftPlusPrice)).setText(formatter.format(lyftPlusPrice));
        }
        if (lyftPremierPrice != 0) {
            ((TextView) findViewById(R.id.lyftPremierPrice)).setText(formatter.format(lyftPremierPrice));
        }
        if (lyftLuxPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxPrice)).setText(formatter.format(lyftLuxPrice));
        }
        if (lyftLuxSUVPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxSUVPrice)).setText(formatter.format(lyftLuxSUVPrice));
        }


        //change the Uber section's price
        if(uberXPrice != 0) {
            ((TextView) findViewById(R.id.uberXPrice)).setText(formatter.format(uberXPrice));
        }

        if(uberXLPrice != 0) {
            ((TextView) findViewById(R.id.uberXLPrice)).setText(formatter.format(uberXLPrice));
        }

        if(uberSelectPrice != 0) {
            ((TextView) findViewById(R.id.uberSelectPrice)).setText(formatter.format(uberSelectPrice));
        }

        if(uberBlackPrice != 0) {
            ((TextView) findViewById(R.id.uberBlackPrice)).setText(formatter.format(uberBlackPrice));
        }

        if(uberSUVPrice != 0) {
            ((TextView) findViewById(R.id.uberSUVPrice)).setText(formatter.format(uberSUVPrice));
        }

        // Compares Uber and Lyft prices and sets colors
        if(uberXPrice<lyftPrice){
            ((TextView) findViewById(R.id.uberXPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.lyftPrice)).setTextColor(Color.RED);
        }else if (uberXPrice>lyftPrice){
            ((TextView) findViewById(R.id.lyftPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.uberXPrice)).setTextColor(Color.RED);
        }
        if(uberXLPrice<lyftPlusPrice){
            ((TextView) findViewById(R.id.uberXLPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.lyftPlusPrice)).setTextColor(Color.RED);
        }else if(uberXLPrice>lyftPlusPrice){
            ((TextView) findViewById(R.id.lyftPlusPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.uberXLPrice)).setTextColor(Color.RED);
        }
        if(uberSelectPrice<lyftPremierPrice){
            ((TextView) findViewById(R.id.uberSelectPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.lyftPremierPrice)).setTextColor(Color.RED);
        }else if(uberSelectPrice>lyftPremierPrice){
            ((TextView) findViewById(R.id.lyftPremierPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.uberSelectPrice)).setTextColor(Color.RED);
        }
        if(uberBlackPrice<lyftLuxPrice){
            ((TextView) findViewById(R.id.uberBlackPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.lyftLuxPrice)).setTextColor(Color.RED);
        }else if(uberBlackPrice>lyftLuxPrice){
            ((TextView) findViewById(R.id.lyftLuxPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.uberBlackPrice)).setTextColor(Color.RED);
        }
        if(uberSUVPrice<lyftLuxSUVPrice){
            ((TextView) findViewById(R.id.uberSUVPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.lyftLuxSUVPrice)).setTextColor(Color.RED);
        }else if(uberSUVPrice>lyftLuxSUVPrice){
            ((TextView) findViewById(R.id.lyftLuxSUVPrice)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.uberSUVPrice)).setTextColor(Color.RED);
        }

    }

}