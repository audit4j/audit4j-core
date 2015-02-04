package org.audit4j.core.extra.scannotation;

import java.io.IOException;
import java.net.URL;

public interface DirectoryIteratorFactory {
    StreamIterator create(URL url, Filter filter) throws IOException;
}
