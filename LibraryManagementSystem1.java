import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LibraryManagementSystem1{

    // JDBC connection details
    static final String URL = "jdbc:mysql://localhost:3306/LIBMANAGE?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root"; 
    static final String PASS = "!@#$PRACHI%^&*2007"; 
    
    private Connection conn;
    private boolean isAuthenticated = false;  

    // Constructor to initialize the database connection
    public LibraryManagementSystem1() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }

    // Method to authenticate admin login
    public boolean authenticateAdmin(String USER_ID, String PASSWORD) {
        String query = "SELECT * FROM ADMININFO WHERE USER_ID = ? AND PASSWORD = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, USER_ID);
            pst.setString(2, PASSWORD);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error during authentication: " + e.getMessage());
        }
        return false;
    }

 public void showWelcomePage() {
    JFrame welcomeFrame = new JFrame("Library System - Welcome");
    welcomeFrame.setSize(400, 250);
    welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    welcomeFrame.setLayout(null);

    // Welcome message
    JLabel welcomeLabel = new JLabel("Select Login Type", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
    welcomeLabel.setBounds(50, 30, 300, 30);

    // Buttons for Admin and User login
    JButton adminButton = new JButton("Login as Admin");
    adminButton.setBounds(100, 80, 200, 40);
    adminButton.addActionListener(e -> {
        welcomeFrame.dispose(); // Close this window
        showLoginPage(); // Open Admin login
    });

    JButton userButton = new JButton("Login as User");
    userButton.setBounds(100, 130, 200, 40);
    userButton.addActionListener(e -> {
     //  welcomeFrame.dispose(); // Close this window
       // JOptionPane.showMessageDialog(null, "User login is not available at the moment.");
     welcomeFrame.dispose(); // Close welcome page
    showUserLoginPage(); // Open User login page
    });

    // Add components to frame
    welcomeFrame.add(welcomeLabel);
    welcomeFrame.add(adminButton);
   welcomeFrame.add(userButton);

    // Ensure frame is visible
    welcomeFrame.setVisible(true);
}

    // Method to show login page
   // Add this modified method inside your LibraryManagementSystem class

public void showLoginPage() {
    JFrame loginFrame = new JFrame("Admin Login");
    loginFrame.setSize(400, 300);
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginFrame.setLayout(null);

    // Load background image
    java.net.URL imageUrl = getClass().getResource("/background.png");
    ImageIcon originalIcon = null;

    if (imageUrl != null) {
        originalIcon = new ImageIcon(imageUrl);
    } else {
        originalIcon = new ImageIcon("C:/LIBRARYPROJECT/background.png"); // Change path
    }

    if (originalIcon == null) {
        JOptionPane.showMessageDialog(null, "Background image not found!");
        return;
    }

    // Scale and display the background
    Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JLabel backgroundLabel = new JLabel(scaledIcon);
    backgroundLabel.setBounds(0, 0, 400, 300);

    // Transparent login form
    JPanel panel = new JPanel(new GridLayout(3, 2));
    panel.setBounds(50, 80, 300, 120);
    panel.setOpaque(false);

    // Create labels with different colors
    JLabel usernameLabel = new JLabel("USERNAME:");
    usernameLabel.setForeground(Color.BLUE); // Blue color for username

    JLabel passwordLabel = new JLabel("PASSWORD:");
    passwordLabel.setForeground(Color.BLUE); // Red color for password

    JTextField USERNAMEField = new JTextField();
    JPasswordField PASSWORDField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    panel.add(usernameLabel);
    panel.add(USERNAMEField);
    panel.add(passwordLabel);
    panel.add(PASSWORDField);
    panel.add(new JLabel(""));
    panel.add(loginButton);

    // Layered pane to keep the background and panel separate
    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 400, 300);
    layeredPane.add(backgroundLabel, Integer.valueOf(0));
    layeredPane.add(panel, Integer.valueOf(1));

    loginButton.addActionListener(e -> {
        String USERNAME = USERNAMEField.getText();
        String PASSWORD = new String(PASSWORDField.getPassword());

        if (authenticateAdmin(USERNAME, PASSWORD)) {
            isAuthenticated = true;
            JOptionPane.showMessageDialog(null, "Login successful!");
            loginFrame.dispose();
            createAndShowGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    });

    loginFrame.setContentPane(layeredPane);
    loginFrame.setVisible(true);
}

    // Method to create and display the main library management system after successful login
    public void createAndShowGUI() {
        if (!isAuthenticated) {
            JOptionPane.showMessageDialog(null, "You need to login first.");
            return;
        }

        JFrame frame = new JFrame("Library Management System");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));

        // Form components
        JTextField ACC_NOField = new JTextField();
        JTextField AUTHORField = new JTextField();
        JTextField TITLEField = new JTextField();
        JTextField SUBJECTField = new JTextField();
        JTextField PUBLISHERField = new JTextField();
        JTextField REG_NOField = new JTextField();

        JTextField STAFF_IDField = new JTextField();
        JTextField NAMEField = new JTextField();
        JTextField JOBField = new JTextField();
        JTextField SALARYField = new JTextField();
        JTextField CONTACTField = new JTextField();

        // Adding labels and fields
        panel.add(new JLabel("ACC_NO:"));
        panel.add(ACC_NOField);
        panel.add(new JLabel("STAFF_ID:"));
        panel.add(STAFF_IDField);
        panel.add(new JLabel("AUTHOR:"));
        panel.add(AUTHORField);
        panel.add(new JLabel("NAME:"));
        panel.add(NAMEField);
        panel.add(new JLabel("TITLE:"));
        panel.add(TITLEField);
        panel.add(new JLabel("JOB:"));
        panel.add(JOBField);
        panel.add(new JLabel("SUBJECT:"));
        panel.add(SUBJECTField);
        panel.add(new JLabel("SALARY:"));
        panel.add(SALARYField);
        panel.add(new JLabel("PUBLISHER:"));
        panel.add(PUBLISHERField);
        panel.add(new JLabel("CONTACT:"));
        panel.add(CONTACTField);
        panel.add(new JLabel("REG_NO:"));
        panel.add(REG_NOField);

        
      //borrow code 
   
