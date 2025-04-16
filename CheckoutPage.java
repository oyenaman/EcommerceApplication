package Online_retail_store;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class CheckoutPage extends JFrame {
    private JTable checkoutTable;
    private DefaultTableModel checkoutModel;

    public CheckoutPage(DefaultTableModel model) {
        setTitle("Checkout Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        checkoutModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Brand", "Price", "Quantity"}, 0);
        double grandTotal = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] item = new Object[6];
            for (int j = 0; j < 5; j++) {
                item[j] = model.getValueAt(i, j);
            }
            item[5] = model.getValueAt(i, 5); // Quantity remains the same
            checkoutModel.addRow(item);

            double price = Double.parseDouble(model.getValueAt(i, 4).toString());
            int quantity = Integer.parseInt(model.getValueAt(i, 5).toString());
            grandTotal += price * quantity;
        }

        checkoutTable = new JTable(checkoutModel);
        JScrollPane scrollPane = new JScrollPane(checkoutTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel totalLabel = new JLabel("Grand Total: ₹⟩" + grandTotal);
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(totalLabel);
        panel.add(totalPanel, BorderLayout.NORTH);

        JButton moveToThankYouButton = new JButton("Proceed to Pay");
        moveToThankYouButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ThankYouPage().setVisible(true);
            }
        });
        panel.add(moveToThankYouButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
