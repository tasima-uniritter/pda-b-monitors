package br.edu.uniritter.monitors.processor;

import br.edu.uniritter.monitors.model.Alert;
import br.edu.uniritter.monitors.model.Decision;
import br.edu.uniritter.monitors.model.Reading;
import br.edu.uniritter.monitors.model.Rule;
import br.edu.uniritter.monitors.service.RuleService;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MonitorProcessor {
    @Autowired
    private RuleService ruleService;

    public void startDecision(Exchange exchange) {
        Reading reading = exchange.getIn().getBody(Reading.class);
        Decision decision = new Decision(reading);
        exchange.getOut().setBody(decision);
    }

    public void getRule(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();

        List<Rule> rules = ruleService.getRulesForReading(reading);

        exchange.getOut().setHeader("hasRule", (!rules.isEmpty()));
        decision.setRules(rules);

        exchange.getOut().setBody(decision);
    }

    public void checkRules(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();
        List<Rule> rules = decision.getRules();

        List<Rule> alertRules = new ArrayList<>();
        for (Rule rule : rules) {
            switch (rule.getRule()) {
                case ">":
                    if (reading.getValue() > rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
                case ">=":
                    if (reading.getValue() >= rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
                case "<":
                    if (reading.getValue() < rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
                case "<=":
                    if (reading.getValue() <= rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
                case "==":
                    if (reading.getValue() == rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
                case "!=":
                    if (reading.getValue() != rule.getThreshold()) {
                        alertRules.add(rule);
                    }
                    break;
            }
        }

        exchange.getOut().setHeader("toAlert", !alertRules.isEmpty());
        decision.setAlertRules(alertRules);

        exchange.getOut().setBody(decision);
    }

    public void makeAlerts(Exchange exchange) {
        Decision decision = exchange.getIn().getBody(Decision.class);

        Reading reading = decision.getReading();
        List<Rule> rules = decision.getAlertRules();

        List<Alert> alerts = new ArrayList<>();
        for (Rule rule : rules) {
            Alert alert = new Alert();

            alert.setOrigin(reading.getOrigin());
            alert.setMetric(reading.getMetric());
            alert.setValue(reading.getValue());
            alert.setTimestamp(reading.getTimestamp());
            alert.setRule(rule.getRule());
            alert.setThreshold(rule.getThreshold());

            alerts.add(alert);
        }

        exchange.getOut().setBody(alerts);
    }
}