//BORROW_USER_IDField.setPreferredSize(new Dimension(200, 25));
//BORROW_ACC_NOField.setPreferredSize(new Dimension(200, 25));

//panel.add(new JLabel("Borrow - USER_ID:"));
//panel.add(BORROW_USER_IDField);

//panel.add(new JLabel("Borrow - ACC_NO:"));
//panel.add(BORROW_ACC_NOField);

        JButton addBookButton = new JButton("Add Book");
        JButton listBooksButton = new JButton("List Books");
        JButton removeBookButton = new JButton("Remove Book");

        JButton addStaffButton = new JButton("Add Staff");
        JButton listStaffButton = new JButton("List Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        JButton updateStaffButton = new JButton("Update Staff");
       JButton updateBookButton = new JButton("Update Book");

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        addBookButton.addActionListener(e -> addBook(ACC_NOField.getText(), AUTHORField.getText(), TITLEField.getText(), SUBJECTField.getText(), PUBLISHERField.getText(), REG_NOField.getText()));
        listBooksButton.addActionListener(e -> textArea.setText(listBooks()));
        removeBookButton.addActionListener(e -> removeBook(JOptionPane.showInputDialog("Enter ACC_NO to remove:")));
updateBookButton.addActionListener(e -> {
    updateBook(
       ACC_NOField.getText().trim(),
        TITLEField.getText().trim(),
        AUTHORField.getText().trim(),
        SUBJECTField.getText().trim(),
        PUBLISHERField.getText().trim(),
        REG_NOField.getText().trim()
    );
});


        addStaffButton.addActionListener(e -> addStaff(STAFF_IDField.getText().trim(), NAMEField.getText(), JOBField.getText(), SALARYField.getText(), CONTACTField.getText()));
        listStaffButton.addActionListener(e -> textArea.setText(listStaff()));
        removeStaffButton.addActionListener(e -> removeStaff(JOptionPane.showInputDialog("Enter STAFF_ID to remove:")));
        updateStaffButton.addActionListener(e -> {
    updateStaff(
        STAFF_IDField.getText().trim(),
        NAMEField.getText(),
        JOBField.getText(),
        SALARYField.getText(),
        CONTACTField.getText()
    );
});


       // Panel for buttons with a FlowLayout to keep them in a single row
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-aligned with spacing
buttonPanel.add(addBookButton);
buttonPanel.add(listBooksButton);
buttonPanel.add(removeBookButton);
buttonPanel.add(updateBookButton);
buttonPanel.add(addStaffButton);
buttonPanel.add(listStaffButton);
buttonPanel.add(removeStaffButton);
buttonPanel.add(updateStaffButton);


// Add components to the frame
frame.add(panel, BorderLayout.NORTH);
frame.add(scrollPane, BorderLayout.CENTER); // Keep records view in center
frame.add(buttonPanel, BorderLayout.SOUTH); // Place buttons in a single line

frame.setVisible(true);

    }

    // Method to add book

    public void addBook(String ACC_NO, String AUTHOR, String TITLE, String SUBJECT, String PUBLISHER, String REG_NO) {
    // Ensure ACC_NO (Primary Key) is not NULL or empty
    if (ACC_NO == null || ACC_NO.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "ACC_NO cannot be NULL or empty.");
        return;  // Stop execution if ACC_NO is invalid
    }

    String query = "INSERT INTO BOOKSINFO (ACC_NO, AUTHOR, TITLE, SUBJECT, PUBLISHER, REG_NO) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, ACC_NO);  // Ensure ACC_NO is always set
        pst.setString(2, AUTHOR);
        pst.setString(3, TITLE);
        pst.setString(4, SUBJECT);
        pst.setString(5, PUBLISHER);
        pst.setString(6, REG_NO);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
    }
}

    // Method to list books
    public String listBooks() {
        StringBuilder booksList = new StringBuilder();
        String query = "SELECT * FROM BOOKSINFO";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                booksList.append("ACC_NO: ").append(rs.getString("ACC_NO"))
                         .append(", AUTHOR: ").append(rs.getString("AUTHOR"))
                         .append(", TITLE: ").append(rs.getString("TITLE"))
                         .append(", SUBJECT: ").append(rs.getString("SUBJECT"))
                         .append(", PUBLISHER: ").append(rs.getString("PUBLISHER"))
                        .append(", REG_NO: ").append(rs.getString("REG_NO"))

                         .append("\n");
            }
        } catch (SQLException e) {
            return "Error listing books: " + e.getMessage();
        }
        return booksList.toString();
    }

    // Method to remove book
    public void removeBook(String ACC_NO) {
        String query = "DELETE FROM BOOKSINFO WHERE ACC_NO = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, ACC_NO);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book removed successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing book: " + e.getMessage());
        }
    }

    public boolean isStaffIDExists(String STAFF_ID) {
    String query = "SELECT STAFF_ID FROM STAFFSINFO WHERE STAFF_ID = ?";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, STAFF_ID);
        ResultSet rs = pst.executeQuery();
        return rs.next(); // If result exists, STAFF_ID is already in use
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error checking STAFF_ID: " + e.getMessage());
    }
    return false;
}


    // Corrected addStaff method

