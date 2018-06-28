package br.edu.uniritter.monitors.model;

import java.util.ArrayList;
import java.util.List;

public class Decision {
    private Reading reading;
    private List<Rule> rules;
    private List<Rule> alertRules = new ArrayList<>();

    public Decision(Reading reading) {
        this.reading = reading;
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getAlertRules() {
        return alertRules;
    }

    public void setAlertRules(List<Rule> alertRules) {
        this.alertRules = alertRules;
    }
}
