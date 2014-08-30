package com.icexel;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import sample.ble.sensortag.R;
import sample.ble.sensortag.fusion.SensorFusionFragment;
import sample.ble.sensortag.fusion.sensors.AndroidSensorManager;
import sample.ble.sensortag.fusion.sensors.ISensor;
import sample.ble.sensortag.fusion.sensors.ISensorManager;

/**
 * Allow the user to toggle data capture on and off
 */
public class DataCaptureActivity extends Activity {
    private ISensorManager sensorManager;
    DataCaptureListener dataCaptureListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.action_capture_data);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        dataCaptureListener = new DataCaptureListener();

        setContentView(R.layout.data_capture_activity);
        sensorManager = new AndroidSensorManager(this);
        sensorManager.setListener(dataCaptureListener);


    }


    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.enable();
        sensorManager.registerSensor(ISensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerSensor(ISensor.TYPE_ACCELEROMETER);
        sensorManager.registerSensor(ISensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.disable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataCaptureListener.clearData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        final MenuItem lockOrientationItem = menu.findItem(R.id.menu_lock_orientaion);
//        lockOrientation(lockOrientationItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_lock_orientaion:
                item.setChecked(!item.isChecked());
                lockOrientation(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void lockOrientation(MenuItem item) {
        if (item.isChecked()) {
            item.setIcon(R.drawable.ic_action_lock_orientaion_off);
            item.setTitle(R.string.menu_lock_orientation);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        } else {
            item.setIcon(R.drawable.ic_action_lock_orientaion_on);
            item.setTitle(R.string.menu_unlock_orientation);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    public void toggleCapture(View view){
        Button button = (Button) view;
        if(button.getText().toString().equals(R.string.button_start)){
            button.setText(R.string.button_stop);
        } else {
            button.setText(R.string.button_start);
        }

    }
}
