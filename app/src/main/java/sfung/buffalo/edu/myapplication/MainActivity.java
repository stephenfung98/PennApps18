package sfung.buffalo.edu.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import static sfung.buffalo.edu.myapplication.MapsActivity.*;

import java.text.NumberFormat;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        MapsActivity mapsActivity = new MapsActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wait one second to allow the information to come back from AWS
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //change the lyft section's price
        if (lyftPrice != 0) {
            ((TextView) findViewById(R.id.lyftPrice)).setText(formatter.format(lyftPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftPrice)).setText("N/A");
        }
        if (lyftPlusPrice != 0) {
            ((TextView) findViewById(R.id.lyftPlusPrice)).setText(formatter.format(lyftPlusPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftPlusPrice)).setText("N/A");
        }
        if (lyftPremierPrice != 0) {
            ((TextView) findViewById(R.id.lyftPremierPrice)).setText(formatter.format(lyftPremierPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftPremierPrice)).setText("N/A");
        }
        if (lyftLuxPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxPrice)).setText(formatter.format(lyftLuxPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftLuxPrice)).setText("N/A");
        }
        if (lyftLuxSUVPrice != 0) {
            ((TextView) findViewById(R.id.lyftLuxSUVPrice)).setText(formatter.format(lyftLuxSUVPrice));
        }
        else {
            ((TextView) findViewById(R.id.lyftLuxSUVPrice)).setText("N/A");
        }


        //change the uber section's price

        if(uberXPrice != 0) {
            ((TextView) findViewById(R.id.uberXPrice)).setText(formatter.format(uberXPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberXPrice)).setText("N/A");
        }

        if(uberXLPrice != 0) {
            ((TextView) findViewById(R.id.uberXLPrice)).setText(formatter.format(uberXLPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberXL)).setText("N/A");
        }

        if(uberSelectPrice != 0) {
            ((TextView) findViewById(R.id.uberSelectPrice)).setText(formatter.format(uberSelectPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberSelectPrice)).setText("N/A");
        }

        if(uberBlackPrice != 0) {
            ((TextView) findViewById(R.id.uberBlackPrice)).setText(formatter.format(uberBlackPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberBlackPrice)).setText("N/A");
        }

        if(uberSUVPrice != 0) {
            ((TextView) findViewById(R.id.uberSUVPrice)).setText(formatter.format(uberSUVPrice));
        }
        else{
            ((TextView) findViewById(R.id.uberSUVPrice)).setText("N/A");
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