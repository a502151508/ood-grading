package GUI;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class StudentPanel extends JFrame {

   
    public StudentPanel() {
        initComponents();
        populateTable();

    }
    
    public class Student{
        public int id;
        public String fname;
        public String lname;
        public int age;
        
        public Student(int Id, String FName, String LName, int Age){
            this.id = Id;
            this.fname = FName;
            this.lname = LName;
            this.age = Age;
        }
    }
    
    public ArrayList ListStudents(int classID){
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(1,"Emmanuel","Amponsah",10));
        list.add(new Student(2,"Emmanuel","Amponsah",20));
        list.add(new Student(3,"Sid","Prem",30));
        list.add(new Student(6,"FNF","LNF",60));
      
        return list;
    }
    

    public void populateTable(){
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        ArrayList<Student> list = ListStudents(0);
        Object rowData[] = new Object[4];
        for(int i = 0; i < list.size(); i++)
        {
            rowData[0] = list.get(i).id;
            rowData[1] = list.get(i).fname;
            rowData[2] = list.get(i).lname;
            rowData[3] = list.get(i).age;
            model.addRow(rowData);
        }
                
    }
                        
    private void initComponents() {

        scrollPane = new JScrollPane();
        studentTable = new JTable();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        studentTable.setModel(new DefaultTableModel(
            new Object [][] {
				
            },
            new String [] {
               "Id", "First Name", "Last Name", "Age"
            }
        ));
        scrollPane.setViewportView(studentTable);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }                     

 
    public static void main(String args[]) {
   
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentPanel().setVisible(true);
            }
        });
    }

                         
    JScrollPane scrollPane;
    JTable studentTable;
                     
}
