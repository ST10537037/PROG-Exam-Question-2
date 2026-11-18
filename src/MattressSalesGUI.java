
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class MattressSalesGUI extends JFrame {

    private JTextField txtCustomer;
    private JTextField txtPrice;
    private JComboBox<String> cmbProduct;
    private JButton btnProcess;

    public MattressSalesGUI() {

        setTitle("Product App");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(
                30, 20, 30, 20));

        JLabel lblCustomer = new JLabel("Customer Name:");
        txtCustomer = new JTextField();

        JLabel lblProduct = new JLabel("Select Product:");

        String[] products = {
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
        };

        cmbProduct = new JComboBox<>(products);

        JLabel lblPrice = new JLabel("Enter Price:");
        txtPrice = new JTextField();

        btnProcess = new JButton("PROCESS");

        panel.add(lblCustomer);
        panel.add(txtCustomer);

        panel.add(lblProduct);
        panel.add(cmbProduct);

        panel.add(lblPrice);
        panel.add(txtPrice);

        panel.add(new JLabel(""));
        panel.add(btnProcess);

        add(panel);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);

        // Button and menu actions
        btnProcess.addActionListener(e -> processSale());

        processItem.addActionListener(e -> processSale());

        clearItem.addActionListener(e -> clearFields());

        exitItem.addActionListener(e -> System.exit(0));
    }

    private void processSale() {

        String customer = txtCustomer.getText().trim();
        String product =
                (String) cmbProduct.getSelectedItem();

        if (customer.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter customer name.");
            return;
        }

        try {

            double price = Double.parseDouble(
                    txtPrice.getText().trim());

            if (!Double.isFinite(price) || price <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid positive price.");
                return;
            }

            ProductCalculations calc =
                    new ProductCalculations(price);

            double vat = calc.GetVat();
            double commission = calc.GetCommission();
            double total = calc.GetTotal();

            String result = String.format(
                    "Customer Name: %s%n" +
                            "Product: %s%n" +
                            "VAT: R %.2f%n" +
                            "COMMISSION: R %.2f%n" +
                            "TOTAL: R %.2f",
                    customer, product, vat,
                    commission, total);

            // Save to data.txt
            try (FileWriter writer =
                         new FileWriter("data.txt", true)) {

                writer.write("DATA LOG\n");
                writer.write(
                        "********************************\n");
                writer.write(result + "\n");
                writer.write(
                        "********************************\n\n");

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(this,
                        "Error saving file: "
                                + ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Please enter a valid numeric price.");
        }
    }

    private void clearFields() {

        txtCustomer.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
        txtCustomer.requestFocusInWindow();
    }
}

