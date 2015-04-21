package org.audit4j.core;

import org.junit.Test;

public class ConfigurationsTest {

 /*   @Test
    public void testScanConfigFile() {
        String path = Configurations.scanConfigFile(null);
        assertNotNull(path);
    }

    @Test
    public void testLoadConfig() {
        String path = Configurations.scanConfigFile(null);
        try {
            Configuration config = Configurations.loadConfig(path);
            assertNotNull(config);
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
*/
    @Test
    public void testgetEnvironemtVariableConfigFilePath() {
        final String value = System.getenv("AUDIT4J_CONF_FILE_PATH");
        System.out.println(value);
        // Path path = Configurations.getEnvironemtVariableConfigFilePath();
       // System.out.println(path.toUri().getPath());
    }
}
