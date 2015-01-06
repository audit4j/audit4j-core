package org.audit4j.core;

import org.audit4j.core.annotation.Audit4jAnnotationTestSuite;
import org.audit4j.core.smoke.SmokeTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SmokeTestSuite.class, Audit4jCoreTestSuit.class, Audit4jAnnotationTestSuite.class })
public class Audit4jTestRunner {

}
