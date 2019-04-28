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
    	this.setLayout(new BorderLayout());
    	Container contentPane = this.getContentPane();
        JPanel buttonPanel = createButtonPanel();
        contentPane.add(buttonPanel,BorderLayout.NORTH);


        JPanel tablePanel = createTablePanel();
        contentPane.add(tablePanel,BorderLayout.CENTER);

        this.setTitle("Grade");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,543,367);

     

        this.setVisible(true);
    }
    protected  JPanel createButtonPanel(){
        JPanel buttonPanel = new JPanel();
        JButton classCretriaButton = new JButton("Class Cretria");
        buttonPanel.add(classCretriaButton);
        return  buttonPanel;
    }
    private JPanel createTablePanel(){

        JPanel tablePanel= new JPanel();
        tablePanel.setBorder(new EmptyBorder(5,5,5,5));
        tablePanel.setLayout(new BorderLayout(0,0));
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        gradeTable = new JTable();
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(gradeTable);
        setTableContent();
        return tablePanel;
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
