package br.edu.uniritter.monitors.route;

import br.edu.uniritter.monitors.model.Reading;
import br.edu.uniritter.monitors.processor.MonitorProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class MonitorRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("amqp:queue:b-metrics-monitor")
                .log("Mensagem recebida")
                .unmarshal().json(JsonLibrary.Gson, Reading.class)
                .bean(MonitorProcessor.class, "startDecision")
                .bean(MonitorProcessor.class, "getRule")
                .choice()
                    .when(simple("${header.hasRule} == true"))
                        .log("com regra")
                        .bean(MonitorProcessor.class, "checkRules")
                        .choice()
                            .when(simple("${header.toAlert} == true"))
                                .log("alertar")
                                .bean(MonitorProcessor.class, "makeAlerts")
                                .split(body())
                                .marshal().json(JsonLibrary.Gson, true)
                                .log("enviando alerta")
                                .to("amqp:queue:b-monitor-alerts")
                            // can't use with split for some reason...
                            // .otherwise()
                            // .log("não alertar")
                        .endChoice()
                    .otherwise()
                        .log("sem regra")
                .end();
    }
}
