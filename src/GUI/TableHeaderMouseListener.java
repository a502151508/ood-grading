package GUI;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableHeaderMouseListener extends MouseAdapter {
    private JTable table;
    
    public TableHeaderMouseListener(JTable table) {
        this.table = table;
    }
     
    public void mouseClicked(MouseEvent event) {
        Point point = event.getPoint();
        int column = table.columnAtPoint(point);
         
        JOptionPane.showMessageDialog(table, "Column header #" + column + " is clicked");
        DefaultTableModel currentTableModel = (DefaultTableModel) table.getModel();
        currentTableModel.addRow(new Object[]{"test1","10","9","8"});
        
        //table.setColumnSelectionInterval(column,column); 
        // do your real thing here...
    }
}
