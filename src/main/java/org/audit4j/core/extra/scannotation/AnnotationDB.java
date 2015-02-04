package org.audit4j.core.extra.scannotation;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;

public class AnnotationDB implements Serializable {
    protected Map<String, Set<String>> annotationIndex = new HashMap<String, Set<String>>();    
    protected Map<String, Set<String>> implementsIndex = new HashMap<String, Set<String>>();
    protected Map<String, Set<String>> classIndex = new HashMap<String, Set<String>>();
    protected transient boolean scanClassAnnotations = true;
    protected transient boolean scanMethodAnnotations = true;
    protected transient boolean scanParameterAnnotations = true;
    protected transient boolean scanFieldAnnotations = true;
    protected transient String[] ignoredPackages = { "javax", "java", "sun", "com.sun", "javassist" };
    protected transient String[] scanPackages = null;
    protected transient boolean ignoreBadURLs = false;

    public String[] getScanPackages() {
        return scanPackages;
    }

    /**
     * Set explicit packages to scan. Set to null to enable ignore list.
     * 
     * @param scanPackages
     *            packages to scan or null
     */
    public void setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
    }

    public String[] getIgnoredPackages() {
        return ignoredPackages;
    }

    /**
     * Override/overwrite any ignored packages
     * 
     * @param ignoredPackages
     *            cannot be null
     */
    public void setIgnoredPackages(String[] ignoredPackages) {
        this.ignoredPackages = ignoredPackages;
    }

    public void addIgnoredPackages(String... ignored) {
        String[] tmp = new String[ignoredPackages.length + ignored.length];
        int i = 0;
        for (String ign : ignoredPackages)
            tmp[i++] = ign;
        for (String ign : ignored)
            tmp[i++] = ign;
        this.ignoredPackages = tmp;
    }

    private boolean ignoreScan(String intf) {
        if (scanPackages != null) {
            for (String scan : scanPackages) {
                // do not ignore if on packages to scan list
                if (intf.startsWith(scan + ".")) {
                    return false;
                }
            }
            return true; // didn't match whitelist, ignore
        }
        for (String ignored : ignoredPackages) {
            if (intf.startsWith(ignored + ".")) {
                return true;
            } else {
                // System.out.println("NOT IGNORING: " + intf);
            }
        }
        return false;
    }

    /**
     * returns a map keyed by the fully qualified string name of a annotation
     * class. The Set returne is a list of classes that use that annotation
     * somehow.
     */
    public Map<String, Set<String>> getAnnotationIndex() {
        return annotationIndex;
    }

    /**
     * returns a map keyed by the list of classes scanned. The value set
     * returned is a list of annotations used by that class.
     */
    public Map<String, Set<String>> getClassIndex() {
        return classIndex;
    }

    /**
     * Whether or not you want AnnotationDB to scan for class level annotations
     * 
     * @param scanClassAnnotations
     */
    public void setScanClassAnnotations(boolean scanClassAnnotations) {
        this.scanClassAnnotations = scanClassAnnotations;
    }

    /**
     * Wheter or not you want AnnotationDB to scan for method level annotations
     * 
     * @param scanMethodAnnotations
     */
    public void setScanMethodAnnotations(boolean scanMethodAnnotations) {
        this.scanMethodAnnotations = scanMethodAnnotations;
    }

    /**
     * Whether or not you want AnnotationDB to scan for parameter level
     * annotations
     * 
     * @param scanParameterAnnotations
     */
    public void setScanParameterAnnotations(boolean scanParameterAnnotations) {
        this.scanParameterAnnotations = scanParameterAnnotations;
    }

    /**
     * Whether or not you want AnnotationDB to scan for parameter level
     * annotations
     * 
     * @param scanFieldAnnotations
     */
    public void setScanFieldAnnotations(boolean scanFieldAnnotations) {
        this.scanFieldAnnotations = scanFieldAnnotations;
    }

    /**
     * Whether or not you want AnnotationDB to ignore bad URLs passed to
     * scanArchives. Default is to throw an IOException.
     * 
     * @param ignoreBadURLs
     */
    public void setIgnoreBadURLs(boolean ignoreBadURLs) {
        this.ignoreBadURLs = ignoreBadURLs;
    }

    /**
     * Scan a url that represents an "archive" this is a classpath directory or
     * jar file
     * 
     * @param urls
     *            variable list of URLs to scan as archives
     * @throws IOException
     */
    public void scanArchives(URL... urls) throws IOException {
        for (URL url : urls) {
            Filter filter = new Filter() {
                @Override
                public boolean accepts(String filename) {
                    if (filename.endsWith(".class")) {
                        if (filename.startsWith("/") || filename.startsWith("\\"))
                            filename = filename.substring(1);
                        if (!ignoreScan(filename.replace('/', '.')))
                            return true;
                        // System.out.println("IGNORED: " + filename);
                    }
                    return false;
                }
            };
            try {
                StreamIterator it = IteratorFactory.create(url, filter);
                InputStream stream;
                while ((stream = it.next()) != null)
                    scanClass(stream);
            } catch (IOException e) {
                if (ignoreBadURLs)
                    continue;
                else
                    throw e;
            }
        }
    }

    /**
     * Parse a .class file for annotations
     * 
     * @param bits
     *            input stream pointing to .class file bits
     * @throws IOException
     */
    public void scanClass(InputStream bits) throws IOException {
        DataInputStream dstream = new DataInputStream(new BufferedInputStream(bits));
        ClassFile cf = null;
        try {
            cf = new ClassFile(dstream);
            classIndex.put(cf.getName(), new HashSet<String>());
            if (scanClassAnnotations)
                scanClass(cf);
            if (scanMethodAnnotations || scanParameterAnnotations)
                scanMethods(cf);
            if (scanFieldAnnotations)
                scanFields(cf);
            // create an index of interfaces the class implements
            if (cf.getInterfaces() != null) {
                Set<String> intfs = new HashSet<String>();
                for (String intf : cf.getInterfaces())
                    intfs.add(intf);
                implementsIndex.put(cf.getName(), intfs);
            }
        } finally {
            dstream.close();
            bits.close();
        }
    }

    protected void scanClass(ClassFile cf) {
        String className = cf.getName();
        AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
        AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
        if (visible != null)
            populate(visible.getAnnotations(), className);
        if (invisible != null)
            populate(invisible.getAnnotations(), className);
    }

    /**
     * Scanns both the method and its parameters for annotations.
     * 
     * @param cf
     */
    protected void scanMethods(ClassFile cf) {
        List<ClassFile> methods = cf.getMethods();
        if (methods == null)
            return;
        for (Object obj : methods) {
            MethodInfo method = (MethodInfo) obj;
            if (scanMethodAnnotations) {
                AnnotationsAttribute visible = (AnnotationsAttribute) method
                        .getAttribute(AnnotationsAttribute.visibleTag);
                AnnotationsAttribute invisible = (AnnotationsAttribute) method
                        .getAttribute(AnnotationsAttribute.invisibleTag);
                if (visible != null)
                    populate(visible.getAnnotations(), cf.getName());
                if (invisible != null)
                    populate(invisible.getAnnotations(), cf.getName());
            }
            if (scanParameterAnnotations) {
                ParameterAnnotationsAttribute paramsVisible = (ParameterAnnotationsAttribute) method
                        .getAttribute(ParameterAnnotationsAttribute.visibleTag);
                ParameterAnnotationsAttribute paramsInvisible = (ParameterAnnotationsAttribute) method
                        .getAttribute(ParameterAnnotationsAttribute.invisibleTag);
                if (paramsVisible != null && paramsVisible.getAnnotations() != null) {
                    for (Annotation[] anns : paramsVisible.getAnnotations()) {
                        populate(anns, cf.getName());
                    }
                }
                if (paramsInvisible != null && paramsInvisible.getAnnotations() != null) {
                    for (Annotation[] anns : paramsInvisible.getAnnotations()) {
                        populate(anns, cf.getName());
                    }
                }
            }
        }
    }

    protected void scanFields(ClassFile cf) {
        List<ClassFile> fields = cf.getFields();
        if (fields == null)
            return;
        for (Object obj : fields) {
            FieldInfo field = (FieldInfo) obj;
            AnnotationsAttribute visible = (AnnotationsAttribute) field.getAttribute(AnnotationsAttribute.visibleTag);
            AnnotationsAttribute invisible = (AnnotationsAttribute) field
                    .getAttribute(AnnotationsAttribute.invisibleTag);
            if (visible != null)
                populate(visible.getAnnotations(), cf.getName());
            if (invisible != null)
                populate(invisible.getAnnotations(), cf.getName());
        }
    }

    protected void populate(Annotation[] annotations, String className) {
        if (annotations == null)
            return;
        Set<String> classAnnotations = classIndex.get(className);
        for (Annotation ann : annotations) {
            Set<String> classes = annotationIndex.get(ann.getTypeName());
            if (classes == null) {
                classes = new HashSet<String>();
                annotationIndex.put(ann.getTypeName(), classes);
            }
            classes.add(className);
            classAnnotations.add(ann.getTypeName());
        }
    }

    /**
     * Prints out annotationIndex
     * 
     * @param writer
     */
    public void outputAnnotationIndex(PrintWriter writer) {
        for (String ann : annotationIndex.keySet()) {
            writer.print(ann);
            writer.print(": ");
            Set<String> classes = annotationIndex.get(ann);
            Iterator<String> it = classes.iterator();
            while (it.hasNext()) {
                writer.print(it.next());
                if (it.hasNext())
                    writer.print(", ");
            }
            writer.println();
        }
    }
    
}