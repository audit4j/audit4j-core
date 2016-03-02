package org.audit4j.core;

import org.audit4j.core.annotation.AuditAnnotationAttributesTests;
import org.audit4j.core.command.CommandProcessorTest;
import org.audit4j.core.dto.AuditBaseTest;
import org.audit4j.core.dto.AuditEventTest;
import org.audit4j.core.dto.EventBuilderTest;
import org.audit4j.core.dto.FieldTest;
import org.audit4j.core.exception.ConfigurationExceptionTest;
import org.audit4j.core.exception.HandlerExceptionTest;
import org.audit4j.core.exception.InitializationExceptionTest;
import org.audit4j.core.exception.TroubleshootExceptionTest;
import org.audit4j.core.exception.ValidationExceptionTest;
import org.audit4j.core.handler.file.ArchiveManagerTest;
import org.audit4j.core.layout.SecureLayoutTest;
import org.audit4j.core.layout.SimpleLayoutTest;
import org.audit4j.core.util.EncryptionUtilTest;
import org.audit4j.core.util.EnvUtilTest;
import org.audit4j.core.util.StopWatchTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class Audit4jCoreTestSuit.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ ValidationManagerTest.class, EncryptionUtilTest.class, ArchiveManagerTest.class,
        AnnotationTransformerTests.class, SecureLayoutTest.class, SimpleLayoutTest.class,
        EnvUtilTest.class, EncryptionUtilTest.class, StopWatchTest.class,
        ConfigurationExceptionTest.class, HandlerExceptionTest.class,
        InitializationExceptionTest.class, TroubleshootExceptionTest.class,
        ValidationExceptionTest.class, AuditBaseTest.class, AuditEventTest.class,
        EventBuilderTest.class, FieldTest.class, AuditAnnotationAttributesTests.class,
        CommandProcessorTest.class })
public class Audit4jCoreUnitTestSuit {

}
