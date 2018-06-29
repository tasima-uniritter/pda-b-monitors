package br.edu.uniritter.monitors.route;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class QueueConfig {

    private static final String AMQP_SERVICE_HOST = "b-79e24b8a-cd93-48a3-b234-9d75e05e3def-1.mq.us-east-1.amazonaws.com";
    private static final String AMQP_SERVICE_PORT = "5671";
    private static final String AMQP_SERVICE_USERNAME = "tda-monitors-b";

    @Bean
    AMQPConnectionDetails securedAmqpConnection() {

        Dotenv dotenv = Dotenv
                            .configure()
                            .ignoreIfMissing()
                            .directory("./")
                            .load();

        String senhaMQ = dotenv.get("AMQP_SERVICE_PASSWORD");

        if (senhaMQ.length() == 0) {
            throw new IllegalArgumentException("Missing AMQP_SERVICE_PASSWORD environment variable");
        }

        String uri = "amqps://" + AMQP_SERVICE_HOST + ":" + AMQP_SERVICE_PORT;

        return new AMQPConnectionDetails(uri, AMQP_SERVICE_USERNAME, senhaMQ);

        //teste
    }
}