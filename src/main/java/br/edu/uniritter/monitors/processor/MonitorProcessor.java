package br.edu.uniritter.monitors.processor;

import br.edu.uniritter.monitors.model.Reading;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MonitorProcessor {
    public void process(Exchange exchange) {
        System.out.println("CHEGOU AQUI NESSA PORRA: " + exchange.getIn());

        Reading reading = exchange.getIn().getBody(Reading.class);
        System.out.println(reading.toString());
//        exchange.getOut().setHeader("shouldAlert", true);

//        Reading output = new Reading(
//                incomeMessage.getOrigin(),
//                incomeMessage.getMetric(),
//                incomeMessage.getValue(),
//                incomeMessage.getTimestamp(),
//                threshold.getRule(),
//                threshold.getThreshold()
//        );
        reading.setValue(123);

        exchange.getOut().setBody(reading);
    }

    private void getThreshold() {

    }

    private void setShouldAlert() {

    }
}
