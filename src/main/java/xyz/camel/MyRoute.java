package xyz.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.ListJacksonDataFormat;

import xyz.model.HTTPResponseProcessor;
import xyz.model.TrainPOJO;
/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() {

        /* https://rata.digitraffic.fi/api/v1/train-locations/latest/
        * Exercise: route train location data from digitraffic.fi and ping one location in google maps
        * Also create CXF-rest api for triggering route
        */

        restConfiguration()//Bind the api servlet to the localhost port 8080
            .component("servlet")
            .bindingMode(RestBindingMode.auto);

        /*rest("/api")//Log any get requests
            .get()
            .route().to("log:DEBUG?showBody=true&showHeaders=true");*/



        //from("timer://once?delay=1000&repeatCount=1")
        //from("timer://periodically?period=1000&fixedRate=true")
        rest("/api")//Log any get requests
        .get()
            .route()
            .log("Sending request by api trigger!")
            .setHeader(Exchange.HTTP_METHOD, simple("GET"))
            .setHeader("Accept-Encoding", constant("gzip"))
            .setHeader("Accept", constant("*/*"))
            .to("https://rata.digitraffic.fi/api/v1/train-locations/latest/")
            .unmarshal(new ListJacksonDataFormat(TrainPOJO.class))
            //TODO filter out unnecessary stuff and get urlquerystringparameter from request and return maps link to the location of the train
            //.to("mock:marshalledObject");
            .process(new HTTPResponseProcessor());
    }

}
