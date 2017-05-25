package jp.co.hyron.ope.storage;

/**
 * @author li_x
 */
public class StorageFileNotFoundException extends StorageException {

    /**
     *
     */
    private static final long serialVersionUID = -531054585186307528L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}