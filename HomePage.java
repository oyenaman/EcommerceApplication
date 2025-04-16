package Online_retail_store;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class HomePage extends JFrame {
    private JTable itemTable;
    private DefaultTableModel cartModel;

    // MySQL database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "Test@123";

    public HomePage() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a table to display items
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Brand", "Price", "Add to Cart"}, 0);
        itemTable = new JTable(model) {
            @Override
            public Class getColumnClass(int column) {
                if (column == 5) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        itemTable.getColumnModel().getColumn(5).setCellRenderer(new RadioButtonRenderer());
        itemTable.getColumnModel().getColumn(5).setCellEditor(new RadioButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(itemTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Fetch items from the database and populate the table
        fetchItems(model);

        // Add "Move to Cart" button
        JButton moveToCartButton = new JButton("Move to Cart");
        moveToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Object[]> selectedItems = new ArrayList<>();
                for (int i = 0; i < itemTable.getRowCount(); i++) {
                    if ((Boolean) itemTable.getValueAt(i, 5)) {
                        Object[] item = new Object[5];
                        for (int j = 0; j < 5; j++) {
                            item[j] = itemTable.getValueAt(i, j);
                        }
                        selectedItems.add(item);
                    }
                }
                cartModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Brand", "Price"}, 0);
                for (Object[] item : selectedItems) {
                    cartModel.addRow(item);
                }
                dispose();
                new CartPage(cartModel).setVisible(true);
            }
        });
        panel.add(moveToCartButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void fetchItems(DefaultTableModel model) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM items";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String brand = resultSet.getString("brand");
                double price = resultSet.getDouble("price");
                model.addRow(new Object[]{id, name, category, brand, price, false});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch items!");
        }
    }

    class RadioButtonRenderer extends JRadioButton implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setSelected((Boolean) value);
            }
            return this;
        }
    }

    class RadioButtonEditor extends DefaultCellEditor {
        public RadioButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value != null) {
                ((JCheckBox) getComponent()).setSelected((Boolean) value);
            }
            return getComponent();
        }

        public Object getCellEditorValue() {
            return ((JCheckBox) getComponent()).isSelected();
        }
    }

    public static void main(String[] args) {
        // Register the MySQL JDBC driver (implementation omitted)

        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
