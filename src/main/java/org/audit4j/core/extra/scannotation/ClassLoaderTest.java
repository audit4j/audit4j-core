package org.audit4j.core.extra.scannotation;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class ClassLoaderTest {

    public static void main(String[] args) {
        ann();
    }

    private static void classL() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Set<URL> result = new HashSet<>();
        System.out.println("asd");
        String packageName = "org.audit4j.core.option.annotation";
        String path = packageName.replace('.', '/');
        try {
            final Enumeration<URL> urls = classLoader.getResources(path);

            while (urls.hasMoreElements()) {

                final URL url = urls.nextElement();

                System.out.println(url.toString());
                int index = url.toExternalForm().lastIndexOf(path);
                System.out.println(index);
                if (index != -1) {
                    System.out.println("a");
                    result.add(new URL(url.toExternalForm().substring(0, index)));
                } else {
                    System.out.println("b");
                    result.add(url); // whatever
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (URL url : result) {
            System.out.println(url.toString());
        }
    }

    private static void ann() {
        System.out.println("as");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String packageName = "org.audit4j.core.option.annotation";
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AnnotationDB db = new AnnotationDB();
        String[] packages = { "org.audit4j.core.option.annotation" };
        db.setScanClassAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.getClassIndex();
        for (Entry<String, Set<String>> entry : db.getClassIndex().entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
