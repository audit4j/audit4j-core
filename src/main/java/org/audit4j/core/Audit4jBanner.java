package org.audit4j.core;

import java.io.PrintStream;

public class Audit4jBanner {

    private static final String[] BANNER = { "    _             _ _ _   _  _   _ ",
            "   / \\  _   _  __| (_) |_| || | (_)", "  / _ \\| | | |/ _` | | __| || |_| |",
            " / ___ \\ |_| | (_| | | |_|__   _| |", "/_/   \\_\\__,_|\\__,_|_|\\__|  |_|_/ |",
            "                              |__/ " };

    private static final String line = "===========================";

    public void printBanner() {
        PrintStream printStream = System.out;
        for (String line : BANNER) {
            printStream.println(line);
        }
        printStream.print(line);
        String version = Audit4jBanner.class.getPackage().getImplementationVersion();
        if (version == null) {
            printStream.println("(v" + CoreConstants.RELEASE_VERSION + ")");
        } else {
            printStream.println("(v" + Audit4jBanner.class.getPackage().getImplementationVersion() + ")");
        }
    }
}
