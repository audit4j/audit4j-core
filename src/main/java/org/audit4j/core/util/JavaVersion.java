package org.audit4j.core.util;

/*

java.version is a system property that exists in every JVM. There are two possible formats for it:

    Java 8 or lower: 1.6.0_23, 1.7.0, 1.7.0_80, 1.8.0_211
    Java 9 or higher: 9.0.1, 11.0.4, 12, 12.0.1

 */
public class JavaVersion {
    /**
     * Checks if is jD k_ n_ or higher.
     *
     * @param n
     *            the n
     * @return true, if is jD k_ n_ or higher
     */
    static boolean isJDK_N_OrHigher(int n, String javaVersion) {
        try {
            final String[] versionParts = javaVersion.split("\\.");

            int version;
            if ("1".equals(versionParts[0])) {
                version = Integer.parseInt(versionParts[1]);
            } else {
                version = Integer.parseInt(versionParts[0]);
            }
            return version >= n;
        } catch (Exception e) {
            // swallow any error and return false to maintain previous behaviour of defaulting to false
            return false;
        }
    }

    public static String current() {
        return System.getProperty("java.version");
    }
}
