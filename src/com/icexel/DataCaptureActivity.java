package com.icexel;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import sample.ble.sensortag.R;
import sample.ble.sensortag.fusion.sensors.AndroidSensorManager;
import sample.ble.sensortag.fusion.sensors.ISensor;
import sample.ble.sensortag.fusion.sensors.ISensorManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Allow the user to toggle data capture on and off
 */
public class DataCaptureActivity extends Activity {
    private ISensorManager sensorManager;
    DataCaptureListener dataCaptureListener;
    List<DataSession> capturedDataSessions = new ArrayList<DataSession>();


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
        dataCaptureListener.stopRecording();
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.disable();
        dataCaptureListener.stopRecording();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataCaptureListener.clearData();
        capturedDataSessions = null;
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
        if (!dataCaptureListener.isRecording()) {
            dataCaptureListener.startRecording(new DataSession());
            showStop(button);
        } else {
            dataCaptureListener.stopRecording();
            showStart(button);
            DataSession capturedDataSession = dataCaptureListener.getDataSession();
            capturedDataSessions.add(capturedDataSession);
            showCapturedData(capturedDataSession, (View) view.getParent());
            try {
                saveCapturedData(capturedDataSession);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void showStart(Button button) {
        button.setText(R.string.button_start);
        button.setBackgroundResource(R.drawable.control_button_start);
    }

    private void showStop(Button button) {
        button.setText(R.string.button_stop);
        button.setBackgroundResource(R.drawable.control_button_stop);

    }

    private void saveCapturedData(DataSession capturedDataSession) throws IOException {
        if(isExternalStorageWritable()){

            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/iceXel");
            if(!dir.exists()){
                dir.mkdir();
            }
            File logFile = new File(dir, String.valueOf((new Date()).getTime()) + ".txt");
            logFile.createNewFile();
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(capturedDataSession.toString());
            buf.close();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void showCapturedData(DataSession dataSession, View actionView) {
        if(dataSession != null){
            TextView captureLog = (TextView) actionView.findViewById(R.id.captureLog);
            captureLog.setText(dataSession.tail(20));
        }
    }
}
