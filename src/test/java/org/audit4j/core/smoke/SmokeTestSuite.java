package org.audit4j.core.smoke;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SmokeTest.class, CommandSmokeTest.class })
public class SmokeTestSuite {

}
