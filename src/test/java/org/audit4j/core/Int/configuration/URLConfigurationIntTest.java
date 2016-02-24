package org.audit4j.core.Int.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.Int.IntTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlWriter;

public class URLConfigurationIntTest extends IntTestBase {

    private String configFileLocation;

    @Before
    public void before() {
        configFileLocation = System.getProperty("user.home") + "/audit4j.conf.yml";
        System.out.println(configFileLocation);

        YamlWriter writer;
        try {
            writer = new YamlWriter(new FileWriter(configFileLocation));
            writer.getConfig().setClassTag("Configuration", Configuration.class);
            writer.write(Configuration.DEFAULT);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testInitWithConfigUri() {
        System.out.println("starting");
        AuditManager.startWithConfiguration(configFileLocation);
        AuditManager.getInstance().audit(getSampleAuditEvent());
    }

    @After
    public void after() {
        System.out.println(configFileLocation);
        File file = new File(configFileLocation);
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
        AuditManager.shutdown();
    }
}
