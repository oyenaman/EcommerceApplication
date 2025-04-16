package Online_retail_store;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class CartPage extends JFrame {
    private JTable cartTable;
    private DefaultTableModel cartModel;

    public CartPage(DefaultTableModel model) {
        setTitle("Cart Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        cartModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Brand", "Price", "Quantity"}, 0);
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] item = new Object[6];
            for (int j = 0; j < 5; j++) {
                item[j] = model.getValueAt(i, j);
            }
            item[5] = 1; // Default quantity is set to 1
            cartModel.addRow(item);
        }

        cartTable = new JTable(cartModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton moveToCheckoutButton = new JButton("Move to Checkout");
        moveToCheckoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CheckoutPage(cartModel).setVisible(true);
            }
        });
        panel.add(moveToCheckoutButton, BorderLayout.SOUTH);

        // Add a button to set quantity
        JButton setQuantityButton = new JButton("Set Quantity");
        setQuantityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    String input = JOptionPane.showInputDialog("Enter quantity:");
                    try {
                        int quantity = Integer.parseInt(input);
                        if (quantity > 0) {
                            cartModel.setValueAt(quantity, selectedRow, 5);
                        } else {
                            JOptionPane.showMessageDialog(null, "Quantity must be greater than 0!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to set quantity!");
                }
            }
        });
        panel.add(setQuantityButton, BorderLayout.NORTH);

        add(panel);
        setVisible(true);
    }
}
