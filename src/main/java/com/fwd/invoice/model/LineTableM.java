package com.fwd.invoice.model;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class LineTableM extends AbstractTableModel {

    private String[] col = { "Item Name", "Item Price", "Quantity", "Items Total"};
    private List<LineM> lines;

    public LineTableM(List<LineM> lines) {
        this.lines = lines;
    }

    public List<LineM> getLines() {
        return lines;
    }
    

    @Override
    public int getRowCount() {
        return lines.size();
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
        LineM line=lines.get(rowIndex);
        switch(columnIndex){
        case 0: return line.getProduct();
        case 1: return line.getValue();
        case 2: return line.getQuantity();
        case 3: return line.getTotalLine();
        }
        return "";
    }

}
