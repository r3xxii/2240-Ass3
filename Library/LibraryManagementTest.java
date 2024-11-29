import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class LibraryManagementTest {
	private Transaction transaction;
	private Book book;
    private Member member;
    
    @BeforeEach
    public void setUp() throws Exception {
    	transaction = Transaction.getTransaction();
    	book = new Book(101, "Test Book");
        member = new Member(1, "Test Member");
    }
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
        
        assertTrue(new Book(200, "Valid Book").isValidId(validBook1.getId()));
        assertFalse(new Book(200, "Valid Book").isValidId(50));
        assertFalse(new Book(200, "Valid Book").isValidId(1200));
	}
	@Test
	public void testBorrowReturn() {
		//check if book is available
		assertTrue(book.isAvailable());
		//borrow book and check if successful
		boolean borrowResult = transaction.borrowBook(book, member);
        assertTrue(borrowResult);
        //check that the book is no longer available
        assertFalse(book.isAvailable());
        //borrow book again, should be unsuccessful
        boolean borrowAgainResult = transaction.borrowBook(book, member);
        assertFalse(borrowAgainResult);
        //return book and check that its successful
        boolean returnResult = transaction.returnBook(book, member);
        assertTrue(returnResult);
        //check if book is available, it should be
        assertTrue(book.isAvailable());
        //return book, it should be unsuccessful
        boolean returnAgainResult = transaction.returnBook(book, member);
        assertFalse(returnAgainResult);
	}
	@Test
	public void testSingletonTransaction() throws Exception{
		Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
		//check if constructor is private
		int modifiers = constructor.getModifiers();
		assertTrue(Modifier.isPrivate(modifiers), "Transaction constructor is not private");
	}
}
