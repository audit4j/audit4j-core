package org.audit4j.core.extra.reflections.scanners;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import org.audit4j.core.extra.reflections.Configuration;
import org.audit4j.core.extra.reflections.vfs.Vfs;

import javax.annotation.Nullable;

/**
 *
 */
public interface Scanner {

    void setConfiguration(Configuration configuration);

    Multimap<String, String> getStore();

    void setStore(Multimap<String, String> store);

    Scanner filterResultsBy(Predicate<String> filter);

    boolean acceptsInput(String file);

    Object scan(Vfs.File file, @Nullable Object classObject);

    boolean acceptResult(String fqn);
}
