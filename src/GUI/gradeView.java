package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import entity.Grade;
import entity.SubTask;
import entity.dto.StudentGradingDto;
import entity.dto.TaskDto;
import service.GradeService;
import service.TaskService;
import service.impl.GradeServiceImpl;
import service.impl.TaskServiceImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class gradeView extends JFrame implements ActionListener {
    private static JTable gradeTable;
    private static TaskService ts = new TaskServiceImpl();
    private static GradeService gs = new GradeServiceImpl();
    private List<String> columns ;
    private static List<Integer> subTaskIdList;
    private static List<StudentGradingDto> rowDataList;
    private static HashMap<Integer,Integer> subTaskPositionMap = new HashMap<>();
    private static int classId;
    private JButton classCretriaButton;
//    public static void main(String[] args){
//        gradeView frame = new gradeView();
//     //   frame.setVisible(true);
//
//    }
    public gradeView(int classId){
    	columns  = new ArrayList<>();
    	subTaskIdList = new ArrayList<>();
    	subTaskIdList.add(0, -1);
    	rowDataList = new ArrayList<>();
    	this.classId = classId;
    	this.setLayout(new BorderLayout());
    	Container contentPane = this.getContentPane();
        JPanel buttonPanel = createButtonPanel("Class Criteria");
        contentPane.add(buttonPanel,BorderLayout.NORTH);


        JPanel tablePanel = createTablePanel();
        contentPane.add(tablePanel,BorderLayout.CENTER);

        this.setTitle("Grade");
        this.setBounds(100,100,543,367);

     

        this.setVisible(true);
    }
    protected JPanel createButtonPanel(String buttonName){
        JPanel buttonPanel = new JPanel();
        classCretriaButton = new JButton(buttonName);
        classCretriaButton.addActionListener(this);
        buttonPanel.add(classCretriaButton);
        return  buttonPanel;
    }
    private  JPanel createTablePanel(){

        JPanel tablePanel= new JPanel();
        tablePanel.setBorder(new EmptyBorder(5,5,5,5));
        tablePanel.setLayout(new BorderLayout(0,0));
        JScrollPane scrollPane = new JScrollPane();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        gradeTable = new JTable();
      
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(gradeTable);
       
        JTableHeader header = gradeTable.getTableHeader();
        header.addMouseListener(new TableHeaderMouseListener(gradeTable,this.rowDataList,this.subTaskIdList));
        
        
        
        setTableContent();
        return tablePanel;
    }
    protected static void setTableContent()
    {
    	DefaultTableModel tableModel = new DefaultTableModel();
    	//ArrayList<String> column = new ArrayList<String>(Arrays.asList("Name","hw1","hw2","midterm1"));
    	//set column
    	List<TaskDto> l =ts.getTaskList(classId);
    	
    	tableModel.addColumn("Name");
    	
    	for(TaskDto t : l) {
    		List<SubTask> subTaskList = t.getSubTaskList();
    		int i = 0;
    		for(SubTask subtask : subTaskList) {
    			//columns.add(subtask.getSubTaskName());
    			i++;
    			tableModel.addColumn(subtask.getSubTaskName());
    			//need hashmap to memory position
    			subTaskIdList.add(i,subtask.getSubTaskId());
    			subTaskPositionMap.put(subtask.getSubTaskId(), i);
    			
    		}
    		
    		
    	}
    	
    	//set data
    	List<StudentGradingDto> studentGradingList = gs.getGradingList(classId);
    	for(StudentGradingDto sgdto : studentGradingList) {
    		rowDataList.add(sgdto);
    		List<String> data = new ArrayList<>();
    		String name = sgdto.getFirstName() + " " + sgdto.getLastName();
    		data.add(name);
    		
    		List<Grade> gradeList = sgdto.getGrades();
    		for(Grade g : gradeList) {
    			int subTaskId = g.getSubTaskId();
    			data.add(subTaskPositionMap.get(subTaskId), Double.toString(g.getScore()));
    		}
    	//	this.rowDataList.add(data);
    		String[] dataString = data.toArray(new String[0]);
    		tableModel.addRow(dataString);
    	}
    	
    	
    //	ArrayList<String> row = new ArrayList<String>(Arrays.asList("YIFEI","100","90","80"));
    	
    	// tableModel.addRow(new Object[]{"YIFEI","100","90","80"});
        gradeTable.setRowHeight(30);
        gradeTable.setModel(tableModel);
    }
    private List<StudentGradingDto> getRowData(){
    		return this.rowDataList;
    	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == classCretriaButton) {
			new EditGradingCriteria(classId).init();
			setTableContent();
		}
		
	}
  	}
