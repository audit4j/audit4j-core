package org.audit4j.core.extra.scannotation;

import java.io.InputStream;

public interface StreamIterator {
    /**
     * User is resposible for closing the InputStream returned
     * 
     * @return null if no more streams left to iterate on
     */
    InputStream next();

    /**
     * Cleanup any open resources of the iterator
     * 
     */
    void close();
}
