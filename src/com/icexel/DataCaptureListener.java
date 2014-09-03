package com.icexel;

import sample.ble.sensortag.fusion.sensors.ISensorManager;

public class DataCaptureListener implements ISensorManager.SensorEventListener {
    private DataSession dataSession;
    private boolean recording;


    @Override
    public void onSensorChanged(int sensorType, float[] values) {
        if (recording && dataSession != null) {
            dataSession.recordData(sensorType, values);
        }

        /*switch(sensorType) {
            case ISensor.TYPE_ACCELEROMETER:
                sensorFusion.onAccDataUpdate(values);
                dataSession.recordData();
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
        dataSession = null;
    }

    public boolean isRecording() {
        return recording;
    }

    public void stopRecording() {
        recording = false;
    }

    public void startRecording(DataSession dataSession){
        this.dataSession = dataSession;
        recording = true;
    }

    public DataSession getDataSession() {
        return dataSession;
    }
}
