package Online_retail_store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField pinCodeField;
    private JButton submitButton;

    // Role selection radio buttons
    private JRadioButton adminRadioButton;
    private JRadioButton customerRadioButton;

    // MySQL database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "Test@123";

    public SignupPage() {
        setTitle("Signup Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField();
        JLabel stateLabel = new JLabel("State:");
        stateField = new JTextField();
        JLabel pinCodeLabel = new JLabel("Pin Code:");
        pinCodeField = new JTextField();

        // Role selection
        JLabel roleLabel = new JLabel("Select Role:");
        adminRadioButton = new JRadioButton("Admin");
        customerRadioButton = new JRadioButton("Customer");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadioButton);
        roleGroup.add(customerRadioButton);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String address = addressField.getText();
                String city = cityField.getText();
                String state = stateField.getText();
                String pinCode = pinCodeField.getText();
                String role = adminRadioButton.isSelected() ? "Admin" : "Customer";

                if (registerUser(name, email, password, address, city, state, pinCode, role)) {
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new LoginPage(); // Navigate to the login page
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to register user. Please try again.");
                }
            }
        });

        panel.add(roleLabel);
        panel.add(adminRadioButton);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(customerRadioButton);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(stateLabel);
        panel.add(stateField);
        panel.add(pinCodeLabel);
        panel.add(pinCodeField);
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    private boolean registerUser(String name, String email, String password, String address, String city, String state, String pinCode, String role) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "INSERT INTO users (name, email, password, address, city, state, pin_code, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, pinCode);
            preparedStatement.setString(8, role);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to register user!");
            return false;
        }
    }

    public static void main(String[] args) {
        // Register the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupPage();
            }
        });
    }
}
