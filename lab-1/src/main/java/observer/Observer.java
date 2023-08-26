package observer;

/**
 * Interface of Observer object
 */
public interface Observer {
    /**
     * Creates an update with message
     * @param message
     */
    void update(String message);
}
