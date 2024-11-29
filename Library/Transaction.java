import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Transaction {
	private static Transaction instance; //The single instance
	private Transaction() {} //The private constructor to restrict construction access
	public static Transaction getTransaction() { //The getter
		if(instance == null) {
			instance = new Transaction(); //Constructs an instance if it doesn't already exist
		}
		return instance;
	}

	//Save transaction deets to a file
	public void saveTransaction(String transactionDetails) {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/transactions.txt", true))) {
            writer.write(transactionDetails);
            writer.newLine(); //each transaction starts at a new line
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
	
	public void displayTransactionHistory() {
		BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader("src/transactions.txt"));
	        String line;
	        System.out.println("=============== Transaction History ===============");
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }
	        System.out.println("===================================================");
	    } catch (IOException e) {
	        System.err.println("Error reading transaction history: " + e.getMessage());
	    } finally {
	        //close BufferedReader.
	        try {
	            if (reader != null) {
	                reader.close();
	            }
	        } catch (IOException e) {
	            System.err.println("Error closing file: " + e.getMessage());
	        }
	    }
	}
	
    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails); //saves transactions to the file
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public boolean returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails);
            return true;//successful return
        } else {
            System.out.println("This book was not borrowed by the member.");
            return false;//an unsuccessful return
        }
    }
    
    
    // Get the current date and time in a readable format
    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}