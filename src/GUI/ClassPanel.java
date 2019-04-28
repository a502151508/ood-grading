package GUI;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ClassPanel extends JFrame {

    JScrollPane scrollPane;
    JTable classTable;

    public ClassPanel() {
        initComponents();
        populateTable();

    }

    public class Class {
        public int id;
        public String sem;
        public String className;

        public Class(int Id, String sem, String className) {
            this.id = Id;
            this.sem = sem;
            this.className = className;

        }
    }

    public ArrayList ListClass(int classID) {
        ArrayList<Class> list = new ArrayList<>();
        list.add(new Class(1, "Fall 2019", "CS591"));
        list.add(new Class(3, "Spring 2018", "CS320"));
        list.add(new Class(4, "Fall 2018", "CS112"));
        list.add(new Class(5, "Spring 2019", "CS111"));

        return list;
    }

    public void populateTable() {
        DefaultTableModel model = (DefaultTableModel) classTable.getModel();
        ArrayList<Class> list = ListClass(0);
        Object rowData[] = new Object[3];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).id;
            rowData[1] = list.get(i).sem;
            rowData[2] = list.get(i).className;
            model.addRow(rowData);
        }

    }

    private void initComponents() {

        scrollPane = new JScrollPane();
        classTable = new JTable();

        JButton removeClassButton = new JButton("Remove Class");
        removeClassButton.setSize(40, 40);
        removeClassButton.addActionListener(new RemoveClassListener());

        JButton addClassbutton = new JButton("Add Class");
        addClassbutton.setSize(40, 40);
        addClassbutton.addActionListener(new AddClassListener());

        JButton classSettingsbutton = new JButton("Class Settings");
        classSettingsbutton.setSize(40, 40);
        classSettingsbutton.addActionListener(new ClassSettingsListener());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        classTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Semester", "Class Name" }));

        scrollPane.setViewportView(classTable);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout
                        .createSequentialGroup().addGap(32, 32, 32).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(61, Short.MAX_VALUE)));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout
                        .createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE)));

        //scrollPane.add(removeClassButton);
        scrollPane.add(addClassbutton);
        pack();
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassPanel().setVisible(true);
            }
        });
    }

    public class RemoveClassListener implements  ActionListener {
        public void actionPerformed(ActionEvent event){
            int row = classTable.getSelectedRow();
            int modelRow = classTable.convertRowIndexToModel(row);
            DefaultTableModel model = (DefaultTableModel)classTable.getModel();
            model.removeRow(modelRow);                  
        }
    }

    public class AddClassListener implements  ActionListener {
        public void actionPerformed(ActionEvent event){
            DefaultTableModel model = (DefaultTableModel)classTable.getModel();
            model.addRow(new Object[]{"1", "Fall 2020", "CS432"}); 
            //TODO: add popup window for collecting class information                
        }
    }

    public class ClassSettingsListener implements  ActionListener {
        public void actionPerformed(ActionEvent event){
            int row = classTable.getSelectedRow();
            int modelRow = classTable.convertRowIndexToModel(row);
            //TODO: call other UI code                 
        }
    }

}
