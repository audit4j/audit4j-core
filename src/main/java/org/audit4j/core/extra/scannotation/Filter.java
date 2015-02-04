package org.audit4j.core.extra.scannotation;

public interface Filter {
    boolean accepts(String filename);
}
