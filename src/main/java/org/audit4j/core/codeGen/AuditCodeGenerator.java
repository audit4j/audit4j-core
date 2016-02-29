package org.audit4j.core.codeGen;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Initializable;
import org.audit4j.core.ObjectSerializer;
import org.audit4j.core.ObjectToFieldsSerializer;
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

        db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanMethodAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            throw new InitializationException("", e);
        }

        Set<String> annotated = db.getAnnotationIndex().get(Audit.class.getName());
        for (String annotaionClass : annotated) {
            // Class<?> clazz = Class.forName(annotaionClass);
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = null;
            try {
                cc = pool.get(annotaionClass);
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (cc.hasAnnotation(Audit.class)) {
               System.out.println("Class: " + cc.getName());
                for (CtMethod method : cc.getMethods()) {
                    if (method.getName().contains("wait") || method.getName().contains("equals")
                            || method.getName().contains("toString")
                            || method.getName().contains("hashCode")
                            || method.getName().contains("getClass")
                            || method.getName().contains("notify")
                            || method.getName().contains("notifyAll")
                            || method.getName().contains("clone")
                            || method.getName().contains("finalize")) {

                    } else {
                        System.out.println("Method: " + method.getName());
                        StringBuilder builder = new StringBuilder(
                                "{ org.audit4j.core.AuditManager.getInstance()")
                                        .append(".audit(new org.audit4j.core.dto.EventBuilder()");
                        // Determine Action
                        //FIXME method never having annotations
                        if (method.hasAnnotation(Audit.class)) {
                            try {
                                builder.append(".addAction(\"").append(
                                        ((Audit) method.getAnnotation(Audit.class)).action())
                                        .append("\")");
                            } catch (ClassNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            builder.append(".addAction(\"").append(method.getName()).append("\")");
                        }

                        // Extract Fields
                        try {
                            int i = 1;
                            String paramName = null;
                            final Object[][] parameterAnnotations = method
                                    .getParameterAnnotations();
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
                                    builder.append(".addField(\"").append(paramName).append("\", ")
                                            .append("$").append(i).append(")");
                                }

                                paramName = null;
                                i++;
                            }
                        } catch (ClassNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        builder.append(".build()); }");
                        System.out.println(builder.toString());
                        try {
                            // method.insertBefore(
                            // "{ System.out.println(\"aaa\");
                            // System.out.println(\"aaa\"); }");
                            method.insertBefore(builder.toString());
                        } catch (CannotCompileException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            } else {
               // FIXME body
                System.out.println("MEy");
            }
            try {
                cc.writeFile();
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CannotCompileException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Class clazz = null;
            try {
                clazz = cc.toClass();
            } catch (CannotCompileException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }
    
    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public static void main(String[] args) {
        AuditManager.getInstance();
        AuditCodeGenerator gen = new AuditCodeGenerator();
        gen.setBasePackageName("org.audit4j.core");
        gen.init();

        TestClass testClass = new TestClass();
        testClass.test();
        testClass.test2("testParam");
    }
}
