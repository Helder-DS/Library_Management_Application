import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Library_Management {

    //Book
    // User - B00447489
    // Barcode - 25832497

    //Book
    //User - B00575912
    //Barcode - 514284220

    //NON Existing
    //User -  B00000464
    //Barcode @@@@

    //Multimedia
    // User -  B00000464
    //Barcode - 592475236

    //renew
    // item not on loan - 577774437




    public static void main(String[] args) {

        Library_Management app = new Library_Management();
        ArrayList<User> users = readUsersFromFile("USERS.csv");
        ArrayList<Item> items = readItemsFromFile("ITEMS.csv");
        ArrayList<Loan> loans = readLoansFromFile("LOANS.csv");
        app.start(users, items, loans);

    }

    /**
     * Calls the menu method and passes the user, item and loan ArrayList
     *
     * @param users An ArrayList containing User objects representing users
     * @param items An ArrayList containing items objects representing items
     * @param loans An ArrayList containing loans objects representing loans
     */
    public void start(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        System.out.println("Welcome to the Library Management System");
        menu(users, items, loans);

    }


    /**
     * Displays a menu with options for the user to select and calls corresponding methods based on the user's choice
     *
     * @param users An ArrayList containing User objects representing users
     * @param items An ArrayList containing items objects representing items
     * @param loans An ArrayList containing loans objects representing loans
     *
     */
    public void menu(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("Please select an option:");
            System.out.println("1. Issue an item");
            System.out.println("2. Renew a loan");
            System.out.println("3. Return an item");
            System.out.println("4. View all items on loan");
            System.out.println("5. Search for an item by barcode");
            System.out.println("6. Report details about loaned items");
            System.out.println("7. Exit");

            try {
                int choice = input.nextInt();
                input.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        issueItem(users, items, loans);
                        // Handle issuing and item
                        break;
                    case 2:
                        renewLoan(items, loans);
                        // Handle renewing a loan
                        break;
                    case 3:
                        returnItem(items, loans);
                        // Handle recording return of an item
                        break;
                    case 4:
                        viewAllLoans(loans);
                        // Handle viewing all items on loan
                        break;
                    case 5:
                        searchLoan(loans, items);
                        // Handle searching for an item by barcode
                        break;
                    case 6:
                        generateLoanReport(loans, items);
                        //Generates a loan report
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter a number from 1 to 6.\n");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid option.\n");
                input.nextLine();
            }
        } while (!exit);

        System.out.println("Thank you for using the Library Management System. Goodbye!");
        input.close();
    }

    /**
     * Validates the entered users ID and item barcode to issue the requested item
     *
     * @param users - An ArrayList containing User objects representing users
     * @param items - An ArrayList containing items objects representing items
     * @param loans - An ArrayList containing loans objects representing loans
     *
     */
    public void issueItem(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        String userId;
        String barcode;
        boolean userIdFound = false;
        boolean barcodeFound = false;

        System.out.println("To loan a book or multimedia, please enter your user ID and the item's barcode:");

        do {
            System.out.print("User ID: ");
            userId = input.nextLine().toUpperCase();

            for (User user : users) {
                if (userId.equals(user.getUserId())) {
                    userIdFound = true;
                    break;
                }
            }

            if (!userIdFound) {
                System.out.println("Invalid user ID entered. Please try again.");
                continue; // Skip to the next iteration of the loop to prompt for user ID again
            }

            System.out.print("Barcode: ");
            barcode = input.nextLine().toUpperCase();

            for (Item item : items) {
                if (barcode.equals(item.getBarcode())) {
                    barcodeFound = true;
                    if (item.getStatus().equals("available")) {
                        LocalDate issueDate = item.calculateIssueDate();
                        LocalDate dueDate = item.calculateDueDate();
                        item.setStatus("On Loan");
                        Loan obj = new Loan(userId, barcode, issueDate, dueDate, 0);
                        loans.add(obj);
                        writeLoansToFileLoan(loans, "LOANS.csv");
                        writeItemsToFileItem(items, "ITEMS.csv");
                        System.out.println("Item issued successfully.");
                    } else {
                        System.out.println("Item is not available for loan.");
                    }
                    break;
                }
            }

            if (!barcodeFound && userIdFound) {
                System.out.println("Invalid barcode entered. Please try again.");
            }

        } while (!userIdFound || !barcodeFound);


    }

    /**
     *  Validates the entered barcode for renewal and updates the due date if successful
     *
     * @param items - items An ArrayList containing User objects representing items
     * @param loans - loans An ArrayList containing User objects representing loans
     *
     */
    public void renewLoan(ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        String barcode;
        boolean barcodeFound = false;

        System.out.println("To renew a loaned item, please enter the item barcode");

        do {
            System.out.print("barcode: ");
            barcode = input.nextLine().toUpperCase();

            for (Loan loan : loans) {
                if (barcode.equals(loan.getBarcode())) {
                    barcodeFound = true;
                    break;
                }
            }

            if (!barcodeFound) {
                System.out.println("Invalid barcode entered, please try again");
            }

        } while (!barcodeFound);

        for (Loan loan : loans) {
            if (barcode.equals(loan.getBarcode())) {
                for (Item item : items) {
                    if (barcode.equals(item.getBarcode())) {
                        if (item.getType().equals("Book") && loan.getRenewCount() < 3) {
                            loan.setRenewCount(loan.getRenewCount() + 1);
                            loan.setDueDate(loan.getDueDate().plusWeeks(2));
                            System.out.println("Book renewed successfully.");
                            writeLoansToFileLoan(loans, "LOANS.csv");
                        } else if (item.getType().equals("Multimedia") && loan.getRenewCount() < 2) {
                            loan.setRenewCount(loan.getRenewCount() + 1);
                            loan.setDueDate(loan.getDueDate().plusWeeks(1));
                            System.out.println("Multimedia renewed successfully.");
                            writeLoansToFileLoan(loans, "LOANS.csv");
                        } else {
                            System.out.println("Item cannot be renewed. Maximum number of attempts reached.");
                        }
                        break;
                    }
                }
                break;
            }
        }


    }


    /**
     * Validates the entered item barcode for return and if successful updates the status
     * and removes the item from the loan list
     *
     * @param items - An ArrayList containing items objects representing items
     * @param loans - An ArrayList containing loans objects representing loans
     *
     */
    public void returnItem(ArrayList<Item> items, ArrayList<Loan> loans) {

        Scanner input = new Scanner(System.in);
        String barcode;
        boolean barcodeFound = false;

        System.out.println("To return an item, please enter the item barcode.");

        do {
            System.out.print("barcode: ");
            barcode = input.nextLine().toUpperCase();

            for (Loan loan : loans) {
                if (barcode.equals(loan.getBarcode())) {
                    barcodeFound = true;
                    break;
                }
            }

            if (!barcodeFound) {
                System.out.println("Invalid barcode entered, please try again");
            }

        } while (!barcodeFound);

        Iterator<Loan> iterator = loans.iterator();
        while (iterator.hasNext()) {
            Loan loan = iterator.next();
            if (barcode.equals(loan.getBarcode())) {
                for (Item item : items) {
                    if (barcode.equals(item.getBarcode())) {
                        item.setStatus("available"); // Corrected spelling
                        iterator.remove(); // Remove the loan using the iterator
                        System.out.println("Item returned successfully, thank you");
                        writeLoansToFileLoan(loans, "LOANS.csv");
                        writeItemsToFileItem(items, "ITEMS.csv");
                        break; // Exit the loop once the item is found
                    }
                }
            }
        }

    }


    /**
     * Displays the details of all items currently on loan, such as the user loaned ID,
     * barcode number, issue date, due date and renew count.
     *
     * @param loans - An ArrayList containing loans objects representing loans
     */
    public void viewAllLoans(ArrayList<Loan> loans) {

        System.out.println("All items currently on loan");
        System.out.println("");
        for (Loan loan : loans) {
            System.out.println(loan);
        }

        System.out.println("");

    }


    /**
     * Validates the entered item barcode to display the item details such as:
     * Item type, author/artist, title, year, ISBN and status.
     *
     * @param items - An ArrayList containing items objects representing items
     * @param loans - An ArrayList containing loans objects representing loans
     */
    public void searchLoan(ArrayList<Loan> loans, ArrayList<Item> items) {

        Scanner input = new Scanner(System.in);
        String barcode;
        boolean barcodeFound = false;

        System.out.println("To view a loaned item, please enter the item barcode.");

        do {
            System.out.print("barcode: ");
            barcode = input.nextLine().toUpperCase();

            for (Loan loan : loans) {
                if (barcode.equals(loan.getBarcode())) {
                    barcodeFound = true;
                    break;
                }
            }

            if (!barcodeFound) {
                System.out.println("Invalid barcode entered, please try again");
            }

        } while (!barcodeFound);

        for (Loan loan : loans) {
            if (barcode.equals(loan.getBarcode())) {
                for (Item item : items) {
                    if (barcode.equals(item.getBarcode())) {
                        System.out.println(item.toString());
                    }
                }
            }
        }

    }


    /**
     * Generates a report indicating the library name, total number of each type of loan
     * held by the library and the percentage of loan items having been renewed more then
     * once.
     *
     * @param items - An ArrayList containing items objects representing items
     * @param loans - An ArrayList containing loans objects representing loans
     */
    public void generateLoanReport(ArrayList<Loan> loans, ArrayList<Item> items) {

        int totalBooks = 0;
        int totalMultimedia = 0;
        int renewedMoreThanOnceCount = 0;
        int totalLoans = loans.size();

        // Count total books, total multimedia, and loans renewed more than once
        for (Loan loan : loans) {
            for (Item item : items) {
                if (loan.getBarcode().equals(item.getBarcode()) && item.getType().equals("Book")) {
                    totalBooks++;
                } else if (loan.getBarcode().equals(item.getBarcode()) && item.getType().equals("Multimedia")) {
                    totalMultimedia++;
                }
            }

            if (loan.getRenewCount() > 1) {
                renewedMoreThanOnceCount++;
            }
        }

        // Calculate percentage of loans renewed more than once
        double percentageRenewedMoreThanOnce = (double) renewedMoreThanOnceCount / totalLoans * 100;

        // Print report
        System.out.println("\nLibrary Name: Ulster University Library ");
        System.out.println("Total Books on Loan: " + totalBooks);
        System.out.println("Total Multimedia Items on Loan: " + totalMultimedia);
        System.out.println("Percentage of Loans Renewed More Than Once: " + percentageRenewedMoreThanOnce + "%\n");
    }



    /**
     * Reads loan information from a file and stores them in an Arraylist
     *
     * @param fileName - The name of the file to read from
     * @return - An ArrayList containing Loan objects read from the file
     */
    public static ArrayList<Loan> readLoansFromFile(String fileName) {
        ArrayList<Loan> loans = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fileName));

            // Skip header row
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] arr = line.trim().split(",");

                String userId = arr[0];
                String barcode = arr[1];
                LocalDate issueDate = LocalDate.parse(arr[2]);
                LocalDate dueDate = LocalDate.parse(arr[3]);
                int renewCount = Integer.parseInt(arr[4]);

                Loan loan = new Loan(userId, barcode, issueDate, dueDate, renewCount);
                loans.add(loan);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return loans;
    }


    /**
     * Reads User information from a file and stores them in an Arraylist
     *
     * @param fileName - The name of the file to read from
     * @return - An ArrayList containing User objects read from the file
     */
    public static ArrayList<User> readUsersFromFile(String fileName) {

        ArrayList<User> users = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fileName));

            // Skip header row
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] arr = line.trim().split(",");

                String userId = arr[0];
                String firstName = arr[1];
                String lastName = arr[2];
                String email = arr[3];

                User user = new User(userId, firstName, lastName, email);
                users.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return users;
    }


    /**
     * Reads Item information from a file and stores them in an Arraylist
     *
     * @param fileName - The name of the file to read from
     * @return - An ArrayList containing Item objects read from the file
     */
    public static ArrayList<Item> readItemsFromFile(String fileName) {

        ArrayList<Item> items = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fileName));

            // Skip header row
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] arr = line.trim().split(",");

                String barcode = arr[0];
                String authorArtist = arr[1];
                String title = arr[2];
                String type = arr[3];
                int year = Integer.parseInt(arr[4]);
                String isbn = arr[5];
                String status = ""; // Default status

                if (arr.length > 6) { // Check if status field exists
                    status = arr[6];
                }

                if (status.isEmpty()) { // If status is empty, set it to "available"
                    status = "available";
                }

                // Create an instance of a concrete subclass of Item
                Item item;
                if (type.equals("Book")) {
                    // Create a Book object
                    item = new Book(barcode, authorArtist, title, type, year, isbn, status);
                } else if (type.equals("Multimedia")) {
                    // Create a Multimedia object
                    item = new Multimedia(barcode, authorArtist, title, type, year, isbn, status);
                } else {
                    // Handle invalid type
                    System.out.println("Invalid type: " + type);
                    continue;
                }
                items.add(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return items;
    }


    /**
     * Writes loan information from an ArrayList into a file using FileWriter
     *
     * @param loans - The ArrayList containing Loan objects to be written into the file
     * @param fileName - The name of the file to write the loan information into
     */
    public static void writeLoansToFileLoan(ArrayList<Loan> loans, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("UserId,Barcode,IssueDate,DueDate,RenewCount\n");
            for (Loan loan : loans) {
                writer.write(loan.getUserId() + "," + loan.getBarcode() + "," + loan.getIssueDate() + ","
                        + loan.getDueDate() + "," + loan.getRenewCount() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Writes Item information from an ArrayList into a file using FileWriter
     *
     * @param items - The ArrayList containing Loan objects to be written into the file
     * @param fileName - The name of the file to write the items information into
     */
    public static void writeItemsToFileItem(ArrayList<Item> items, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Barcode,Author/Artist,Title,Type,Year,ISBN,Status\n");
            for (Item item : items) {
                writer.write(item.getBarcode() + "," + item.getAuthorArtist() + "," + item.getTitle() + ","
                        + item.getType() + "," + item.getYear() + "," + item.getIsbn() + "," + item.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
