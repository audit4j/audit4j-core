package org.audit4j.core.handler.file.archive;

import java.net.UnknownHostException;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.exception.Audit4jRuntimeException;
import org.audit4j.core.exception.InitializationException;

/**
 * The Class FTPArchiveJob.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class FTPArchiveJob extends ArchiveJob {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8651767095397282923L;
  
    /** The client arguments. */
    String[] clientArguments;

    /* (non-Javadoc)
     * @see org.audit4j.core.handler.file.ArchiveJob#archive()
     */
    @Override
    void archive() {
        FTPArchiveClient client = new FTPArchiveClient();
        try {
            client.init(clientArguments);
        } catch (UnknownHostException e) {
            throw new Audit4jRuntimeException("Excepting in running archive client.", e);
        }
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#init()
     */
    @Override
    public void init() throws InitializationException {
        clientArguments = super.archiveOptions.split(CoreConstants.SEMI_COLON);
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

}