public void addStaff(String STAFF_ID, String NAME, String JOB, String SALARY, String CONTACT) {
    // SQL query for inserting staff data
    String query = "INSERT INTO STAFFSINFO (STAFF_ID, NAME, JOB, SALARY, CONTACT) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement pst = conn.prepareStatement(query)) {
        // Insert the STAFF_ID, NAME, JOB, SALARY, and CONTACT
        pst.setString(1, STAFF_ID);  // STAFF_ID should not be NULL
        pst.setString(2, NAME);      // NAME should not be NULL
        pst.setString(3, JOB);       // JOB should not be NULL
        pst.setString(4, SALARY);    // SALARY should not be NULL
        pst.setString(5, CONTACT);   // CONTACT should not be NULL

        // Execute the insert query
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Staff added successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error adding staff: " + e.getMessage());
    }
}




// Corrected listStaff method
public String listStaff() {
    StringBuilder staffsList = new StringBuilder();
    String query = "SELECT * FROM STAFFSINFO"; // Ensure the table name is correct
    try (Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            staffsList.append("STAFF_ID: ").append(rs.getString("STAFF_ID"))
                    .append(", NAME: ").append(rs.getString("NAME"))
                    .append(", JOB: ").append(rs.getString("JOB"))
                    .append(", SALARY: ").append(rs.getString("SALARY"))
                    .append(", CONTACT: ").append(rs.getString("CONTACT"))
                    .append("\n");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error listing staff: " + e.getMessage()); // Fixed message
    }
    return staffsList.toString();
}
public void updateStaff(String STAFF_ID, String NAME, String JOB, String SALARY, String CONTACT) {
    String query = "UPDATE STAFFSINFO SET NAME = ?, JOB = ?, SALARY = ?, CONTACT = ? WHERE STAFF_ID = ?";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, NAME);
        pst.setString(2, JOB);
        pst.setString(3, SALARY);
        pst.setString(4, CONTACT);
        pst.setString(5, STAFF_ID);

        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Staff record updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "No staff found with the given ID.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error updating staff: " + e.getMessage());
    }
}
public void updateBook(String ACC_NO, String TITLE, String AUTHOR, String SUBJECT, String PUBLISHER, String REG_NO) {
    String query = "UPDATE BOOKSINFO SET TITLE = ?, AUTHOR = ?, SUBJECT = ?, PUBLISHER = ?, REG_NO = ? WHERE ACC_NO= ?";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, TITLE);
        pst.setString(2, AUTHOR);
        pst.setString(3, SUBJECT);
        pst.setString(4, PUBLISHER);
        pst.setString(5, REG_NO);
        pst.setString(6, ACC_NO);

        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Book updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "No book found with the given ID.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error updating book: " + e.getMessage());
    }
}


