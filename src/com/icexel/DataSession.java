package com.icexel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by andrei on 2014-08-29.
 */
public class DataSession {
    Date creationDate;
    List<SensorDataElement> data = new ArrayList<SensorDataElement>();

    public DataSession() {
        this.creationDate = new Date();
    }

    public void recordData(int sensorType, float[] values) {
        data.add(new SensorDataElement(sensorType, values));
    }

    public String toString(List<SensorDataElement> data) {
        StringBuilder sb = new StringBuilder();
        for (SensorDataElement dataElement : data) {
            sb.append(dataElement.getTimestamp().getTime());
            sb.append(":");
            sb.append(dataElement.dataType);
            sb.append(":");
            sb.append(Arrays.toString(dataElement.getValues()));
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(data);
    }

    public String tail(int n){
        return toString(data.subList(data.size() - n, data.size()));
    }
}
