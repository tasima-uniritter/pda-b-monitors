package br.edu.uniritter.monitors.model;

import java.security.Timestamp;
import java.time.format.DateTimeFormatterBuilder;

public class Reading {
    private String origin;
    private String metric;
    private int value;
    private String timestamp;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Leitura(origin/metric/value/timestamp): "
                + this.origin + " / " + this.metric + " / "
                + this.value + " / " + this.timestamp;
    }
}
