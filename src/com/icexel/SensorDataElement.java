package com.icexel;


import java.util.Arrays;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public int getDataType() {
        return dataType;
    }

    public float[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "SensorDataElement{" +
                "timestamp=" + timestamp +
                ", dataType=" + dataType +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
