package GUI;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.Grade;
import entity.dto.StudentGradingDto;

public class TableHeaderMouseListener extends MouseAdapter {
    private JTable table;
    private List<StudentGradingDto> rowDataList;
    private List<Integer> subTaskIdList;
    public TableHeaderMouseListener(JTable table, List<StudentGradingDto> rowDataList,List<Integer> subTaskIdList) {
        this.table = table;
        this.rowDataList = rowDataList;
        this.subTaskIdList = subTaskIdList;
    }
     
    public void mouseClicked(MouseEvent event) {
        Point point = event.getPoint();
        int column = table.columnAtPoint(point);
         
        JOptionPane.showMessageDialog(table, "Column header #" + column + " is clicked");
        DefaultTableModel currentTableModel = (DefaultTableModel) table.getModel();
        DefaultTableModel newTableModel = new DefaultTableModel();
        newTableModel.addColumn("Name");
        //  currentTableModel.addRow(new Object[]{"test1","10","9","8"});
        
        newTableModel.addColumn(currentTableModel.getColumnName(column));
        int modifiedSubtaskId = subTaskIdList.get(column);
        //table.setColumnSelectionInterval(column,column); 
       
        //set row
       
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
    		newTableModel.addRow(dataString);
        }
        
        table.setModel(newTableModel);
        
        // do your real thing here...
    }
}
