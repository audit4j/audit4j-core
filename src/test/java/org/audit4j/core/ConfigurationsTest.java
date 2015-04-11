package org.audit4j.core;

import static org.junit.Assert.assertNotNull;

import org.audit4j.core.exception.ConfigurationException;
import org.junit.Test;

public class ConfigurationsTest {

    @Test
    public void testScanConfigFile(){
       String path =  Configurations.scanConfigFile(null);
       System.out.println(path);
       assertNotNull(path);
    }
    
    @Test
    public void testLoadConfig(){
        String path = Configurations.scanConfigFile(null);
        try {
            Configuration config = Configurations.loadConfig(path);
            assertNotNull(config);
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
               
    }
}
