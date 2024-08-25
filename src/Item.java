import java.time.LocalDate;

public abstract class Item {
    private String barcode;
    private String authorArtist;
    private String title;
    private String type;
    private int year;
    private String isbn;
    private String status;

    //constructor
    public Item(String barcode, String authorArtist, String title, String type, int year, String isbn, String status) {
        this.barcode = barcode;
        this.authorArtist = authorArtist;
        this.title = title;
        this.type = type;
        this.year = year;
        this.isbn = isbn;
        this.status = status;
    }

    // Getter methods for accessing the private attributes
    public String getBarcode() {
        return barcode;
    }

    public String getAuthorArtist() {
        return authorArtist;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getStatus() {
        return status;
    }

    // Setter method for updating status
    public void setStatus(String status) {
        this.status = status;
    }

    //Abstract methods for the subclasses to use and override/overload
    public abstract LocalDate calculateIssueDate();

    public abstract LocalDate calculateDueDate();

    // String representation of the Item object
    @Override
    public String toString() {
        return "Item{" + "barcode='" + barcode + '\'' + ", authorArtist='" + authorArtist + '\'' + ", title='" + title
                + '\'' + ", type='" + type + '\'' + ", year=" + year + ", isbn='" + isbn + '\'' + ", status='" + status
                + '\'' + '}';
    }
}
