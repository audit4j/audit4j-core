package org.audit4j.core;

import org.audit4j.core.dto.EventBuilderTest;
import org.audit4j.core.handler.file.ArchiveManagerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class Audit4jCoreTestSuit.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ ValidationManagerTest.class, EncryptionUtilTest.class, TroubleshootManagerTest.class,
        ArchiveManagerTest.class, EventBuilderTest.class })
public class Audit4jCoreTestSuit {

}
