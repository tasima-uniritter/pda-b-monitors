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
                .bean(MonitorProcessor.class, "process")
                .choice()
                    .when(simple("${header.shouldAlert} == true"))
                        .marshal().json(JsonLibrary.Gson, true)
                        .to("amqp:queue:b-monitor-alerts")
                        .log("MANDOU PRA OUTRA FILA!!!!!!!!!!!!!1!!!!!")
                    .otherwise()
                        .log("Não conseguiu enviar pra outra fila.")
                .end();
    }
}
