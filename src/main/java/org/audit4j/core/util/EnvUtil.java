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

import org.audit4j.core.Configurations;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * The Class EnvUtil.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class EnvUtil {


    /**
     * private constructor to avoid instantiation of this class
     */
    private EnvUtil(){

    }

    /**
     * Checks if is jD k5.
     *
     * @return true, if is jD k5
     */
    static public boolean isJDK5() {
        return JavaVersion.isJDK_N_OrHigher(5, getJavaVersion());
    }

    /**
     * Checks if is jD k6 or higher.
     *
     * @return true, if is jD k6 or higher
     */
    static public boolean isJDK6OrHigher() {
        return JavaVersion.isJDK_N_OrHigher(6, getJavaVersion());
    }

    /**
     * Checks if is jD k7 or higher.
     *
     * @return true, if is jD k7 or higher
     */
    static public boolean isJDK7OrHigher() {
        return JavaVersion.isJDK_N_OrHigher(7, getJavaVersion());
    }

    /**
     * Gets the javaersion
     *
     * @deprecated renamed this method. use {@link EnvUtil#getJavaVersion()} instead.
     *
     * @return the javaersion
     */
    @Deprecated
    public static String getJavaersion() {
        return getJavaVersion();
    }

    /**
     * Gets the java version.
     *
     * @return the java version
     */
    public static String getJavaVersion() {
        return JavaVersion.current();
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
            return bindingClass != null;
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

    /**
     * Checks whether config file exists in directory.
     *
     * @param dirPath the dir path
     * @return true, if successful
     */
    public static boolean hasConfigFileExists(String dirPath) {
        String filePath = dirPath + File.separator + Configurations.CONFIG_FILE_NAME + ".";
        if (AuditUtil.isFileExists(filePath + Configurations.YML_EXTENTION)
                || AuditUtil.isFileExists(filePath + Configurations.YAML_EXTENTION)
                || AuditUtil.isFileExists(filePath + Configurations.XML_EXTENTION)) {
            return true;
        }
        return false;
    }

}
