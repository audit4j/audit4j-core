package org.audit4j.core.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class JavaVersionTest {

    public JavaVersionTest(final int javaVersion, final String javaSystemProperty) {
        this.javaVersion = javaVersion;
        this.javaSystemProperty = javaSystemProperty;
    }

    @Parameterized.Parameters(name = "{index}: checking java({0}) with version string {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
          { 6, "1.6.0_23"},
          { 7, "1.7.0_80"},
          { 8, "1.8.0_211"},
          { 9, "9.0.1"},
          { 10, "10.0.1"},
          { 11, "11.0.4"},
          { 12, "12"}
        });
    }

    private final int javaVersion;
    private final String javaSystemProperty;

    @Test
    public void canDetectWhetherJavaVersionIsHigherThanAGivenValue() {
        final List<Integer> javaVersions = Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13);

        for (final Integer version : javaVersions) {
            assertThat(
              "java version " + javaVersion + " with description " + javaSystemProperty + " was not correctly detected as higher than java " + version,
              JavaVersion.isJDK_N_OrHigher(version, javaSystemProperty),
              is(version <= javaVersion));
        }
    }
}