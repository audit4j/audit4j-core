package org.audit4j.core.extra.scannotation;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class IteratorFactory {
    private static final ConcurrentHashMap<String, DirectoryIteratorFactory> registry = new ConcurrentHashMap<String, DirectoryIteratorFactory>();
    
    /**
     * private constructor to avoid instantiation of this class
     */
    private IteratorFactory(){
    	
    }
    
    static {
        registry.put("file", new FileProtocolIteratorFactory());
    }

    public static StreamIterator create(URL url, Filter filter) throws IOException {
        String urlString = url.toString();
        if (urlString.endsWith("!/")) {
            urlString = urlString.substring(4);
            urlString = urlString.substring(0, urlString.length() - 2);
            url = new URL(urlString);
        }
        if (!urlString.endsWith("/")) {
            return new JarIterator(url.openStream(), filter);
        } else {
            DirectoryIteratorFactory factory = registry.get(url.getProtocol());
            if (factory == null)
                throw new IOException("Unable to scan directory of protocol: " + url.getProtocol());
            return factory.create(url, filter);
        }
    }
}
