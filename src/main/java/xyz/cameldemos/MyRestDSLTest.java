package xyz.cameldemos;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MyRestDSLTest extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration()//Bind the api servlet to the localhost port 8080
            .component("servlet")
            .bindingMode(RestBindingMode.auto);

        rest("/api")//Log any get requests
            .get()
            .route().to("log:DEBUG?showBody=true&showHeaders=true");
    }

}
