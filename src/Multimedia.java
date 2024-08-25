import java.time.LocalDate;

public class Multimedia extends Item {

    // Constructor
    public Multimedia(String barcode, String authorArtist, String title, String type, int year, String isbn,
                      String status) {
        super(barcode, authorArtist, title, type, year, isbn, status);
    }

    // Method to calculate due date for returning the multimedia item
    @Override
    public LocalDate calculateDueDate() {
        // Due date for multimedia is 1 week after the issue date
        return calculateIssueDate().plusWeeks(1);
    }

    // Method to calculate issue date when the multimedia item is borrowed
    @Override
    public LocalDate calculateIssueDate() {
        // Issue date for multimedia is the current date
        return LocalDate.now();
    }

    // Method to provide a string representation of the Multimedia object
    @Override
    public String toString() {
        // Formatting the output for better readability
        return "Multimedia\n" + "Artist: " + getAuthorArtist() + "\n" + "Title: " + getTitle() + "\n" + "Year: "
                + getYear() + "\n" + "ISBN: " + getIsbn() + "\n" + "Status: " + getStatus() + "\n";
    }
}
