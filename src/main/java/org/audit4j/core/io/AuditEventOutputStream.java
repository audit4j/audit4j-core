package org.audit4j.core.io;

import org.audit4j.core.dto.AuditEvent;

/**
 * The Class AuditEventOutputStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditEventOutputStream implements AuditOutputStream {

    /** The output stream. */
    AuditOutputStream outputStream;

    /**
     * Instantiates a new audit event output stream.
     *
     * @param outputStream the output stream
     */
    public AuditEventOutputStream(AuditOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent)
     */
    @Override
    public AuditEventOutputStream write(AuditEvent event) {
        outputStream.write(event);
        return this;
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    @Override
    public void close() {
        outputStream.close();
        outputStream = null;
    }

}
