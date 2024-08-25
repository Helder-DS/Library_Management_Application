public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    // Constructor
    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters for accessing attributes
    public String getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    // String representation of the User object
    @Override
    public String toString() {
        return "ID: " + this.userId + ", First Name: " + this.firstName + ", Last Name: " + this.lastName + ", Email: " + this.email;
    }
}
