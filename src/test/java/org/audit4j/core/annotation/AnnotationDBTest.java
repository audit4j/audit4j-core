package org.audit4j.core.annotation;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;

import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.extra.scannotation.AnnotationDB;
import org.audit4j.core.extra.scannotation.ClasspathUrlFinder;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.Before;
import org.junit.Test;

public class AnnotationDBTest {

    AnnotationDB db = null;
    StopWatch watch;

    @Before
    public void before() {
        watch = new StopWatch();
    }

    @Test
    public void testHasAnnotation() {
        watch.start("annotationDB");
        URL url = ClasspathUrlFinder.findClassBase(ClassAnnotationMock.class);
        System.out.println(url);
        db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        watch.stop();
        Log.info(watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
        watch.reset();

        watch.start("testgetIndexClass");
        boolean result = db.getAnnotationIndex().get(Audit.class.getName())
                .contains(ClassAnnotationMock.class.getName());
        watch.stop();
        Log.info(watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
        assertTrue(result);
    }
    
    public static void main(String[] args) {
       
        System.out.println("as");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String packageName = "org.audit4j.core.Mock";
        String path = packageName.replace('.', '/');
        URL url = null;
        try {
            final Enumeration<URL> urls = classLoader.getResources(path);
            
            while (urls.hasMoreElements()) {
              
               url = urls.nextElement();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        url = ClasspathUrlFinder.findClassBase(ClassAnnotationMock.class);
        AnnotationDB db = new AnnotationDB();
        String[] packages = {"org.audit4j.core.option.annotation"};
        db.setScanClassAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.getClassIndex();
        for (Entry<String, Set<String>> entry :db.getClassIndex().entrySet()) {
            System.out.println(entry.getKey());
        }
    }

}
