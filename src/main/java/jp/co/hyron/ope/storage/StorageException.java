package jp.co.hyron.ope.storage;

/**
 * @author li_x
 */
public class StorageException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1352582310001494706L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
