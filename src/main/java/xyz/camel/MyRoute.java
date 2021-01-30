package xyz.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;

import org.apache.camel.component.jackson.ListJacksonDataFormat;
//import org.apache.camel.component.jackson.JacksonDataFormat;

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

        rest("/api")//Log any get requests
        .get()
            .route()
            .setProperty("train_id", simple("${header.train_id}"))//Move train_id to property
            .log("Train_id ${property.train_id}")
            .removeHeaders("*")//Remove all headers
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader("Accept-Encoding", constant("gzip"))
            .setHeader("Accept", constant("*/*"))
            .log("Sending request to train api!")
            //Returns json list with max 1 object
            .toD("https://rata.digitraffic.fi/api/v1/train-locations/latest/${property.train_id}")
            
            .unmarshal(new ListJacksonDataFormat(TrainPOJO.class))
            
            .to("direct:createLink")
        .endRest();
            
        
        from("direct:createLink")
            .process(new HTTPResponseProcessor())
            .log("Train id is: ${property.train_id}")
            .setHeader("Location", simple("${body}"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(302))
            .transform().simple("${property.train_id}");
            

    }

}
