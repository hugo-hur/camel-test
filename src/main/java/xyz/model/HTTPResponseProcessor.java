package xyz.model;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import xyz.model.TrainPOJO;
import xyz.model.LocationPOJO;
import java.util.List;
import java.util.ArrayList;



public class HTTPResponseProcessor implements Processor {


    
    
    public void process(Exchange exchange) throws Exception {

        
        List<TrainPOJO> message = (List<TrainPOJO>)exchange.getIn().getBody(List.class);//Get the list from the parsed response body
        List<Double> coordinates = message.get(0).getLocation().getCoordinates();//Get the coordinates from the train object
        //Generate google maps link.
        exchange.getOut().setBody("https://www.google.com/maps/search/?api=1&query=" + coordinates.get(1) + "," + coordinates.get(0));
        
    }
}