// Corrected removeStaff method
public void removeStaff(String STAFF_ID) {
    String query = "DELETE FROM STAFFSINFO WHERE STAFF_ID = ?"; // Fixed table name
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, STAFF_ID);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Staff removed successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error removing staff: " + e.getMessage());
    }
}
//user registration
public void registerUser(String userId, String password, String name, String email) {
    String query = "INSERT INTO USERINFO (USER_ID, PASSWORD, NAME, EMAIL) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, userId);
        pst.setString(2, password);
        pst.setString(3, name);
        pst.setString(4, email);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "User registered successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error registering user: " + e.getMessage());
    }
}
//user login
public boolean authenticateUser(String userId, String password) {
    String query = "SELECT * FROM USERINFO WHERE USER_ID = ? AND PASSWORD = ?";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, userId);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error during user login: " + e.getMessage());
    }
    return false;
}
//borrow book
public void borrowBook(String userId, String accNo) {
    String query = "INSERT INTO BORROWINFO (USER_ID, ACC_NO, BORROW_DATE, DUE_DATE) VALUES (?, ?, ?, ?)";
    LocalDate today = LocalDate.now();
    LocalDate dueDate = today.plusDays(14);

    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, userId);
        pst.setString(2, accNo);
        pst.setDate(3, Date.valueOf(today));
        pst.setDate(4, Date.valueOf(dueDate));
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Book borrowed! Due date: " + dueDate);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error borrowing book: " + e.getMessage());
    }
}
//view borrow book
public String getUserBorrowedBooks(String userId) {
    StringBuilder result = new StringBuilder();
    String query = "SELECT * FROM BORROWINFO WHERE USER_ID = ?";
    try (PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            result.append("Book ACC_NO: ").append(rs.getString("ACC_NO"))
                  .append(", Borrowed on: ").append(rs.getDate("BORROW_DATE"))
                  .append(", Due by: ").append(rs.getDate("DUE_DATE"))
                  .append("\n");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching borrowed books: " + e.getMessage());
    }
    return result.toString();
}


