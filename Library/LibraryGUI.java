import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LibraryGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel cards;
    private CardLayout cardLayout;
    private Library library = new Library(); // The Library instance for managing books and members
    private Transaction transaction = Transaction.getTransaction(); // Singleton instance of Transaction

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LibraryGUI frame = new LibraryGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor to set up the main frame and initialize components.
     */
    public LibraryGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        //CardLayout for switching between panels
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        contentPane.add(cards, BorderLayout.CENTER);

        //The different panels
        cards.add(createMainMenu(), "MainMenu");
        cards.add(createAddBookPanel(), "AddBook");
        cards.add(createAddMemberPanel(), "AddMember");

        //app opens with the menu
        cardLayout.show(cards, "MainMenu");
    }

    /**
     * Main menu panel
     */
    private JPanel createMainMenu() {
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(null);
        mainMenu.setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("LIBRARY MANAGEMENT SYSTEM");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 20));
        lblTitle.setForeground(new Color(13, 71, 161));
        lblTitle.setBounds(10, 10, 560, 40);
        mainMenu.add(lblTitle);

        JButton btnAddBook = createStyledButton("Add Book", new Color(33, 150, 243));
        btnAddBook.setBounds(40, 100, 150, 40);
        btnAddBook.addActionListener(e -> cardLayout.show(cards, "AddBook"));
        mainMenu.add(btnAddBook);

        JButton btnAddMember = createStyledButton("Add Member", new Color(33, 150, 243));
        btnAddMember.setBounds(40, 160, 150, 40);
        btnAddMember.addActionListener(e -> cardLayout.show(cards, "AddMember"));
        mainMenu.add(btnAddMember);

        JButton btnTransactionHistory = createStyledButton("Transaction History", new Color(251, 140, 0));
        btnTransactionHistory.setBounds(40, 359, 180, 40);
        btnTransactionHistory.addActionListener(e -> showTransactionHistory());
        mainMenu.add(btnTransactionHistory);

        JButton btnBorrowBook = createStyledButton("Borrow Book", new Color(76, 175, 80)); 
        btnBorrowBook.setBounds(360, 100, 150, 40);
        btnBorrowBook.addActionListener(e -> borrowBook());
        mainMenu.add(btnBorrowBook);

        JButton btnReturnBook = createStyledButton("Return Book", new Color(76, 175, 80));
        btnReturnBook.setBounds(360, 160, 150, 40);
        btnReturnBook.addActionListener(e -> returnBook());
        mainMenu.add(btnReturnBook);

        JButton btnBorrowedBooks = createStyledButton("Borrowed Books", new Color(251, 140, 0));
        btnBorrowedBooks.setBounds(360, 359, 150, 40);
        btnBorrowedBooks.addActionListener(e -> showBorrowedBooks());
        mainMenu.add(btnBorrowedBooks);

        return mainMenu;
    }

    /**
     * Creates the panel for adding books to the library.
     */
    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(null);
        addBookPanel.setBackground(new Color(227, 242, 253));

        JLabel lblTitle = new JLabel("Add Book");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTitle.setForeground(new Color(13, 71, 161));
        lblTitle.setBounds(10, 10, 200, 30);
        addBookPanel.add(lblTitle);

        JLabel lblBookName = new JLabel("Book Name:");
        lblBookName.setBounds(30, 80, 100, 25);
        addBookPanel.add(lblBookName);

        JTextField txtBookName = createTextField();
        txtBookName.setBounds(140, 80, 200, 25);
        addBookPanel.add(txtBookName);

        JLabel lblBookId = new JLabel("Book ID:");
        lblBookId.setBounds(30, 120, 100, 25);
        addBookPanel.add(lblBookId);

        JTextField txtBookId = createTextField();
        txtBookId.setBounds(140, 120, 200, 25);
        addBookPanel.add(txtBookId);

        JButton btnAdd = createStyledButton("Add", new Color(76, 175, 80));
        btnAdd.setBounds(140, 180, 100, 30);
        btnAdd.addActionListener(e -> addBook(txtBookName, txtBookId));
        addBookPanel.add(btnAdd);

        JButton btnMenu = createStyledButton("Menu", new Color(189, 189, 189));
        btnMenu.setBounds(10, 400, 100, 30);
        btnMenu.addActionListener(e -> cardLayout.show(cards, "MainMenu"));
        addBookPanel.add(btnMenu);

        return addBookPanel;
    }

    /**
     * panel for adding members to library
     */
    private JPanel createAddMemberPanel() {
        JPanel addMemberPanel = new JPanel();
        addMemberPanel.setLayout(null);
        addMemberPanel.setBackground(new Color(232, 245, 233));

        JLabel lblTitle = new JLabel("Add Member");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTitle.setForeground(new Color(13, 71, 161)); 
        lblTitle.setBounds(10, 10, 200, 30);
        addMemberPanel.add(lblTitle);

        JLabel lblMemberName = new JLabel("Member Name:");
        lblMemberName.setBounds(30, 80, 100, 25);
        addMemberPanel.add(lblMemberName);

        JTextField txtMemberName = createTextField();
        txtMemberName.setBounds(140, 80, 200, 25);
        addMemberPanel.add(txtMemberName);

        JLabel lblMemberId = new JLabel("Member ID:");
        lblMemberId.setBounds(30, 120, 100, 25);
        addMemberPanel.add(lblMemberId);

        JTextField txtMemberId = createTextField();
        txtMemberId.setBounds(140, 120, 200, 25);
        addMemberPanel.add(txtMemberId);

        JButton btnAdd = createStyledButton("Add", new Color(33, 150, 243));
        btnAdd.setBounds(140, 180, 100, 30);
        btnAdd.addActionListener(e -> addMember(txtMemberName, txtMemberId));
        addMemberPanel.add(btnAdd);

        JButton btnMenu = createStyledButton("Menu", new Color(189, 189, 189));
        btnMenu.setBounds(10, 400, 100, 30);
        btnMenu.addActionListener(e -> cardLayout.show(cards, "MainMenu"));
        addMemberPanel.add(btnMenu);

        return addMemberPanel;
    }

    /**
     * styled JButton
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker()));

        // Adding hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    /**
     * JTextField
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189)), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    //adds a new book to the library
    private void addBook(JTextField txtBookName, JTextField txtBookId) {
        String bookName = txtBookName.getText();
        int bookId = Integer.parseInt(txtBookId.getText());
        try {
            Book newBook = new Book(bookId, bookName);
            library.addBook(newBook);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }

    //adds a new member to the library
    private void addMember(JTextField txtMemberName, JTextField txtMemberId) {
        String memberName = txtMemberName.getText();
        int memberId = Integer.parseInt(txtMemberId.getText());
        Member newMember = new Member(memberId, memberName);
        library.addMember(newMember);
        JOptionPane.showMessageDialog(this, "Member added successfully!");
    }

    //borrow book
    private void borrowBook() {
        // Get available books
        StringBuilder availableBooksList = new StringBuilder("Available Books:\n");
        List<Book> availableBooks = new ArrayList<>();     
        // Collect all available books
        for (Book book : library.getBooks()) {
            if (book.isAvailable()) {
                availableBooks.add(book);
                availableBooksList.append("ID: ").append(book.getId()).append(" - ").append(book.getTitle()).append("\n");
            }
        }
        // If no books are available,
        if (availableBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books available for borrowing!");
            return;
        }
        //Show the list of available books in dialog box
        String selectedBookIdStr = JOptionPane.showInputDialog(this, availableBooksList.toString() + "\nEnter the Book ID you want to borrow:");
        if (selectedBookIdStr == null || selectedBookIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No book selected!");
            return;
        }
        int selectedBookId = Integer.parseInt(selectedBookIdStr);      
        Book selectedBook = library.findBookById(selectedBookId);
        if (selectedBook == null || !selectedBook.isAvailable()) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID or Book not available.");
            return;
        }
        // Ask for Member ID
        String memberIdStr = JOptionPane.showInputDialog(this, "Enter Member ID:");
        int memberId = Integer.parseInt(memberIdStr);
        Member member = library.findMemberById(memberId);
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Invalid Member ID!");
            return;
        }
        //Proceed to borrow the book
        if (transaction.borrowBook(selectedBook, member)) {
            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
        }
    }
    // Method to return a book
    private void returnBook() {
        String memberIdStr = JOptionPane.showInputDialog(this, "Enter Member ID:");
        //if user pressed Cancel or left the input empty
        if (memberIdStr == null || memberIdStr.isEmpty()) {
            return;
        }
        int memberId = Integer.parseInt(memberIdStr);
        Member member = library.findMemberById(memberId);
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Invalid Member ID!");
            return;
        }
        String bookIdStr = JOptionPane.showInputDialog(this, "Enter Book ID:");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            return;
        }
        // Convert input to integer (Book ID)
        int bookId = Integer.parseInt(bookIdStr);
        Book book = library.findBookById(bookId);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID!");
            return;
        }
        if (transaction.returnBook(book, member)) {
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
        }
    }


    // display the transaction history
    private void showTransactionHistory() {
        transaction.displayTransactionHistory();
    }

 //display borrowed books by a specific member
    private void showBorrowedBooks() {
        String memberIdStr = JOptionPane.showInputDialog(this, "Enter Member ID:");
        if (memberIdStr == null || memberIdStr.isEmpty()) {
            return;
        }

        int memberId = Integer.parseInt(memberIdStr);
        Member member = library.findMemberById(memberId);
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Invalid Member ID!");
            return;
        }
        List<Book> borrowedBooks = member.getBorrowedBooks();
        StringBuilder borrowedBooksList = new StringBuilder();       
        if (borrowedBooks.isEmpty()) {
            borrowedBooksList.append("No books borrowed by this member.");
        } else {
            for (Book book : borrowedBooks) {
                borrowedBooksList.append(book.getTitle()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, borrowedBooksList.toString());
    }

}
