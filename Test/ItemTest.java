import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void getStatus() {
        // Arrange
        Item item = new Book("B00447489", "Elka Glazebrook", "non mattis pulvinar nulla pede",
                "Book", 2008, "867041599-2", "available");

        // Act
        item.setStatus("On Loan"); // Set a new status

        // Assert
        assertEquals("On Loan", item.getStatus());
    }
}
