package org.audit4j.core.handler.file.archive;

/**
 * The Enum ArchiveType.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public enum ArchiveType {

    /** The local. */
    LOCAL("local"), /** The ftp. */
    FTP("ftp");

    /** The name. */
    private String name;

    /**
     * Instantiates a new archive type.
     * 
     * @param name
     *            the name
     */
    private ArchiveType(String name) {
        this.name = name;
    }
    
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

}
