package org.audit4j.core.Int;

import org.audit4j.core.Int.configuration.InjectConfigurationIntTest;
import org.audit4j.core.Int.configuration.URLConfigurationIntTest;
import org.audit4j.core.Int.event.annotation.ClassAnnotationTest;
import org.audit4j.core.Int.event.annotation.DeIdentifyAnnotationTest;
import org.audit4j.core.Int.event.annotation.MethodAnnotationTest;
import org.audit4j.core.Int.filter.AuditEventFilterIntTest;
import org.audit4j.core.Int.option.ScanAnnotationOptionIntTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MethodAnnotationTest.class, ClassAnnotationTest.class, DeIdentifyAnnotationTest.class,
        AuditEventFilterIntTest.class, ScanAnnotationOptionIntTest.class, InjectConfigurationIntTest.class,
        URLConfigurationIntTest.class })
public class IntTestSuite {

}
