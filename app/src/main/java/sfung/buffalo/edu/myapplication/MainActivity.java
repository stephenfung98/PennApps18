package sfung.buffalo.edu.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends MapsActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Typeface LyftTypeFace = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.ttf");
//        TextView LyftTextView = (TextView) findViewById(R.id.lyftLinetextView);
//        LyftTextView.setTypeface(LyftTypeFace);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.lyftLinetextView)).setText(Double.toString(totalPrice));

    }

}
