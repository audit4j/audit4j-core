package org.audit4j.core;

import java.io.PrintStream;

/**
 * The Class Audit4jBanner.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
class Audit4jBanner {

    /** The Constant BANNER. */
    private static final String[] BANNER = { "    _             _ _ _   _  _   _ ",
            "   / \\  _   _  __| (_) |_| || | (_)", "  / _ \\| | | |/ _` | | __| || |_| |",
            " / ___ \\ |_| | (_| | | |_|__   _| |", "/_/   \\_\\__,_|\\__,_|_|\\__|  |_|_/ |",
            "                              |__/ " };

    /** The Constant line. */
    private static final String line = "===========================";

    /**
     * Prints the banner.
     */
    void printBanner() {
        PrintStream printStream = System.out;
        for (String line : BANNER) {
            printStream.println(line);
        }
        printStream.print(line);
        String version = Audit4jBanner.class.getPackage().getImplementationVersion();
        if (version == null) {
            printStream.println("(v" + CoreConstants.RELEASE_VERSION + ")");
        } else {
            printStream.println("(v" + version + ")");
        }
    }
}
