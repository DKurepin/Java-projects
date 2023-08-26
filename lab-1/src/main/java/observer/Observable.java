package observer;

import clients.Client;

/**
 * Interface of Observable object
 */
public interface Observable {
    /**
     * Adds observer
     * @param client
     */
    void addObserver(Client client);

    /**
     * Removes observer
     * @param client
     */
    void removeObserver(Client client);

    /**
     * Notifies obserbers
     * @param message
     */
    void notifyObservers(String message);
}
