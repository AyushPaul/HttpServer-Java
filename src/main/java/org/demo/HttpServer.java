package org.demo;

import org.demo.config.Configuration;
import org.demo.config.ConfigurationManager;

import java.io.IOException;

public class HttpServer {

    public static void main(String[] args)  {
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Port : " + configuration.getPort());
        System.out.println("Webroot : " + configuration.getWebroot());
    }
}
