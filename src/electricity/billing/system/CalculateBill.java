package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculateBill extends JFrame implements ActionListener{

    JTextField tfprev,tfcurr;
    JButton next, cancel;
    JLabel lblname, labeladdress,lblun,lblpv;
    Choice meternumber, cmonth;
    CalculateBill() {
        setSize(700, 500);
        setLocation(400, 150);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);
        
        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(100, 10, 400, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
        
        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(100, 80, 100, 20);
        p.add(lblmeternumber);
        
        meternumber = new Choice();
        
        try {
            Conn c  = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            meternumber.add("--Select--");
            while(rs.next()) {
                meternumber.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        meternumber.setBounds(240, 80, 200, 20);
        p.add(meternumber);
        
        JLabel lblmeterno = new JLabel("Name");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        lblname = new JLabel("");
        lblname.setBounds(240, 120, 100, 20);
        p.add(lblname);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);
        
        labeladdress = new JLabel();
        labeladdress.setBounds(240, 160, 200, 20);
        p.add(labeladdress);
        
        JLabel lblprev = new JLabel("Previous");
        lblprev.setBounds(100, 240, 100, 20);
        p.add(lblprev);
        
        lblpv = new JLabel();
        lblpv.setBounds(240, 240, 200, 20);
        p.add(lblpv);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
            while(rs.next()) {
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        meternumber.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
                     
                    while(rs.next()) {
//                        ResultSet r = c.s.executeQuery("select * from readings where meter_no = '"+meternumber.getSelectedItem()+"'");
                        lblname.setText(rs.getString("name"));
                        labeladdress.setText(rs.getString("address"));
                        lblpv.setText(rs.getString("prev"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //here
       
        //to here
//        JLabel lblcity = new JLabel("Units Consumed");
//        lblcity.setBounds(100, 200, 100, 20);
//        p.add(lblcity);
//        
//        tfunits = new JTextField();
//        tfunits.setBounds(240, 200, 200, 20);
//        p.add(tfunits);
        
        JLabel lblstate = new JLabel("Month");
        lblstate.setBounds(100, 200, 100, 20);
        p.add(lblstate);
        
        cmonth = new Choice();
        cmonth.setBounds(240, 200, 200, 20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        p.add(cmonth);
        
        
        
        
        
        JLabel lblcurr = new JLabel("Current");
        lblcurr.setBounds(100, 280, 100, 20);
        p.add(lblcurr);
        
        tfcurr = new JTextField();
        tfcurr.setBounds(240, 280, 200, 20);
        p.add(tfcurr);
        
        JLabel lblunits = new JLabel("Units Consumed");
        lblunits.setBounds(100, 320, 100, 20);
        p.add(lblunits);
        
        lblun = new JLabel("");
        lblun.setBounds(240, 320, 200, 20);
        p.add(lblun);
        
        
        tfcurr.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        try {
            int prev = Integer.parseInt(lblpv.getText());
            int curr = Integer.parseInt(tfcurr.getText());
            if (curr > prev) {
                int unit = curr - prev;
                lblun.setText(Integer.toString(unit));
            } else {
                lblun.setText("Invalid input");
            }
        } catch (NumberFormatException ex) {
            lblun.setText("");
        }
    }
});
        
//        lblun.setText(tfprev.getText());
        
//        cmonth.addItemListener(new ItemListener() {
//    public void itemStateChanged(ItemEvent ie) {
//        Random ran = new Random();
//        int number = ran.nextInt(500) % 1000000;
//        lblun.setText("");
//    }
//});
        
        next = new JButton("Submit");
        next.setBounds(120, 350, 100,25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 350, 100,25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String meter = meternumber.getSelectedItem();
            String units = lblun.getText();
            String month = cmonth.getSelectedItem();
            float factor=1f;
            float min=80;
            
            Conn c = new Conn();  
            try {
                ResultSet rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month='"+month+"' and status='Not Paid'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Bill for "+month+" is already generated!");
                    return;
                }
                ResultSet rs1 = c.s.executeQuery("select * from meter_info where meter_no = '"+meternumber.getSelectedItem()+"'");
                if(rs1.getString("meter_locationn")=="Urban")
                {   
                    factor+=0.1;
                    min=100;
                }
                if(rs1.getString("phase_code")=="4.6-33KV(HT)")
                    factor+=0.63;
                else if(rs1.getString("phase_code")=="66-400KV(EHT)")
                    factor+=0.81;
                if(rs1.getString("bill_type")=="Industrial")
                {
                    factor-=0.2;
                    min+=50;
                }
                if(rs1.getString("meter_type")=="Three Phase")
                {
                    factor+=0.18;
                    min+=47;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CalculateBill.class.getName()).log(Level.SEVERE, null, ex);
            }
                 
            float totalbill = 0;
            float unit_consumed = Integer.parseInt(units);
            
            String query = "select * from tax";
            
            try {
                
                ResultSet rs = c.s.executeQuery(query);
                
                while(rs.next()) {
//                   
                    
                    if (unit_consumed <= 50) {
                        totalbill = unit_consumed * 3.6f;
                    } else if (unit_consumed <= 100) {
                        totalbill = 50 * 3.6f + (unit_consumed - 50) * 5.4f;
                    } else {
                        totalbill = 50 * 3.6f + 50 * 5.4f + (unit_consumed - 100) * 7.0f;
                    }
                    totalbill += Integer.parseInt(rs.getString("meter_rent"));
                    totalbill += Integer.parseInt(rs.getString("service_charge"));
                    totalbill += Integer.parseInt(rs.getString("service_tax"));
                    totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                    totalbill += Integer.parseInt(rs.getString("fixed_tax"));
                    totalbill*=factor;
                    totalbill+=min;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int curr=Integer.parseInt(tfcurr.getText());
            String query2 = "insert into bill values('"+meter+"', '"+month+"', '"+units+"', '"+totalbill+"', 'Not Paid')";
            String query3 = "UPDATE customer SET prev='"+curr+"' WHERE meter_no='"+meter+"';";
           
            try {
               
                c.s.executeUpdate(query2);
                c.s.executeUpdate(query3);
                
                JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new CalculateBill();
    }
}