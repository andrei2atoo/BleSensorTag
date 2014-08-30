package com.icexel;

import sample.ble.sensortag.fusion.sensors.ISensorManager;

public class DataCaptureListener implements ISensorManager.SensorEventListener {
    private DataRecorder dataStore = new DataRecorder();


    @Override
    public void onSensorChanged(int sensorType, float[] values) {
        dataStore.recordData(sensorType, values);

        /*switch(sensorType) {
            case ISensor.TYPE_ACCELEROMETER:
                sensorFusion.onAccDataUpdate(values);
                dataStore.recordData();
                break;

            case ISensor.TYPE_GYROSCOPE:
                sensorFusion.onGyroDataUpdate(values);
                break;

            case ISensor.TYPE_MAGNETIC_FIELD:
                sensorFusion.onMagDataUpdate(values);
                break;
        }*/
    }

    public void clearData() {
        dataStore = null;
    }
}
