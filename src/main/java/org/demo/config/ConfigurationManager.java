package org.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.demo.exception.HttpConfigurationException;
import org.demo.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    // A singleton

    private static ConfigurationManager configurationManager;
    private static Configuration configuration;
    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(configurationManager == null){
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    /*
    * Used to load a configuration file by the path provided
     */
    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();

        int i;

        while (true){
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException("Error while reading the file",e);
            }
            sb.append((char) i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File",e);
        }
        try {
            configuration = Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error creating Configuration Object",e);
        }
    }
    /*
     * Returns the current configuration
     */
    public Configuration getCurrentConfiguration(){
        if(configuration == null){
            throw new HttpConfigurationException("No current configuration set");
        }

        return configuration;
    }
}
