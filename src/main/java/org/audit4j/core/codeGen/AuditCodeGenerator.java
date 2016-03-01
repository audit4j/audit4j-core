package org.audit4j.core.codeGen;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Initializable;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.IgnoreAudit;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.extra.scannotation.AnnotationDB;
import org.audit4j.core.extra.scannotation.ClasspathUrlFinder;
import org.audit4j.core.util.annotation.Beeta;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

@Beeta
public class AuditCodeGenerator implements Initializable {

    private AnnotationDB db;
    private String basePackageName;

    @Override
    public void init() throws InitializationException {
        indexAnnotations();
        ClassPool pool = ClassPool.getDefault();

        Set<String> annotated = db.getAnnotationIndex().get(Audit.class.getName());
        for (String annotaionClass : annotated) {
            CtClass cc = null;
            try {
                cc = pool.get(annotaionClass);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            if (cc.hasAnnotation(Audit.class)) {
                System.out.println("Class: " + cc.getName());
                for (CtMethod method : cc.getMethods()) {
                    if (excludedMethod(method.getName())
                            || method.hasAnnotation(IgnoreAudit.class)) {

                    } else {
                        System.out.println("Method: " + method.getName());
                        StringBuilder builder = new StringBuilder(
                                "{ org.audit4j.core.AuditManager.getInstance()");
                        builder.append(".audit(new org.audit4j.core.dto.EventBuilder()");
                        extractAction(builder, method);
                        extractFields(builder, method);
                        builder.append(".build()); }");

                        System.out.println(builder.toString());
                        try {
                            method.insertBefore(builder.toString());
                        } catch (CannotCompileException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                for (CtMethod method : cc.getMethods()) {
                    if (excludedMethod(method.getName()) || !method.hasAnnotation(Audit.class)
                            || method.hasAnnotation(IgnoreAudit.class)) {

                    } else {
                        System.out.println("Method: " + method.getName());
                        StringBuilder builder = new StringBuilder(
                                "{ org.audit4j.core.AuditManager.getInstance()");
                        builder.append(".audit(new org.audit4j.core.dto.EventBuilder()");
                        extractAction(builder, method);
                        extractFields(builder, method);
                        builder.append(".build()); }");
                        System.out.println(builder.toString());
                        try {
                            method.insertBefore(builder.toString());
                        } catch (CannotCompileException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                cc.writeFile();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }

            Class clazz = null;
            try {
                clazz = cc.toClass();
            } catch (CannotCompileException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void indexAnnotations() {
        db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanMethodAnnotations(true);
        try {
            db.scanArchives(determinePackageURL());
        } catch (IOException e) {
            throw new InitializationException("", e);
        }
    }

    private URL determinePackageURL() {
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
                throw new InitializationException("", e);
            }
        }
        return url;
    }

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

    private void extractAction(StringBuilder builder, CtMethod method) {
        if (method.hasAnnotation(Audit.class)) {
            try {
                builder.append(".addAction(\"")
                        .append(((Audit) method.getAnnotation(Audit.class)).action()).append("\")");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            builder.append(".addAction(\"").append(method.getName()).append("\")");
        }
    }

    private void extractFields(StringBuilder builder, CtMethod method) {

        int i = 1;
        String paramName = null;
        Object[][] parameterAnnotations = null;
        try {
            parameterAnnotations = method.getParameterAnnotations();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }
    
    @Override
    public void stop() {
        db.flush();
    }

    public static void main(String[] args) {
        AuditManager.getInstance();
        AuditCodeGenerator gen = new AuditCodeGenerator();
        gen.setBasePackageName("org.audit4j.core");
        gen.init();
        gen.stop();
        
        TestClass testClass = new TestClass();
        testClass.test();
        testClass.test2("testParam");
        
        
    }
}
