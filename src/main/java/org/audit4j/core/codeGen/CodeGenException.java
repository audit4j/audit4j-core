package org.audit4j.core.codeGen;

import org.audit4j.core.util.Log;

public class CodeGenException extends Exception{

    private static final long serialVersionUID = 6232922647368882504L;

    public CodeGenException(String message, Throwable e) {
        super(message, e);
        Log.warn("Exception occured while code generation :" + message);
    }
}
