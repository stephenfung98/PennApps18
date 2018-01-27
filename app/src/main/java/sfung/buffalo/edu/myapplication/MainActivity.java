package sfung.buffalo.edu.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends Activity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsActivity mapsActivity = new MapsActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface LyftTypeFace = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.ttf");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        ((TextView) findViewById(R.id.lyftLinePriceTextView)).setText(formatter.format(mapsActivity.lyftLinePrice));
        ((TextView) findViewById(R.id.lyftPriceTextView)).setText(formatter.format(mapsActivity.lyftPrice));
        ((TextView) findViewById(R.id.lyftPlusPriceTextView)).setText(formatter.format(mapsActivity.lyftPlusPrice));
        ((TextView) findViewById(R.id.lyftLuxPriceTextView)).setText(formatter.format(mapsActivity.lyftLuxPrice));
        ((TextView) findViewById(R.id.lyftLuxSUVPriceTextView)).setText(formatter.format(mapsActivity.lyftLuxSUVPrice));

        ((TextView) findViewById(R.id.uberPoolPriceTextView)).setText(formatter.format(mapsActivity.uberPoolPrice));
        ((TextView) findViewById(R.id.uberXPriceTextView)).setText(formatter.format(mapsActivity.uberXPrice));
        ((TextView) findViewById(R.id.uberXLPriceTextView)).setText(formatter.format(mapsActivity.uberXLPrice));
        ((TextView) findViewById(R.id.uberBlackPriceTextView)).setText(formatter.format(mapsActivity.uberBlackPrice));
        ((TextView) findViewById(R.id.uberSUVPriceTextView)).setText(formatter.format(mapsActivity.uberSUVPrice));

    }

}