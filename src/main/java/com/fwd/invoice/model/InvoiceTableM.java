package com.fwd.invoice.model;
import com.fwd.invoice.view.MainFrame;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class InvoiceTableM extends AbstractTableModel{
    private String[] col={"invoice number","Date","Client Name","Cost"};
    private List<InvoiceM> invoices;
    
    public InvoiceTableM(List<InvoiceM> invoices){
        this.invoices=invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return col.length;
    }

    @Override
    public String getColumnName(int column) {
        return col[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceM inv = invoices.get(rowIndex);
        switch(columnIndex){
        case 0: return inv.getinvoice_Number();
        case 1: return MainFrame.dFormat.format(inv.getDate());
        case 2: return inv.getClient();
        case 3: return inv.getCost();
                }
        return "";
    }
    
}
