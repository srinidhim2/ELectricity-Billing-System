package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteCustomer extends JFrame implements ActionListener {

    JTextField tMeterNumber;
    JButton bDelete, bCancel;

    DeleteCustomer() {
        super("Delete Customer");
        
        JLabel lMeterNumber = new JLabel("Meter Number");
        lMeterNumber.setBounds(40, 20, 100, 30);
        add(lMeterNumber);

        tMeterNumber = new JTextField();
        tMeterNumber.setBounds(150, 20, 150, 30);
        add(tMeterNumber);

        bDelete = new JButton("Delete");
        bDelete.setBounds(40, 70, 100, 30);
        bDelete.addActionListener(this);
        add(bDelete);

        bCancel = new JButton("Cancel");
        bCancel.setBounds(150, 70, 100, 30);
        bCancel.addActionListener(this);
        add(bCancel);

        setSize(350, 150);
        setLocation(600, 300);
        setLayout(null);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bDelete) {
            String meterNumber = tMeterNumber.getText();
            if (meterNumber.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a meter number");
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Conn c = new Conn();
                        String q = "delete from customer where meter_no ="+meterNumber;
                        String q2 = "delete from login where meter_no ="+meterNumber;
                       
                         c.s.executeUpdate(q);
                         c.s.executeUpdate(q2);
                        JOptionPane.showMessageDialog(null, "Customer deleted successfully");
                        this.setVisible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (ae.getSource() == bCancel) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new DeleteCustomer();
    }
}
