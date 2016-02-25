package org.audit4j.core.handler.file.archive;

/**
 * The Enum CompressionType.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public enum Compression {

    /** The zip. */
    ZIP("zip");

    /** The extention. */
    private String extention;

    /**
     * Instantiates a new compression type.
     *
     * @param extention the extention
     */
    Compression(String extention) {
        this.extention = extention;
    }

    /**
     * Gets the extention.
     *
     * @return the extention
     */
    public String getExtention() {
        return extention;
    }

}
