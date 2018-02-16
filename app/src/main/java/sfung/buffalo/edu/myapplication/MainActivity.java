package sfung.buffalo.edu.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsActivity mapsActivity = new MapsActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wait one second to allow the information to come back from AWS
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //change the lyft section's price
        if (mapsActivity.lyftLinePrice != 0) {
            ((TextView) findViewById(R.id.lyftLinePriceTextView)).setText(formatter.format(mapsActivity.lyftLinePrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftLinePriceTextView)).setText("N/A");
        }
        if (mapsActivity.lyftPrice != 0) {
            ((TextView) findViewById(R.id.lyftPriceTextView)).setText(formatter.format(mapsActivity.lyftPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftPriceTextView)).setText("N/A");
        }
        if (mapsActivity.lyftPlusPrice != 0) {
            ((TextView) findViewById(R.id.lyftPlusPriceTextView)).setText(formatter.format(mapsActivity.lyftPlusPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftPlusPriceTextView)).setText("N/A");
        }
        if (mapsActivity.lyftLuxPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxPriceTextView)).setText(formatter.format(mapsActivity.lyftLuxPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftLuxPriceTextView)).setText("N/A");
        }
        if (mapsActivity.lyftLuxSUVPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxSUVPriceTextView)).setText(formatter.format(mapsActivity.lyftLuxSUVPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftLuxSUVPriceTextView)).setText("N/A");
        }

        //change the uber section's price
        if(mapsActivity.uberPoolPrice != 0) {
            ((TextView) findViewById(R.id.uberPoolPriceTextView)).setText(formatter.format(mapsActivity.uberPoolPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberPoolPriceTextView)).setText("N/A");
        }

        if(mapsActivity.uberXPrice != 0) {
            ((TextView) findViewById(R.id.uberXPriceTextView)).setText(formatter.format(mapsActivity.uberXPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberXPriceTextView)).setText("N/A");
        }

        if(mapsActivity.uberXLPrice != 0) {
            ((TextView) findViewById(R.id.uberXLPriceTextView)).setText(formatter.format(mapsActivity.uberXLPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberXLPriceTextView)).setText("N/A");
        }

        if(mapsActivity.uberBlackPrice != 0) {
            ((TextView) findViewById(R.id.uberBlackPriceTextView)).setText(formatter.format(mapsActivity.uberBlackPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberBlackPriceTextView)).setText("N/A");
        }

        if(mapsActivity.uberSUVPrice != 0) {
            ((TextView) findViewById(R.id.uberSUVPriceTextView)).setText(formatter.format(mapsActivity.uberSUVPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberSUVPriceTextView)).setText("N/A");
        }


//        Typeface LyftTypeFace = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
//        TextView lyftL = (TextView) findViewById(R.id.lyftLineTextView);
//        lyftL.setTypeface(LyftTypeFace);
//        TextView lyft = (TextView) findViewById(R.id.lyftTextView);
//        lyft.setTypeface(LyftTypeFace);
//        TextView lyftP = (TextView) findViewById(R.id.lyftPlusTextView);
//        lyftP.setTypeface(LyftTypeFace);
//        TextView lyftLux = (TextView) findViewById(R.id.lyftLuxTextView);
//        lyftLux.setTypeface(LyftTypeFace);
//        TextView lyftS = (TextView) findViewById(R.id.lyftLuxSUVTextView);
//        lyftS.setTypeface(LyftTypeFace);
    }

}