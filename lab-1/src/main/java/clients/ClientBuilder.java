package clients;

/**
 * ClientBuilder class
 */
public class ClientBuilder {

    /**
     * Client's name
     */
    private String name;

    /**
     * Client's surname
     */
    private String surname;

    /**
     * Client's address
     */
    private String address;

    /**
     * Client's passport
     */
    private String passport;

    /**
     * Sets client name
     * @param name
     * @throws IllegalArgumentException if name is null or WhiteSpace
     * @return name
     */
    public ClientBuilder setName(String name) {
        if (name == null || name.isEmpty() || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
        return this;
    }

    /**
     * Sets client surname
     * @param surname
     * @throws IllegalArgumentException if surname is null or WhiteSpace
     * @return surname
     */
    public ClientBuilder setSurname(String surname) {
        if (surname == null || surname.isEmpty() || surname.trim().isEmpty())
            throw new IllegalArgumentException("Surname cannot be null or empty");
        this.surname = surname;
        return this;
    }


    /**
     * Sets client address
     * @param address
     * @throws IllegalArgumentException
     * @return address
     */
    public ClientBuilder setAddress(String address) {
        if (address == null || address.isEmpty() || address.trim().isEmpty())
            throw new IllegalArgumentException("Address cannot be null or empty");
        this.address = address;
        return this;
    }

    /**
     * Sets client passport
     * @param passport
     * @throws IllegalArgumentException
     * @return passport
     */
    public ClientBuilder setPassport(String passport) {
        if (passport == null || passport.isEmpty() || passport.trim().isEmpty())
            throw new IllegalArgumentException("Passport cannot be null or empty");
        this.passport = passport;
        return this;
    }

    /**
     * Creates client with name, surname, address, passport
     * @return client
     */
    public Client createClient() {
        return new Client(name, surname, address, passport);
    }
}
