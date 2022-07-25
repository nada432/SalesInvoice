
package model;    
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SalesLinesTableModel extends AbstractTableModel {

    private ArrayList<SalesInvoiceLine> lines;
    private String[] Tablecolumns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public SalesLinesTableModel(ArrayList<SalesInvoiceLine> lines) {
        this.lines = lines;
    }

    public ArrayList<SalesInvoiceLine> getLines() {
        return lines;
    }
    
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return Tablecolumns.length;
    }

    @Override
    public String getColumnName(int x) {
        return Tablecolumns[x];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SalesInvoiceLine line = lines.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvoice().getNum();
            case 1: return line.getItem();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getLineTotal();
            default : return "";
        }
    }
    
}

