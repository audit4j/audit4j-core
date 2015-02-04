package org.audit4j.core.extra.scannotation;

import java.io.IOException;
import java.io.InputStream;

/**
 * Delegate to everything but close(). This object will not close the stream
 * 
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class InputStreamWrapper extends InputStream {
    private final InputStream delegate;

    public InputStreamWrapper(InputStream delegate) {
        this.delegate = delegate;
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return delegate.read(bytes);
    }

    @Override
    public int read(byte[] bytes, int i, int i1) throws IOException {
        return delegate.read(bytes, i, i1);
    }

    @Override
    public long skip(long l) throws IOException {
        return delegate.skip(l);
    }

    @Override
    public int available() throws IOException {
        return delegate.available();
    }

    @Override
    public void close() throws IOException {
        // ignored
    }

    @Override
    public void mark(int i) {
        delegate.mark(i);
    }

    @Override
    public void reset() throws IOException {
        delegate.reset();
    }

    @Override
    public boolean markSupported() {
        return delegate.markSupported();
    }
}
