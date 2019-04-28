package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.Grade;
import entity.dto.StudentGradingDto;

public class EditGradeView extends JFrame{
	private JTable gradeTable;
	private String columnName;
	private int modifiedSubtaskId;
	private List<StudentGradingDto> rowDataList;
	private List<Integer> subTaskIdList;
	public EditGradeView(String columnName, int modifiedSubtaskId, List<StudentGradingDto> rowDataList,List<Integer> subTaskIdList) {
		this.gradeTable =  new JTable();
		this.columnName = columnName;
		this.modifiedSubtaskId = modifiedSubtaskId;
		this.rowDataList = rowDataList;
		this.subTaskIdList = subTaskIdList;
		
		this.setLayout(new BorderLayout());
    	Container contentPane = this.getContentPane();
        JPanel buttonPanel = gradeView.createButtonPanel("Save");
        contentPane.add(buttonPanel,BorderLayout.SOUTH);
        
        
        JPanel tablePanel = createTablePanel();
        contentPane.add(tablePanel,BorderLayout.CENTER);
        
        
        this.setTitle("Edit");
     //   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,543,367);

     

        this.setVisible(true);
        
	}
	private  JPanel createTablePanel(){

        JPanel tablePanel= new JPanel();
        tablePanel.setBorder(new EmptyBorder(5,5,5,5));
        tablePanel.setLayout(new BorderLayout(0,0));
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
       
      
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(gradeTable);
       
            
        
        setTableContent();
        return tablePanel;
    }
	private void setTableContent() {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Name");
		tableModel.addColumn(this.columnName);
		
        for(StudentGradingDto sgdto : this.rowDataList) {
        	List<String> data = new ArrayList<>();
    		String name = sgdto.getFirstName() + " " + sgdto.getLastName();
    		data.add(name);
    		
    		List<Grade> gradeList = sgdto.getGrades();
    		for(Grade g : gradeList) {
    			int subTaskId = g.getSubTaskId();
    			if(modifiedSubtaskId == subTaskId) {
    				data.add(Double.toString(g.getScore()));
    			}
    			
    		}
    	//	this.rowDataList.add(data);
    		String[] dataString = data.toArray(new String[0]);
    		tableModel.addRow(dataString);
        }
        gradeTable.setModel(tableModel);
	}

}
