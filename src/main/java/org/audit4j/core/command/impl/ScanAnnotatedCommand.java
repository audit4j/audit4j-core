package org.audit4j.core.command.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.audit4j.core.Registry;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.extra.scannotation.AnnotationDB;
import org.audit4j.core.filter.impl.ScanAnnotatedFilter;

/**
 * The Class ScanAnnotatedCommand.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public class ScanAnnotatedCommand extends AbstractCommand {

    /** The db. */
    AnnotationDB db;

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#init()
     */
    @Override
    public void init() {
        String packageName = getOptions().get("-scanAnnotated");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');
        URL url = null;
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

        db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanMethodAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            throw new InitializationException("", e);
        }

    }

    /* (non-Javadoc)
     * @see org.audit4j.core.command.AbstractCommand#execute()
     */
    @Override
    public void execute() {
        Set<String> annotated = db.getAnnotationIndex().get(Audit.class.getName());
        ScanAnnotatedFilter filter = new ScanAnnotatedFilter();
        for (String annotaionClass : annotated) {
            try {
                Class<?> clazz = Class.forName(annotaionClass);
                filter.addClass(clazz);
            } catch (ClassNotFoundException e) {
                throw new InitializationException("", e);
            }
        }
        Registry.addAnnotationFilter(filter);
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        db = null;
    }

}
