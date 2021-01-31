package xyz.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;

import org.apache.camel.component.jackson.ListJacksonDataFormat;

import xyz.model.HTTPResponseProcessor;
import xyz.model.TrainPOJO;

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

        rest("/api")//Api will be available at localhost:8080/camel/api?train_id=
        .get()
            .route()
            .setProperty("train_id", simple("${header.train_id}")) //Move train_id to property to bypass the request to the digitraffic api
            .log("train_id = ${property.train_id}")
            .removeHeaders("*") //Remove all headers to prevent unwated ones from passing to the digitraffic api http request
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader("Accept-Encoding", constant("gzip"))
            .setHeader("Accept", constant("*/*"))
            //Returns json list with max 1 object
            .toD("https://rata.digitraffic.fi/api/v1/train-locations/latest/${property.train_id}")
            .unmarshal(new ListJacksonDataFormat(TrainPOJO.class))
            .to("direct:choose")
        .endRest();
            
        
        from("direct:choose")
            .choice()
                .when(simple("${body.size()} > 0")) //We got train info for that train
                    .to("direct:createLink")
                .otherwise()//No train info, return 404
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                    .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                    .transform().simple("Cannot find train with id: ${property.train_id}")
        ;
            
        from("direct:createLink")
            .process(new HTTPResponseProcessor()) //Generate maps link
            .setHeader("Location", simple("${body}"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(302))
            .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
            .transform().simple("${property.train_id}")
        ;

    }

}
