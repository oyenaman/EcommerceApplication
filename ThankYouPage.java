package Online_retail_store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThankYouPage extends JFrame {
    private JButton backButton;
    private JButton exitButton;

    public ThankYouPage() {
        setTitle("Thank You Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add "Thank you" label in the center
        JLabel thankYouLabel = new JLabel("Thank You For Shopping! Do Visit Again :)");
        thankYouLabel.setHorizontalAlignment(JLabel.CENTER);
        thankYouLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(thankYouLabel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Add "Back to Home" button
        backButton = new JButton("Continue Shopping");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new HomePage(); // Assuming you have a HomePage class
                    }
                });
            }
        });
        buttonPanel.add(backButton);

        // Add "Exit" button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ThankYouPage();
            }
        });
    }
}