public void showUserLoginPage() {
    JFrame userLoginFrame = new JFrame("User Login");
    userLoginFrame.setSize(400, 300);
    userLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    userLoginFrame.setLayout(null);

    JLabel userLabel = new JLabel("User ID:");
    userLabel.setBounds(50, 60, 100, 30);
    JTextField userField = new JTextField();
    userField.setBounds(150, 60, 180, 30);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 100, 100, 30);
    JPasswordField passField = new JPasswordField();
    passField.setBounds(150, 100, 180, 30);

    JButton loginBtn = new JButton("Login");
    loginBtn.setBounds(50, 150, 120, 30);

    JButton registerBtn = new JButton("Register");
    registerBtn.setBounds(210, 150, 120, 30);

    userLoginFrame.add(userLabel);
    userLoginFrame.add(userField);
    userLoginFrame.add(passLabel);
    userLoginFrame.add(passField);
    userLoginFrame.add(loginBtn);
    userLoginFrame.add(registerBtn);

    loginBtn.addActionListener(e -> {
        String userId = userField.getText();
        String password = new String(passField.getPassword());
        if (authenticateUser(userId, password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            userLoginFrame.dispose();
            showUserDashboard(userId);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials!");
        }
    });

    registerBtn.addActionListener(e -> {
        userLoginFrame.dispose();
        showUserRegistrationPage();
    });

    userLoginFrame.setVisible(true);
}


public void showUserRegistrationPage() {
    JFrame regFrame = new JFrame("User Registration");
    regFrame.setSize(400, 350);
    regFrame.setLayout(null);

    JLabel userLabel = new JLabel("User ID:");
    userLabel.setBounds(50, 40, 100, 30);
    JTextField userField = new JTextField();
    userField.setBounds(150, 40, 180, 30);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 80, 100, 30);
    JPasswordField passField = new JPasswordField();
    passField.setBounds(150, 80, 180, 30);

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setBounds(50, 120, 100, 30);
    JTextField nameField = new JTextField();
    nameField.setBounds(150, 120, 180, 30);

    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setBounds(50, 160, 100, 30);
    JTextField emailField = new JTextField();
    emailField.setBounds(150, 160, 180, 30);

    JButton registerBtn = new JButton("Register");
    registerBtn.setBounds(130, 220, 120, 30);

    registerBtn.addActionListener(e -> {
        registerUser(userField.getText(), new String(passField.getPassword()), nameField.getText(), emailField.getText());
        regFrame.dispose();
        showUserLoginPage();
    });

    regFrame.add(userLabel);
    regFrame.add(userField);
    regFrame.add(passLabel);
    regFrame.add(passField);
    regFrame.add(nameLabel);
    regFrame.add(nameField);
    regFrame.add(emailLabel);
    regFrame.add(emailField);
    regFrame.add(registerBtn);

    regFrame.setVisible(true);
}


public void showUserDashboard(String userId) {
    JFrame dashboard = new JFrame("User Dashboard");
    dashboard.setSize(500, 400);
    dashboard.setLayout(new BorderLayout());

    JTextField accField = new JTextField();

accField.setFont(new Font("Arial", Font.PLAIN, 20)); // Bigger font
accField.setPreferredSize(new Dimension(300, 40)); // Wider and taller field

JPanel panel = new JPanel();
panel.add(new JLabel("Enter Account Number:"));
panel.add(accField);

    JButton borrowBtn = new JButton("Borrow Book");
    JButton viewBtn = new JButton("View Borrowed Books");

    JTextArea resultArea = new JTextArea();
    resultArea.setEditable(false);

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());
    topPanel.add(new JLabel("Enter ACC_NO:"));
    topPanel.add(accField);
    topPanel.add(borrowBtn);
    topPanel.add(viewBtn);

    JScrollPane scrollPane = new JScrollPane(resultArea);

    borrowBtn.addActionListener(e -> {
        String accNo = accField.getText().trim();
        if (!accNo.isEmpty()) {
            borrowBook(userId, accNo);
        } else {
            JOptionPane.showMessageDialog(null, "Enter ACC_NO!");
        }
    });

    viewBtn.addActionListener(e -> {
        resultArea.setText(getUserBorrowedBooks(userId));
    });

    dashboard.add(topPanel, BorderLayout.NORTH);
    dashboard.add(scrollPane, BorderLayout.CENTER);
    dashboard.setVisible(true);
}


    public static void main(String[] args) {
       new LibraryManagementSystem1().showWelcomePage();

    }
}
