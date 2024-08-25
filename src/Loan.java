import java.time.LocalDate;

public class Loan {
    private String userId;
    private String barcode;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private int renewCount;

    //Constructor
    public Loan(String userId, String barcode, LocalDate issueDate, LocalDate dueDate, int renewCount) {
        this.userId = userId;
        this.barcode = barcode;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.renewCount = renewCount;
    }

    // Getters and setters to access the private attributes
    public String getUserId() {
        return userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    // String representation of the Loan object
    @Override
    public String toString() {
        // Formatting the output for better readability
        return "User ID: " + userId + ", " + "Barcode: " + barcode + ", " + "Issue Date: " + issueDate + ", " + "Due Date: " + dueDate + ", " +
                "Renew Count: " + renewCount;
    }
}
