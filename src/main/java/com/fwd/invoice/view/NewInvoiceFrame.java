/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fwd.invoice.view;

import java.awt.Dialog;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NewInvoiceFrame extends JDialog {

    private JLabel invNumlbl;
    public JLabel invNumtxt;
    private JLabel cusNamelbl;
    public JTextField cusNametxt;

    private JLabel datelbl;
    public JTextField datetxt;

    private JButton createInvbtn;
    private JButton cancelInvbtn;

    public NewInvoiceFrame(MainFrame frame) {


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        invNumlbl = new JLabel("  Invoice Number :");
        invNumtxt = new JLabel();
        datelbl = new JLabel("  Date :");
        cusNamelbl = new JLabel("  Customer Name :");

        datetxt = new JTextField(20);
        cusNametxt = new JTextField(20);

        createInvbtn = new JButton("Create");
        cancelInvbtn = new JButton("Cancel");

        createInvbtn.addActionListener(frame.getMyListner());
        cancelInvbtn.addActionListener(frame.getMyListner());
        createInvbtn.setActionCommand("Create Header");
        cancelInvbtn.setActionCommand("Cancel Header");

        add(invNumlbl);
        add(invNumtxt);
        add(datelbl);
        add(datetxt);
        add(cusNamelbl);
        add(cusNametxt);
        add(createInvbtn);
        add(cancelInvbtn);

        setModal(true);
        setLocation(200, 100);
        pack();
    }

}
