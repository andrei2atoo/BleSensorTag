package com.icexel;


import java.util.Date;

/**
 * Created by andrei on 2014-08-29.
 */
public class SensorDataElement {
    Date timestamp;
    int dataType;
    float[] values;

    public SensorDataElement(int dataType, float[] values) {
        this.timestamp = new Date();
        this.dataType = dataType;
        this.values = values;
    }
}
