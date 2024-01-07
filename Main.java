import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Book {
    String title;
    String author;
    boolean isAvailable;
    boolean isBorrowed;
    String borrowerName;
    String borrowerContact;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.isBorrowed = false;
        this.borrowerName = "";
        this.borrowerContact = "";
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author +
                ", Available: " + (isAvailable ? "Yes" : "No") +
                (isBorrowed ? ", Borrower: " + borrowerName + ", Contact: " + borrowerContact : "");
    }
}

class LibraryManagementGUI extends JFrame {
    private List<Book> books;

    public LibraryManagementGUI() {
        books = new ArrayList<>();

        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230)); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(173, 216, 230)); 
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK); 
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(173, 216, 230)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton addBookButton = createStyledButton("Add Book");
        JButton removeBookButton = createStyledButton("Remove Book");
        JButton viewBooksButton = createStyledButton("View Books");
        JButton borrowBookButton = createStyledButton("Borrow Book");
        JButton returnBookButton = createStyledButton("Return Book");

        addBookButton.addActionListener(e -> addBook());
        removeBookButton.addActionListener(e -> removeBook());
        viewBooksButton.addActionListener(e -> viewBooks());
        borrowBookButton.addActionListener(e -> borrowBook());
        returnBookButton.addActionListener(e -> returnBook());

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(addBookButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(removeBookButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(viewBooksButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(borrowBookButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(returnBookButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(new Color(70, 130, 180)); 
        button.setBackground(new Color(176, 224, 230)); 
        return button;
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter book title:");
        String author = JOptionPane.showInputDialog(this, "Enter author:");

        if (title != null && author != null) {
            Book book = new Book(title, author);
            books.add(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        }
    }

    private void removeBook() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books to remove!");
        } else {
            String[] bookTitles = books.stream().map(book -> book.title).toArray(String[]::new);

            String selectedTitle = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a book to remove:",
                    "Remove Book",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    bookTitles,
                    bookTitles[0]
            );

            if (selectedTitle != null) {
                Book selectedBook = books.stream()
                        .filter(book -> book.title.equals(selectedTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedBook != null) {
                    if (selectedBook.isBorrowed) {
                        JOptionPane.showMessageDialog(this, "Cannot remove the book. It is currently borrowed and must be returned first.");
                    } else {
                        books.remove(selectedBook);
                        JOptionPane.showMessageDialog(this, "Book removed successfully!");
                    }
                }
            }
        }
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books available!");
        } else {
            Object[][] data = new Object[books.size()][6]; 

            for (int i = 0; i < books.size(); i++) {
                data[i][0] = books.get(i).title;
                data[i][1] = books.get(i).author;
                data[i][2] = books.get(i).isAvailable ? "Yes" : "No";
                data[i][3] = books.get(i).isBorrowed ? "Yes" : "No";
                data[i][4] = books.get(i).borrowerName;
                data[i][5] = books.get(i).borrowerContact;
            }

            String[] columnNames = {"Title", "Author", "Available", "Borrowed", "Borrower Name", "Borrower Contact"};

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JFrame frame = new JFrame("Book List");
            frame.setSize(800, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(this);
            frame.add(panel);
            frame.setVisible(true);
        }
    }

    private void borrowBook() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books available!");
            return;
        }

        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books currently available for borrowing!");
            return;
        }

        JComboBox<Book> bookComboBox = new JComboBox<>(availableBooks.toArray(new Book[0]));
        bookComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Book) {
                    Book book = (Book) value;
                    setText(book.title + " by " + book.author);
                }
                return this;
            }
        });

        int result = JOptionPane.showConfirmDialog(
                this,
                bookComboBox,
                "Select a book to borrow:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Book selectedBook = (Book) bookComboBox.getSelectedItem();

            if (selectedBook != null) {
                String borrowerName = JOptionPane.showInputDialog(this, "Enter borrower name:");
                String borrowerContact = JOptionPane.showInputDialog(this, "Enter borrower contact:");

                if (borrowerName != null && borrowerContact != null) {
                    selectedBook.isAvailable = false;
                    selectedBook.isBorrowed = true;
                    selectedBook.borrowerName = borrowerName;
                    selectedBook.borrowerContact = borrowerContact;

                    JOptionPane.showMessageDialog(this, "Book '" + selectedBook.title + "' borrowed successfully!");
                }
            }
        }
    }

    private void returnBook() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books available!");
            return;
        }

        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isBorrowed) {
                borrowedBooks.add(book);
            }
        }

        if (borrowedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books currently borrowed!");
            return;
        }

        JComboBox<Book> bookComboBox = new JComboBox<>(borrowedBooks.toArray(new Book[0]));
        bookComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Book) {
                    Book book = (Book) value;
                    setText(book.title + " by " + book.author);
                }
                return this;
            }
        });

        int result = JOptionPane.showConfirmDialog(
                this,
                bookComboBox,
                "Select a borrowed book to return:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Book selectedBook = (Book) bookComboBox.getSelectedItem();

            if (selectedBook != null) {
                String enteredName = JOptionPane.showInputDialog(this, "Enter borrower name:");
                String enteredContact = JOptionPane.showInputDialog(this, "Enter borrower contact:");

                if (enteredName != null && enteredContact != null &&
                        enteredName.equals(selectedBook.borrowerName) && enteredContact.equals(selectedBook.borrowerContact)) {
                    selectedBook.isAvailable = true;
                    selectedBook.isBorrowed = false;
                    selectedBook.borrowerName = "";
                    selectedBook.borrowerContact = "";

                    JOptionPane.showMessageDialog(this, "Book '" + selectedBook.title + "' returned successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid borrower details. Book not returned.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}

class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (authenticate(username, password)) {
                    dispose(); 
                    showLibraryManagementGUI();
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password. Try again.");
                }
                passwordField.setText("");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); 
        panel.add(loginButton);

        add(panel);
    }

    private boolean authenticate(String username, String password) {

        return username.equals("TEAM10") && password.equals("password");
    }

    private void showLibraryManagementGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LibraryManagementGUI().setVisible(true);
        });
    }
}
