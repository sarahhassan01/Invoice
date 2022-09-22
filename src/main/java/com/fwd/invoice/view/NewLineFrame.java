package com.fwd.invoice.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class NewLineFrame extends JDialog {

    private JLabel ProductNamelbl;
    private JLabel ProductPricelbl;
    private JLabel Quantitylbl;

    public JTextField ProductNametxt;
    public JTextField ProductValutxt;
    public JTextField Quantitytxt;

    private JButton okbtn;
    private JButton cancelbtn;

    public NewLineFrame(MainFrame frame) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        ProductNamelbl = new JLabel("  Item Name :");
        ProductPricelbl = new JLabel("  Product Price :");
        Quantitylbl = new JLabel("  Count :");
        
        ProductNametxt = new JTextField(30);
        ProductValutxt = new JTextField(20);
        Quantitytxt = new JTextField(20);

        okbtn = new JButton("Ok");
        cancelbtn = new JButton("Cancel");

        okbtn.addActionListener(frame.getMyListner());
        cancelbtn.addActionListener(frame.getMyListner());
        okbtn.setActionCommand("Ok");
        cancelbtn.setActionCommand("Cancel Adding");
        
        
        add(ProductNamelbl);
        add(ProductNametxt);
        add(ProductPricelbl);
        add(ProductValutxt);
        add(Quantitylbl);
        add(Quantitytxt);
        add(okbtn);
        add(cancelbtn);
        
        setModal(true);
        setLocation(200, 100);
        pack();
    }

}
