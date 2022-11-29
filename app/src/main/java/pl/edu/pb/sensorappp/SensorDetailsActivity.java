package pl.edu.pb.sensorappp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {

    public static final String EXTRA_SENSOR_TYPE_PARAMETER = "EXTRA_SENSOR_TYPE";

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView sensorNameTextView;
    private TextView sensorValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        sensorNameTextView = findViewById(R.id.details_sensor_name);
        sensorValueTextView = findViewById(R.id.details_sensor_value);

        int sensorType = getIntent().getIntExtra(EXTRA_SENSOR_TYPE_PARAMETER, Sensor.TYPE_LIGHT);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);

        if(sensor == null){
            sensorNameTextView.setText(R.string.missing_sensor);
        }else {
            sensorNameTextView.setText(sensor.getName());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float value = sensorEvent.values[0];
        switch (type){
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sensorValueTextView.setText(String.valueOf(value));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
