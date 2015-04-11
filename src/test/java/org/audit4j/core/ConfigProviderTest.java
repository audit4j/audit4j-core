package org.audit4j.core;


import static org.junit.Assert.assertNotNull;

import org.audit4j.core.exception.ConfigurationException;
import org.junit.Test;

public class ConfigProviderTest extends Audit4jTestBase {

    @Test
    public void testYAMLProvider() {
        String url = "audit4j.conf.yaml";
        ConfigProvider<Configuration> provider = new YAMLConfigProvider<Configuration>(Configuration.class);
        try {
            provider.generateConfig(Configuration.DEFAULT, url);
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Configuration config = null;
        try {
             config = provider.readConfig(url);
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertNotNull(config);
        
    }
}
