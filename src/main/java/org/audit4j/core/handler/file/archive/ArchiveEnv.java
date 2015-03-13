package org.audit4j.core.handler.file.archive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ArchiveEnv.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ArchiveEnv {

    /** The date pattern. */
    private final String datePattern = "1d";

    /** The cron pattern. */
    private String cronPattern;

    /** The dir path. */
    private String dirPath;

    /** The compression. */
    private Compression compression = Compression.ZIP;

    /** The properties. */
    private Map<String, String> properties = new HashMap<String, String>();

    /** The archive types. */
    private List<ArchiveType> archiveTypes = new ArrayList<ArchiveType>();

    /** The archive method. */
    private ArchiveMethod archiveMethod = ArchiveMethod.SCHEDULED;

    /** The compressor. */
    private Compressor compressor = new ZIPCompressor();

    /**
     * Instantiates a new archive env.
     */
    public ArchiveEnv() {
        archiveTypes.add(ArchiveType.LOCAL);
    }

    /**
     * Gets the cron pattern.
     * 
     * @return the cron pattern
     */
    public String getCronPattern() {
        return cronPattern;
    }

    /**
     * Sets the cron pattern.
     * 
     * @param cronPattern
     *            the new cron pattern
     */
    public void setCronPattern(String cronPattern) {
        this.cronPattern = cronPattern;
    }

    /**
     * Gets the dir path.
     * 
     * @return the dir path
     */
    public String getDirPath() {
        return dirPath;
    }

    /**
     * Sets the dir path.
     * 
     * @param dirPath
     *            the new dir path
     */
    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    /**
     * Gets the date pattern.
     * 
     * @return the date pattern
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Gets the properties.
     * 
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Gets the compressor.
     * 
     * @return the compressor
     */
    public Compressor getCompressor() {
        return compressor;
    }

    /**
     * Sets the properties.
     * 
     * @param properties
     *            the properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Sets the compressor.
     * 
     * @param compressor
     *            the new compressor
     */
    public void setCompressor(Compressor compressor) {
        this.compressor = compressor;
    }
    
    /**
     * Gets the archive method.
     * 
     * @return the archive method
     */
    public ArchiveMethod getArchiveMethod() {
        return archiveMethod;
    }

    /**
     * Sets the archive method.
     * 
     * @param archiveMethod
     *            the new archive method
     */
    public void setArchiveMethod(ArchiveMethod archiveMethod) {
        this.archiveMethod = archiveMethod;
    }

    /**
     * Gets the archive types.
     * 
     * @return the archive types
     */
    public List<ArchiveType> getArchiveTypes() {
        return archiveTypes;
    }

    /**
     * Sets the archive types.
     * 
     * @param archiveTypes
     *            the new archive types
     */
    public void setArchiveTypes(List<ArchiveType> archiveTypes) {
        this.archiveTypes = archiveTypes;
    }

    /**
     * Gets the compression.
     *
     * @return the compression
     */
    public Compression getCompression() {
        return compression;
    }

    /**
     * Sets the compression.
     *
     * @param compression the new compression
     */
    public void setCompression(Compression compression) {
        this.compression = compression;
    }
}
