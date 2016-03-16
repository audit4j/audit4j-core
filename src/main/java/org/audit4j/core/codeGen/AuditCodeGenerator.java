/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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

package org.audit4j.core.codeGen;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.IgnoreAudit;
import org.audit4j.core.extra.scannotation.AnnotationDB;
import org.audit4j.core.extra.scannotation.ClasspathUrlFinder;
import org.audit4j.core.util.annotation.Beeta;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * The Class AuditCodeGenerator is used to inject codes in runtime to each and every method
 * based on the annotations. 
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.5.0
 */
@Beeta
public class AuditCodeGenerator {

    /** The db. */
    private AnnotationDB db;

    /** The base package name. */
    private String basePackageName;

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.Initializable#init()
     */
    public void generate() throws CodeGenException {
        indexAnnotations();
        ClassPool pool = ClassPool.getDefault();

        Set<String> annotated = db.getAnnotationIndex().get(Audit.class.getName());
        for (String annotaionClass : annotated) {
            CtClass cc = null;
            try {
                cc = pool.get(annotaionClass);
            } catch (NotFoundException e) {
                throw new CodeGenException("Class pool can not be created", e);
            }
            if (cc.hasAnnotation(Audit.class)) {
                for (CtMethod method : cc.getMethods()) {
                    if (excludedMethod(method.getName())
                            || method.hasAnnotation(IgnoreAudit.class)) {

                    } else {
                        StringBuilder builder = new StringBuilder(
                                "{ org.audit4j.core.AuditManager.getInstance()");
                        builder.append(".audit(new org.audit4j.core.dto.EventBuilder()");
                        extractAction(builder, method);
                        extractFields(builder, method);
                        builder.append(".build()); }");
                        try {
                            method.insertBefore(builder.toString());
                        } catch (CannotCompileException e) {
                            throw new CodeGenException("Unable to insert code in to given method. ",
                                    e);
                        }
                    }
                }
            } else {
                for (CtMethod method : cc.getMethods()) {
                    if (excludedMethod(method.getName()) || !method.hasAnnotation(Audit.class)
                            || method.hasAnnotation(IgnoreAudit.class)) {

                    } else {
                        StringBuilder builder = new StringBuilder(
                                "org.audit4j.core.AuditManager.getInstance()");
                        builder.append(".audit(new org.audit4j.core.dto.EventBuilder()");
                        extractAction(builder, method);
                        extractFields(builder, method);
                        builder.append(".build());");
                        try {
                            method.insertBefore(builder.toString());
                        } catch (CannotCompileException e) {
                            throw new CodeGenException("Unable to insert code in to given method. ",
                                    e);
                        }
                    }
                }
            }
            try {
                cc.writeFile();
            } catch (NotFoundException | IOException | CannotCompileException e) {
                throw new CodeGenException("Unable to write code in to given class. ", e);
            }

            try {
                cc.toClass();
            } catch (CannotCompileException e) {
                throw new CodeGenException("Unable to load class. ", e);
            }
        }
    }

    /**
     * Index annotations.
     * 
     * @throws CodeGenException
     */
    private void indexAnnotations() throws CodeGenException {
        db = AnnotationDB.getDefault();
        db.setScanClassAnnotations(true);
        db.setScanMethodAnnotations(true);

        URL[] urls = ClasspathUrlFinder.findClassPaths();
        try {
            db.scanArchives(urls);
        } catch (IOException e) {
            throw new CodeGenException("Unable to load scan classpath. ", e);
        }
    }

    /**
     * Determine package url.
     *
     * @return the url
     * @throws CodeGenException
     */
    private URL determinePackageURL() throws CodeGenException {
        URL url = null;
        if (basePackageName == null) {
            url = ClasspathUrlFinder.findClassBase(AuditCodeGenerator.class);
        } else {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = basePackageName.replace('.', '/');
            try {
                final Enumeration<URL> urls = classLoader.getResources(path);
                while (urls.hasMoreElements()) {
                    url = urls.nextElement();
                    int index = url.toExternalForm().lastIndexOf(path);

                    url = new URL(url.toExternalForm().substring(0, index));
                }
            } catch (IOException e) {
                throw new CodeGenException("Unable to determine url: ", e);
            }
        }
        return url;
    }

    /**
     * Excluded method.
     *
     * @param methodName
     *            the method name
     * @return true, if successful
     */
    private boolean excludedMethod(String methodName) {
        if (methodName.contains("wait") || methodName.contains("equals")
                || methodName.contains("toString") || methodName.contains("hashCode")
                || methodName.contains("getClass") || methodName.contains("notify")
                || methodName.contains("notifyAll") || methodName.contains("clone")
                || methodName.contains("finalize")) {
            return true;
        }
        return false;
    }

    /**
     * Extract action.
     *
     * @param builder
     *            the builder
     * @param method
     *            the method
     * @throws CodeGenException
     */
    private void extractAction(StringBuilder builder, CtMethod method) throws CodeGenException {
        if (method.hasAnnotation(Audit.class)) {
            try {
                builder.append(".addAction(\"")
                        .append(((Audit) method.getAnnotation(Audit.class)).action()).append("\")");
            } catch (ClassNotFoundException e) {
                throw new CodeGenException("Annotation not found in the given method. ", e);
            }
        } else {
            builder.append(".addAction(\"").append(method.getName()).append("\")");
        }
    }

    /**
     * Extract fields.
     *
     * @param builder
     *            the builder
     * @param method
     *            the method
     * @throws CodeGenException
     */
    private void extractFields(StringBuilder builder, CtMethod method) throws CodeGenException {

        int i = 1;
        String paramName = null;
        Object[][] parameterAnnotations = null;
        try {
            parameterAnnotations = method.getParameterAnnotations();
        } catch (ClassNotFoundException e) {
            throw new CodeGenException("Annotation not found in the given parameter. ", e);
        }
        for (final Object[] annotations : parameterAnnotations) {
            // final Object object = params[i++];
            boolean ignoreFlag = false;
            DeIdentify deidentify = null;
            for (final Object annotation : annotations) {
                if (annotation instanceof IgnoreAudit) {
                    ignoreFlag = true;
                    break;
                }
                if (annotation instanceof AuditField) {
                    final AuditField field = (AuditField) annotation;
                    paramName = field.field();
                }
                if (annotation instanceof DeIdentify) {
                    deidentify = (DeIdentify) annotation;
                }
            }

            if (ignoreFlag) {

            } else {
                if (null == paramName) {
                    paramName = "arg" + i;
                }
                // serializer.serialize(fields, object,
                // paramName, deidentify);
                builder.append(".addField(\"").append(paramName).append("\", ").append("$")
                        .append(i).append(")");
            }

            paramName = null;
            i++;
        }
    }

    /**
     * Sets the base package name.
     *
     * @param basePackageName
     *            the new base package name
     */
    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.Initializable#stop()
     */
    public void flush() {
        db.flush();
    }
}