package br.edu.uniritter.monitors.model;

import javax.persistence.*;

@Entity
@Table(name = "TIMEOUT")
public class Timeout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String metric;

    @Column(nullable = false)
    private long value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValueInMillis() {
        return value * 1000;
    }
}
