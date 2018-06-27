package br.edu.uniritter.monitors.processor;

import br.edu.uniritter.monitors.model.Alert;
import br.edu.uniritter.monitors.model.Decision;
import br.edu.uniritter.monitors.model.Reading;
import br.edu.uniritter.monitors.model.Rule;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MonitorProcessor {
    public void startDecision(Exchange exchange) {
        Reading reading = exchange.getIn().getBody(Reading.class);
        Decision decision = new Decision(reading);
        exchange.getOut().setBody(decision);
    }

    public void getRule(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();

        // TODO: find if there is a rule for reading.origin and reading.metric
        // Rule rule = null;
        Rule rule = new Rule();
        rule.setMetric("memory");
        rule.setOrigin("server1");
        rule.setRule(">=");
        rule.setThreshold(30000);

        exchange.getOut().setHeader("hasRule", (rule != null));
        decision.setRule(rule);

        exchange.getOut().setBody(decision);
    }

    public void checkRule(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();
        Rule rule = decision.getRule();

        boolean toAlert = false;
        switch (rule.getRule()) {
            case ">":
                toAlert = reading.getValue() > rule.getThreshold();
                break;
            case ">=":
                toAlert = reading.getValue() >= rule.getThreshold();
                break;
            case "<":
                toAlert = reading.getValue() < rule.getThreshold();
                break;
            case "<=":
                toAlert = reading.getValue() <= rule.getThreshold();
                break;
            case "==":
                toAlert = reading.getValue() == rule.getThreshold();
                break;
            case "!=":
                toAlert = reading.getValue() != rule.getThreshold();
                break;
        }
        exchange.getOut().setHeader("toAlert", toAlert);

        exchange.getOut().setBody(decision);
    }

    public void makeAlert(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();
        Rule rule = decision.getRule();

        Alert alert = new Alert();
        alert.setOrigin(reading.getOrigin());
        alert.setMetric(reading.getMetric());
        alert.setValue(reading.getValue());
        alert.setTimestamp(reading.getTimestamp());
        alert.setRule(rule.getRule());
        alert.setThreshold(rule.getThreshold());

        decision.setAlert(alert);

        exchange.getOut().setBody(decision);
    }
}
