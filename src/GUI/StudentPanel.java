package GUI;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import service.StudentService;
import service.impl.StudentServiceImpl;
import entity.Student;

public class StudentPanel extends JFrame {

    JScrollPane scrollPane;
    JTable studentTable;
    JFrame studentPopup;

    JTextField firstNameTxt;
    JTextField lastNameTxt;
    JTextField BUIDTxt;
    String firstName = "Test";
    String lastName = "Fall 2019";
    String BUID = "";
    private int classID;

    public StudentPanel(int classID) {
        initComponents();
        populateTable();
        this.classID = classID;
    }


    public List<Student> ListStudents(int classID){
        StudentService ss  = new StudentServiceImpl();
        List<Student> list = ss.getStudentList(classID);
        return list;
    }


    public void populateTable(){
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        List<Student> list = ListStudents(2);
        Object rowData[] = new Object[3];
        for(int i = 0; i < list.size(); i++)
        {
            rowData[0] = list.get(i).getStuId();
            rowData[1] = list.get(i).getFirstName();
            rowData[2] = list.get(i).getLastName();
            model.addRow(rowData);
        }

    }

    private void initComponents() {

        scrollPane = new JScrollPane();
        studentTable = new JTable();
        JPanel buttonsPanel = new JPanel();
        JPanel scrollPanel = new JPanel();

        JButton removeStudentButton = new JButton("Remove Student");
        removeStudentButton.addActionListener(new RemoveStudentListener());

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new AddStudentListener());

        JButton switchGradeButton = new JButton("Grade View");
        switchGradeButton.addActionListener(null);

        JButton switchClassButton = new JButton("Class View");
        switchClassButton.addActionListener(new ClassViewActionListener());

        buttonsPanel.add(removeStudentButton);
        buttonsPanel.add(addStudentButton);
        buttonsPanel.add(switchClassButton);
        buttonsPanel.add(switchGradeButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        studentTable.setModel(new DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Id", "First Name", "Last Name"
                }
        ));
        scrollPane.setViewportView(studentTable);
        getContentPane().setLayout(new BorderLayout());
        GroupLayout layout = new GroupLayout(scrollPanel);
        scrollPanel.setLayout(layout);
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
        add(scrollPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
    }


//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new StudentPanel().setVisible(true);
//            }
//        });
//    }


    private class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = studentTable.getSelectedRow();
            int modelRow = studentTable.convertRowIndexToModel(row);
            DefaultTableModel model = (DefaultTableModel)studentTable.getModel();

            int BUID =Integer.parseInt(model.getValueAt(modelRow, 0).toString());
            String firstName = model.getValueAt(modelRow, 1).toString();
            String lastName = model.getValueAt(modelRow, 2).toString();

            Student rem = new Student(0, 2,firstName, lastName,1, "U"+BUID);

            StudentService ss = new StudentServiceImpl();
            ss.deleteStudent(rem);

            model.removeRow(modelRow);
        }
    }

    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            studentPopup = new JFrame();
            JPanel studentCreation = new JPanel();

            JButton addStudent = new JButton("Add Student");
            addStudent.addActionListener(new CreateStudentActionListener());

            firstNameTxt = new JTextField("Enter First Name");
            lastNameTxt = new JTextField("Enter Last Name");
            BUIDTxt = new JTextField("Enter BUID");

            studentCreation.add(firstNameTxt);
            studentCreation.add(lastNameTxt);
            studentCreation.add(BUIDTxt);
            studentCreation.add(addStudent);
            studentCreation.setVisible(true);

            studentPopup.add(studentCreation);
            studentPopup.setVisible(true);
            studentPopup.pack();


        }

        private class CreateStudentActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstName = firstNameTxt.getText();
                lastName = lastNameTxt.getText();
                BUID = BUIDTxt.getText();

                firstNameTxt.setText("Enter First Name");
                lastNameTxt.setText("Enter Last Name");
                BUIDTxt.setText("Enter BUID");
                DefaultTableModel model = (DefaultTableModel)studentTable.getModel();
                if(!firstName.isEmpty() && !lastName.isEmpty() && !BUID.isEmpty()){
                    Student add = new Student(0, 2,firstName, lastName,1, BUID);

                    model.addRow(new Object[]{BUID, firstName, lastName});
                    StudentService ss = new StudentServiceImpl();
                    //ss.addStudent(add);
                }
                studentPopup.setVisible(false);
            }
        }
    }

    private class ClassViewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new ClassPanel().setVisible(true);
        }
    }
}
