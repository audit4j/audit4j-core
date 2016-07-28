package org.audit4j.core.codeGen;

import java.util.Set;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.extra.reflections.Reflections;
import org.audit4j.core.extra.reflections.scanners.MethodAnnotationsScanner;
import org.audit4j.core.extra.reflections.scanners.SubTypesScanner;
import org.audit4j.core.extra.reflections.scanners.TypeAnnotationsScanner;
import org.audit4j.core.extra.reflections.util.ClasspathHelper;
import org.audit4j.core.extra.reflections.util.ConfigurationBuilder;
import org.audit4j.core.web.WebContext;

public class AnnotationIndexer {
    private Reflections reflections;

    public void index() {
        if (WebContext.isInitialized()) {
            reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forWebInfLib(WebContext.getServletContext()))
                    .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(),
                            new MethodAnnotationsScanner()));
        } else {
            reflections = new Reflections(
                    new ConfigurationBuilder().setUrls(ClasspathHelper.forClassLoader())
                            .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(),
                                    new MethodAnnotationsScanner()));
        }
    }

    public Set<String> getTypeAnnotatedAsClasses() {
        return reflections.getTypesAnnotatedWithAsString(Audit.class);
    }
    
}
