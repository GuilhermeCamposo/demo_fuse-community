package fuse.community.message;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 * 
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Value("${sleep.time}")
    private long sleepTime;

    int messageCounter = 0;

    @Override
    public void configure() throws Exception {
        
        timerRoute();
        consumerRoute();
    }

    private void restConfig(){
        restConfiguration()
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Post Message REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiProperty("base.path", "camel/")
                .apiProperty("api.path", "/")
                .apiProperty("host", "")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);
    }

    private void restRoute(){
        rest("/message").description("Post message to queue")
                .post()
                .route().routeId("message-api")
                .inOnly("amq:test.queue")
                .log("message sent to queue. OK")
                .setBody(constant("message sent"));
    }

    private void timerRoute(){
        from("timer://myTimer")
                .routeId("message-sender")
                .process(exchange -> {
                    messageCounter++;
                    exchange.getIn().setBody("message number ("+ messageCounter + ")");
                })
                .to("amq:test.queue")
                .log("message sent");
    }

    private void consumerRoute(){
        from("amq:test.queue").routeId("jms-route")
                .transacted()
                .log("got message")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Thread.sleep(sleepTime);
                    }
                })
                .to("amq:test.final.queue")
                .log("message moved");
    }

}