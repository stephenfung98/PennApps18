package sfung.buffalo.edu.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsActivity mapsActivity = new MapsActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface LyftTypeFace = Typeface.createFromAsset(getAssets(), "Montserrat-Regular.ttf");
        TextView LyftTextView = findViewById(R.id.lyftTextView);
        LyftTextView.setTypeface(LyftTypeFace);
//        ((TextView) findViewById(R.id.lyftLinePriceTextView)).setText(Double.toString(mapsActivity.));

    }

}
