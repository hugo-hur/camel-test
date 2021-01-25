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



public class HTTPResponseProcessor implements Processor {


    
    
    public void process(Exchange exchange) throws Exception {

        //System.out.println("Hello from processor!");
        //CamelMessage message = (CamelMessage)exchange.getIn().getBody(CamelMessage.class);
        List<TrainPOJO> message = (List<TrainPOJO>)exchange.getIn().getBody(List.class);
        //System.out.println(message);
        //System.out.println(message.size());
        for( TrainPOJO t : message){
            //LocationPOJO l = t.getLocation();
            List<Double> coordinates = t.getLocation().getCoordinates();
            //System.out.println("https://www.google.com/maps/search/?api=1&query=" + coordinates.get(1) + "," + coordinates.get(0));
             //https://www.google.com/maps/search/?api=1&query=67.207279,23.844196
            //23.844196, 67.207279
        }
        //System.out.println("Message with correlationId get for exchange " + message.getMsgCorrelationId());
        //System.out.println("Body" + message.getBody());
    }
}