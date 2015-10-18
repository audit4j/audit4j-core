package org.audit4j.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.audit4j.core.exception.ConfigurationException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * The Class XMLConfigProvider.
 *
 * @param <T> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @since 2.4.0
 */
public class XMLConfigProvider<T> implements ConfigProvider<T> {

    /** The clazz. */
    private final Class<T> clazz;

    /**
     * Instantiates a new xML config provider.
     *
     * @param clazz the clazz
     */
    public XMLConfigProvider(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.ConfigProvider#readConfig(java.lang.String)
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public T readConfig(String filePath) throws ConfigurationException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("configuration", clazz);
        return (T) xstream.fromXML(new File(filePath));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.ConfigProvider#readConfig(java.io.InputStream)
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public T readConfig(InputStream fileAsStream) throws ConfigurationException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("configuration", clazz);
        return (T) xstream.fromXML(fileAsStream);
    }
    
    /**
     * {@inheritDoc}
     * 
     * 
     */
    @Override
    public void generateConfig(T config, String filePath) throws ConfigurationException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("configuration", clazz);
        BufferedOutputStream stdout = null;
        try {
            stdout = new BufferedOutputStream(new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xstream.marshal(config, new PrettyPrintWriter(new OutputStreamWriter(stdout)));
    }

}
