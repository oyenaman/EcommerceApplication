package Online_retail_store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddItemPage extends JFrame {
    private JTextField idField;
    private JTextField categoryField;
    private JTextField brandField;
    private JTextField nameField;
    private JTextField priceField;
    private JButton submitButton;

    // MySQL database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "Test@123";

    public AddItemPage() {
        setTitle("Add Item Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField();
        JLabel brandLabel = new JLabel("Brand:");
        brandField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String category = categoryField.getText();
                String brand = brandField.getText();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    String query = "INSERT INTO items (id, category, brand, name, price) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, category);
                    preparedStatement.setString(3, brand);
                    preparedStatement.setString(4, name);
                    preparedStatement.setDouble(5, price);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Item added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to add item!");
                }
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(brandLabel);
        panel.add(brandField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(submitButton);

        add(panel);
        setVisible(true);
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
                new AddItemPage();
            }
        });
    }
}
