package br.edu.uniritter.monitors.model;

import javax.persistence.*;

@Entity
@Table(name = "RULE")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String metric;

    @Column(nullable = false)
    private int threshold;

    @Column(nullable = false)
    private String rule;

    // control fields

    @Column(nullable = false)
    private int timeout;

    @Column(nullable = false)
    private int lastReading;

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

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getLastReading() {
        return lastReading;
    }

    public void setLastReading(Integer lastReading) {
        this.lastReading = lastReading;
    }
}
