package com.example.sensormenuapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    int i =0,j=0;
    List sensors = new ArrayList();
    List sensors2 = new ArrayList();
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_NORMAL);
        db = new DatabaseHandler(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.acclerometer) {
            i = 0;
            Toast.makeText(getApplicationContext(), "Accelerometer is selected", Toast.LENGTH_SHORT).show();
            populateWIthAcc();
            db.deleteAccelerometers();
            return false;
        }
        if (id == R.id.gyroscope) {

            j = 0;
            Toast.makeText(getApplicationContext(), "Gyroscope is selected", Toast.LENGTH_SHORT).show();
            populateWithGyro();
            db.deleteGyroscopes();
            return false;
        }


        return super.onOptionsItemSelected(item);
    }
    public void populateWIthAcc(){
        TextView  tv = (TextView)findViewById(R.id.sensorName);
        tv.setText("Acclerometer Data...");
        ListView lv = (ListView) findViewById(R.id.sensors);
        List<Accelerometer> accelerometers = db.getAllAccelerometers();
        List<String> populatingList = new ArrayList<String>();
        for(int i = 0; i<accelerometers.size(); i++){

            String s = accelerometers.get(i).getValue1()+"\t"+accelerometers.get(i).getValue2()+"\t"+accelerometers.get(i).getValue2();
            populatingList.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,populatingList);
        lv.setAdapter(adapter);

    }
    public void populateWithGyro(){
        TextView  tv = (TextView)findViewById(R.id.sensorName);
        tv.setText("Gyroscope Data coming from Database...");
        ListView lv = (ListView) findViewById(R.id.sensors);
        List<Gyroscope> gyroscopes = db.getAllGyroscopes();
        List<String> populatingList = new ArrayList<String>();
        for(int i = 0; i<gyroscopes.size(); i++){

            String s = gyroscopes.get(i).getValue1()+"\t"+gyroscopes.get(i).getValue2()+"\t"+gyroscopes.get(i).getValue2();
            populatingList.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,populatingList);
        lv.setAdapter(adapter);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;


        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            while (i<100){
                Accelerometer acclerometer = new Accelerometer(sensorEvent.values[0]+"\t",sensorEvent.values[1]+"\t",sensorEvent.values[2]+"");
                db.addAccelerometer(acclerometer);
                i++;
            }
        }
        if(mySensor.getType()== Sensor.TYPE_GYROSCOPE){
            while (j<100){

                Gyroscope gyroscope = new Gyroscope(sensorEvent.values[0]+"\t",sensorEvent.values[1]+"\t",sensorEvent.values[2]+"");
                db.addGyroscope(gyroscope);
                j++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
