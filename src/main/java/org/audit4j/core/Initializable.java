package org.audit4j.core;

import org.audit4j.core.exception.InitializationException;

public interface Initializable {

    void init() throws InitializationException;

    void stop();
}
