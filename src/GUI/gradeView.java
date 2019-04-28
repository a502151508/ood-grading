package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class gradeView extends JFrame {
    private JTable gradeTable;
    public static void main(String[] args){
        gradeView frame = new gradeView();
     //   frame.setVisible(true);

    }
    public gradeView(){
        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel);


        JPanel tablePanel = createTablePanel();
        this.add(tablePanel);

        this.setTitle("Grade");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,450,200);

        this.add(tablePanel);

        this.setVisible(true);
    }
    protected  JPanel createButtonPanel(){
        JPanel jp = new JPanel();
        JButton classCretriaButton = new JButton("Class Cretria");
        jp.add(classCretriaButton);
        return  jp;
    }
    private JPanel createTablePanel(){

        JPanel jp= new JPanel();
        jp.setBorder(new EmptyBorder(5,5,5,5));
        jp.setLayout(new BorderLayout(0,0));
        JScrollPane scrollPane = new JScrollPane();
        jp.add(scrollPane, BorderLayout.CENTER);
        gradeTable = new JTable();
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(gradeTable);
        setTableContent();
        return jp;
    }
    protected void setTableContent()
    {
        DefaultTableModel tableModel=(DefaultTableModel) gradeTable.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Name","HW1","HW2","midterm1"});
        tableModel.addRow(new Object[]{"YIFEI","100","90","80"});
        gradeTable.setRowHeight(30);
        gradeTable.setModel(tableModel);
    }
}
