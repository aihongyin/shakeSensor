package edu.sjsu.shakesensor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {


    SensorManager sm;
    View view;
    TextView xview;
    TextView yview;
    TextView zview;
    private boolean color = false;
    private float currentValue;
    private float previousValue;
    private float changeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.BLUE);
        xview=(TextView) findViewById(R.id.textView2);
        yview=(TextView) findViewById(R.id.textView3);
        zview=(TextView) findViewById(R.id.textView4);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];


            xview.setText("x value: " + x);
            yview.setText("y value: " + y);
            zview.setText("z value: " + z);


            currentValue = (float) Math.sqrt((x*x + y*y + z*z));
            changeValue = Math.abs(currentValue-previousValue);
            previousValue = currentValue;

            if(changeValue>0) {

                if(color){
                    view.setBackgroundColor(Color.RED);
                    Toast.makeText(this, "Don't shake me!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    view.setBackgroundColor(Color.BLUE);
                    // lastUpdate = actualTime;

                }
                color = !color;

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        sm.registerListener(this, sm.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }



}