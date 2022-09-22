package com.fwd.invoice.controller;

import com.fwd.invoice.model.InvoiceM;
import com.fwd.invoice.model.InvoiceTableM;
import com.fwd.invoice.model.LineM;
import com.fwd.invoice.model.LineTableM;
import com.fwd.invoice.view.MainFrame;
import com.fwd.invoice.view.NewInvoiceFrame;
import com.fwd.invoice.view.NewLineFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class InvoiceContent implements ActionListener, ListSelectionListener {

    private MainFrame frame;
    private NewInvoiceFrame newInvoiceFrame;
    private NewLineFrame newLineFrame;

    public InvoiceContent(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent AE) {

        switch (AE.getActionCommand()) {
            case "Load File":
                loadFile(null, null);
                break;
            case "Save File":
                saveFile();
                break;
            case "New":
                newInvoice();
                break;
            case "Delete":
                deleteInvoice();
                break;
            case "New Product":
                new_Product();
                break;
            case "Delete Product":
                delete_Product();
                break;
            case "Create":
                createHeader();

                break;
            case "Cancel":
                cancelHeader();
                break;
            case "Ok":
                ok();
                break;
            case "Cancel Adding":
                cancelAdding();
                break;

        };
    }

    @Override
    public void valueChanged(ListSelectionEvent LSE) {
        int selectedRow = frame.invTable.getSelectedRow();

        if (selectedRow > -1 && selectedRow < frame.getInvoices().size()) {

            InvoiceM inv = frame.getInvoices().get(selectedRow);

            frame.lblInvNum.setText("" + inv.getinvoice_Number());
            frame.lblInvDate.setText(MainFrame.dFormat.format(inv.getDate()));
            frame.lblCusName.setText(inv.getClient());
            frame.lblInvTotal.setText("" + inv.getCost());

            List<LineM> lines = inv.getLines();
            frame.itemsTable.setModel(new LineTableM(lines));

        } else {
            frame.lblInvNum.setText("");
            frame.lblInvDate.setText("");
            frame.lblCusName.setText("");
            frame.lblInvTotal.setText("");

            frame.itemsTable.setModel(new LineTableM(new ArrayList<LineM>()));

        }
    }

    public void loadFile(String headerPath, String linePath) {

        frame.getInvoices().clear();

        File headerFile = null;
        File lineFile = null;
        if (headerPath == null) {
            JFileChooser fileChooser = new JFileChooser();
            int x = fileChooser.showOpenDialog(frame);
            if (x == JFileChooser.APPROVE_OPTION) {
                headerFile = fileChooser.getSelectedFile();
                x = fileChooser.showOpenDialog(frame);
                if (x == JFileChooser.APPROVE_OPTION) {
                    lineFile = fileChooser.getSelectedFile();
                } else {
                    JOptionPane.showMessageDialog(frame, "Second File Not Found Please Try Again", "Erorr Message", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "First File Not Found Please Try Again", "Erorr Message", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            headerFile = new File(headerPath);
            lineFile = new File(linePath);
        }
        if (headerFile != null && lineFile != null) {
            if (getExtension(lineFile).equals("csv") && getExtension(headerFile).equals("csv")) {
                try {

                    List<String> headerList = Files.lines(Paths.get(headerFile.getAbsolutePath())).collect(Collectors.toList());
                    List<String> lineList = Files.lines(Paths.get(lineFile.getAbsolutePath())).collect(Collectors.toList());

                    int v = 0;
                    int i = 0;
                    for (String headerSt : headerList) {
                        String[] row = headerSt.split(",");
                        String numString = row[0];
                        String dateString = row[1];
                        String customerName = row[2];

                        int num = Integer.parseInt(numString);
                        Date date = frame.dFormat.parse(dateString);

                        InvoiceM inv = new InvoiceM(num, date, customerName);
                        frame.getInvoices().add(inv);
                    }

                    for (String lineSt : lineList) {
                        String[] rowLine = lineSt.split(",");
                        String invNumString = rowLine[0];
                        String itemName = rowLine[1];
                        String itemPrice = rowLine[2];
                        String countString = rowLine[3];

                        int invNum = Integer.parseInt(invNumString);
                        int count = Integer.parseInt(countString);
                        double price = Double.parseDouble(itemPrice);

                        InvoiceM inv = getInvoiveByNum(invNum);
                        LineM line = new LineM(itemName, price, count, inv);
                        inv.getLines().add(line);
                        frame.invTable.setModel(new InvoiceTableM(frame.getInvoices()));

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Wrong Date Format", "Erorr Message", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please Choose a csv File", "Erorr Message", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void saveFile() {
        File headerFile = null;
        File lineFile = null;
        String invoicesData = "";
        String linesData = "";

        for (InvoiceM invoices : frame.getInvoices()) {
            invoicesData += invoices.toCSV();
            invoicesData += "\n";
            for (LineM line : invoices.getLines()) {
                linesData += line.toCSV();
                linesData += "\n";
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        int x = fileChooser.showSaveDialog(frame);
        if (x == JFileChooser.APPROVE_OPTION) {
            headerFile = fileChooser.getSelectedFile();
            x = fileChooser.showSaveDialog(frame);
            if (x == JFileChooser.APPROVE_OPTION) {
                lineFile = fileChooser.getSelectedFile();
                if (getExtension(headerFile).equals("csv") && getExtension(lineFile).equals("csv")) {
                    try {
                        FileWriter fwHeader = new FileWriter(headerFile);
                        fwHeader.write(invoicesData);
                        fwHeader.flush();
                        fwHeader.close();
                        FileWriter fwLine = new FileWriter(lineFile);
                        fwLine.write(linesData);
                        fwLine.flush();
                        fwLine.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(frame, "Error while saving", "Erorr Message", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please Make Sure Files Format .CSV", "Erorr Message", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "File Not Found Please Try Again", "Erorr Message", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "File Not Found Please Try Again", "Erorr Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void newInvoice() {
        newInvoiceFrame = new NewInvoiceFrame(frame);
        newInvoiceFrame.invNumtxt.setText(Integer.toString(getNextInvNum()));
        newInvoiceFrame.setVisible(true);

    }

    private void deleteInvoice() {
        int x = frame.invTable.getSelectedRow();
        if (x != -1) {
            frame.getInvoices().remove(x);
            ((InvoiceTableM) frame.invTable.getModel()).fireTableDataChanged();
        }

    }

    private void new_Product() {
        newLineFrame = new NewLineFrame(frame);
        newLineFrame.setVisible(true);
    }

    private void delete_Product() {
        int selectedRow = frame.itemsTable.getSelectedRow();
        if (selectedRow != -1) {
            int hRow = frame.invTable.getSelectedRow();
            LineTableM lineTableModel = (LineTableM) frame.itemsTable.getModel();
            lineTableModel.getLines().remove(selectedRow);
            lineTableModel.fireTableDataChanged();
            ((InvoiceTableM) frame.invTable.getModel()).fireTableDataChanged();
            frame.invTable.setRowSelectionInterval(hRow, hRow);

        }
    }

    private InvoiceM getInvoiveByNum(int num) {

        for (InvoiceM inv : frame.getInvoices()) {
            if (num == inv.getinvoice_Number()) {
                return inv;
            }
        }
        return null;
    }

    private void cancelHeader() {
        newInvoiceFrame.setVisible(false);
        newInvoiceFrame.dispose();
    }

    private void createHeader() {

        String dateStr = (String) newInvoiceFrame.datetxt.getText();
        String customerName = (String) newInvoiceFrame.cusNametxt.getText();
        newInvoiceFrame.setVisible(false);
        newInvoiceFrame.dispose();
        try {
            Date date = frame.dFormat.parse(dateStr);
            InvoiceM inv = new InvoiceM(getNextInvNum(), date, customerName);
            frame.getInvoices().add(inv);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Erorr Date Format (dd-MM-yyy)", "Error", JOptionPane.ERROR_MESSAGE);
        }

        frame.invTable.setModel(new InvoiceTableM(frame.getInvoices()));

        ((InvoiceTableM) frame.invTable.getModel()).fireTableDataChanged();
    }

    private int getNextInvNum() {
        int num = 1;
        for (InvoiceM inv : frame.getInvoices()) {
            if (inv.getinvoice_Number() > num) {
                num = inv.getinvoice_Number();
            }
        }
        return num + 1;
    }

    private void ok() {
        int selectedRow = frame.invTable.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = newLineFrame.ProductNametxt.getText();
            String itemPrice = newLineFrame.ProductValutxt.getText();
            String itemCount = newLineFrame.Quantitytxt.getText();

            newLineFrame.setVisible(false);
            newLineFrame.dispose();

            double price = Double.parseDouble(itemPrice);
            int count = Integer.parseInt(itemCount);

            InvoiceM inv = frame.getInvoices().get(selectedRow);

            LineM line = new LineM(itemName, price, count, inv);

            inv.getLines().add(line);
            frame.invTable.setModel(new InvoiceTableM(frame.getInvoices()));
            ((InvoiceTableM) frame.invTable.getModel()).fireTableDataChanged();
        }
    }

    private void cancelAdding() {
        newLineFrame.setVisible(false);
        newLineFrame.dispose();
    }

    public static String getExtension(File f) {
        String extension = null;
        String ex = f.getName();
        int i = ex.lastIndexOf('.');

        if (i > 0 && i < ex.length() - 1) {
        	extension = ex.substring(i + 1).toLowerCase();
        }
        return extension;
    }

}
