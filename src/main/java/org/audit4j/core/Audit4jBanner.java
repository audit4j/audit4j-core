/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core;

import java.io.PrintStream;

/**
 * Print audit4j banner in audit4j initialization. Called by audit4j Context
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
class Audit4jBanner {

    /** The BANNER. */
    private static final String[] BANNER = { "     _             _ _ _   _  _   _ ",
            "    / \\  _   _  __| (_) |_| || | (_)", "   / _ \\| | | |/ _` | | __| || |_| |",
            "  / ___ \\ |_| | (_| | | |_|__   _| |", " /_/   \\_\\__,_|\\__,_|_|\\__|  |_|_/ |",
            "                               |__/ " };

    /** Line under the banner. */
    private static final String line = " ===========================";

    /**
     * Prints banner using system out.
     */
    void printBanner() {
        PrintStream printStream = System.out;
        for (String lineLocal : BANNER) {
            printStream.println(lineLocal);
        }
        printStream.print(line);
        String version = Audit4jBanner.class.getPackage().getImplementationVersion();
        if (version == null) {
            printStream.println("(v" + CoreConstants.RELEASE_VERSION + ")");
        } else {
            printStream.println("(v" + version + ")");
        }
        printStream.println();
    }
}
