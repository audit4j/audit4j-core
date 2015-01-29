package org.audit4j.core.Int.annotation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MethodAnnotationTest.class, ClassAnnotationTest.class, DeIdentifyAnnotationTest.class })
public class Audit4jAnnotationTestSuite {

}
