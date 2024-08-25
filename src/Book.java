import java.time.LocalDate;

public class Book extends Item {

    // Constructor
    public Book(String barcode, String authorArtist, String title, String type, int year, String isbn, String status) {
        super(barcode, authorArtist, title, type, year, isbn, status);
    }

    // Method to calculate due date for returning the book
    @Override
    public LocalDate calculateDueDate() {
        // Due date for a book is 4 weeks after the issue date
        return calculateIssueDate().plusWeeks(4);
    }

    // Method to calculate issue date when the book is borrowed
    @Override
    public LocalDate calculateIssueDate() {
        // Issue date for a book is the current date
        return LocalDate.now();
    }

    // Method to provide a string representation of the Book object
    @Override
    public String toString() {
        // Formatting the output for better readability
        return "Book\n" +
                "Author: " + getAuthorArtist() + "\n" +
                "Title: " + getTitle() + "\n" +
                "Year: " + getYear() + "\n" +
                "ISBN: " + getIsbn() + "\n" +
                "Status: " + getStatus() + "\n";
    }
}
