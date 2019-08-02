package adkl.smarthomevc;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    protected final float TEMPERATURE_DEVIATION = 0.5F;
    protected float currentTemperature = -999;
    protected FirebaseDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachFirebaseDBListener();
        attachLiveFirebaseDBUploader();
    }

    protected void attachFirebaseDBListener() {
        // listen for updates on ref "tasks"
        this.db = FirebaseDatabase.getInstance();
        final DatabaseReference tasksReference = db.getReference("users/user-id-1234/tasks");
        tasksReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot task, String s) {
                Log.i("firebase", String.format("task %s received", task.getKey()));
                // process a task
                Object completedTaskPayload = TaskProcessor.processTask(task);
                tasksReference.child(task.getKey()).removeValue();
                final DatabaseReference completedTasksRef = db.getReference(String.format("users/user-id-1234/completed-tasks/%s", task.getKey()));
                completedTasksRef.setValue(completedTaskPayload);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) {  }
        });
        Log.i("firebase","Task processor attached");
    }

    protected void attachLiveFirebaseDBUploader() {
        final DatabaseReference temperatureRef = db.getReference("users/user-id-1234/temperature");
        final DatabaseReference humidityRef = db.getReference("users/user-id-1234/humidity");
        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerDynamicSensorCallback(new SensorManager.DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                Log.i("firebase", String.format("Sensor %s connected", sensor.getStringType()));
                if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    sensorManager.registerListener(
                            new SensorEventListener() {
                                @Override
                                public void onSensorChanged(SensorEvent event) {
                                    float roundedTemperature = Float.parseFloat(String.format("%.1f", event.values[0]));
                                    if (Math.abs(currentTemperature - roundedTemperature) > TEMPERATURE_DEVIATION) {
                                        temperatureRef.setValue(roundedTemperature);
                                        currentTemperature = roundedTemperature;
                                    }
                                    Log.i("temperature-sensor", "sensor changed: " + roundedTemperature);
                                }
                                @Override
                                public void onAccuracyChanged(Sensor sensor, int accuracy) { }
                            }, sensor, SensorManager.SENSOR_DELAY_UI);
                }
                else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    sensorManager.registerListener(
                            new SensorEventListener() {
                                @Override
                                public void onSensorChanged(SensorEvent event) {
                                    float humidity = event.values[0];
                                    Log.i("humidity-sensor", "sensor changed: " + humidity);
                                    humidityRef.setValue(humidity);
                                }
                                @Override
                                public void onAccuracyChanged(Sensor sensor, int accuracy) { }
                            }, sensor, SensorManager.SENSOR_DELAY_FASTEST);
                }
            }
        });

        // register sensors
        try {
            Bmx280SensorDriver  temperatureSensorDriver = new Bmx280SensorDriver("I2C1");
            temperatureSensorDriver.registerTemperatureSensor();
            temperatureSensorDriver.registerHumiditySensor();
        } catch (IOException e) {
            Log.e("sensors", "Error configuring sensor", e);
        }
    }
}
