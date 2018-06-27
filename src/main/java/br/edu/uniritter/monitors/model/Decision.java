package br.edu.uniritter.monitors.model;

public class Decision {
    private Reading reading;
    private Rule rule;
    private Alert alert;

    public Decision(Reading reading) {
        this.reading = reading;
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
