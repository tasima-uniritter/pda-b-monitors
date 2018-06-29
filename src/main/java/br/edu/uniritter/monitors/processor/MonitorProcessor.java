package br.edu.uniritter.monitors.processor;

import br.edu.uniritter.monitors.model.*;
import br.edu.uniritter.monitors.service.HeartbeatService;
import br.edu.uniritter.monitors.service.RuleService;
import br.edu.uniritter.monitors.service.TimeoutService;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MonitorProcessor {
    @Autowired
    private RuleService ruleService;
    @Autowired
    private HeartbeatService heartbeatService;
    @Autowired
    private TimeoutService timeoutService;

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

    public void getHeartbeats(Exchange exchange) {
        List<Timeout> timeouts = timeoutService.getAll();

        List<Alert> alerts = new ArrayList<>();
        for (Timeout timeout : timeouts) {
            Heartbeat heartbeat = heartbeatService.get(timeout.getOrigin(), timeout.getMetric());

            Calendar now = Calendar.getInstance();
            if ((heartbeat.getLastReading() + timeout.getValueInMillis()) <= now.getTimeInMillis()) {
                Alert alert = new Alert();

                alert.setOrigin(timeout.getOrigin());
                alert.setMetric(timeout.getMetric());
                alert.setRule("timeout");
                alert.setThreshold(timeout.getValue());
                alert.setValue(now.getTimeInMillis() - heartbeat.getLastReading());
                alert.setTimestamp(now.toString());

                alerts.add(alert);
            }
        }

        exchange.getOut().setHeader("toAlert", !alerts.isEmpty());
        exchange.getOut().setBody(alerts);
    }
}
