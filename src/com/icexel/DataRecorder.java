package com.icexel;

import sample.ble.sensortag.fusion.sensors.ISensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei on 2014-08-29.
 */
public class DataRecorder {
    List<SensorDataElement> data = new ArrayList<SensorDataElement>();

    public void recordData(int sensorType, float[] values) {
        data.add(new SensorDataElement(sensorType, values));
    }
}
