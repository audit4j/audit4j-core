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

package org.audit4j.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * The Class EnvUtil.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class EnvUtil {

    /**
     * Checks if is jD k_ n_ or higher.
     * 
     * @param n
     *            the n
     * @return true, if is jD k_ n_ or higher
     */
    private static boolean isJDK_N_OrHigher(int n) {
        List<String> versionList = new ArrayList<String>();
        // this code should work at least until JDK 10 (assuming n parameter is
        // always 6 or more)
        for (int i = 0; i < 5; i++) {
            versionList.add("1." + (n + i));
        }

        String javaVersion = System.getProperty("java.version");
        if (javaVersion == null) {
            return false;
        }
        for (String v : versionList) {
            if (javaVersion.startsWith(v))
                return true;
        }
        return false;
    }

    /**
     * Checks if is jD k5.
     * 
     * @return true, if is jD k5
     */
    static public boolean isJDK5() {
        return isJDK_N_OrHigher(5);
    }

    /**
     * Checks if is jD k6 or higher.
     * 
     * @return true, if is jD k6 or higher
     */
    static public boolean isJDK6OrHigher() {
        return isJDK_N_OrHigher(6);
    }

    /**
     * Checks if is jD k7 or higher.
     * 
     * @return true, if is jD k7 or higher
     */
    static public boolean isJDK7OrHigher() {
        return isJDK_N_OrHigher(7);
    }

    /**
     * Gets the javaersion.
     * 
     * @return the javaersion
     */
    public static String getJavaersion() {
        return System.getProperty("java.version");
    }

    /**
     * Checks if is janino available.
     * 
     * @return true, if is janino available
     */
    static public boolean isJaninoAvailable() {
        ClassLoader classLoader = EnvUtil.class.getClassLoader();
        try {
            Class<?> bindingClass = classLoader.loadClass("org.codehaus.janino.ScriptEvaluator");
            return (bindingClass != null);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if is servlet spec 3 or higher.
     * 
     * @param context
     *            the context
     * @return true, if is servlet spec3 or higher
     */
    public static boolean isServletSpec3OrHigher(ServletContext context) {
        if (context.getMajorVersion() >= 3) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is windows.
     * 
     * @return true, if is windows
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }
}
