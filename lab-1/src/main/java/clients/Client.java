package clients;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Client class
 */
public class Client implements Observer {

    /**
     * List of notifications
     */
    @Getter
    private List<String> notifications;

    /**
     * Client's name
     */
    @Getter
    private String name;

    /**
     * Client's surname
     */
    @Getter
    private String surname;

    /**
     * Client's address
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private String address;

    /**
     * Client's passport
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private String passport;

    /**
     * Client's subscription status
     */
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean isSubscribed;

    /**
     * Creates a new client
     * @param name Client's name
     * @param surname Client's surname
     * @param address Client's address
     * @param passport Client's passport
     */

    public Client(String name, String surname, String address, String passport) {
        if (isNullOrWhiteSpace(name))
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (isNullOrWhiteSpace(surname))
            throw new IllegalArgumentException("Surname cannot be null or empty");

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
        isSubscribed = false;
        notifications = new ArrayList<>();
    }

    /**
     * Checks if client is verified
     * @return true if client is verified, false otherwise
     */

    public boolean isVerified() {
        return !isNullOrWhiteSpace(address) && !isNullOrWhiteSpace(passport);
    }

    /**
     * Updates client's notifications
     * @param message Notification message
     * @throws IllegalArgumentException if message is null or empty
     */
    @Override
    public void update(String message) {
        if (isNullOrWhiteSpace(message))
            throw new IllegalArgumentException("Message cannot be null or empty");
        notifications.add(message);
    }

    /**
     * Changes client's subscription status
     * @param isSubscribed New subscription status
     */
    public void changeSubscription(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    /**
     * Checks if string is null or empty
     * @param str String to check
     * @return true if string is null or empty, false otherwise
     */
    private boolean isNullOrWhiteSpace(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }
}
