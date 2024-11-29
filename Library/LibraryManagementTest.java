import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LibraryManagementTest {

	@Test
	public void testBookId() throws Exception {
		Book validBook1 = new Book(100, "Valid Book"); //Test for the minimal valid.
		assertEquals(100, validBook1.getId());
		
		Book validBook2 = new Book(999, "Another Valid Book");// Test for the maximum valid.
        assertEquals(999, validBook2.getId()); 
        
        //test for invalid IDs 99 and below.
        try {
        	new Book(99, "Invalid Book");
        	fail("Expected an exception for ID < 100");
        } catch (Exception e) {
        	assertEquals("Invalid Book ID. It must be between 99 and 1000.", e.getMessage());
        }
        
        //test for invalid IDs 1000 and above.
        try {
            new Book(1000, "Invalid Book");
            fail("Expected an exception for ID > 999");
        } catch (Exception e) {
            assertEquals("Invalid Book ID. It must be between 99 and 1000.", e.getMessage());
        }
        
        assertTrue(new Book(200, "Valid Book").isValidId(100));
        assertFalse(new Book(200, "Valid Book").isValidId(50));
        assertFalse(new Book(200, "Valid Book").isValidId(1200));
	}

}
