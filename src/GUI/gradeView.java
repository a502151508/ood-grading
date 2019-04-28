package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.sun.prism.paint.Gradient;

import entity.Grade;
import entity.SubTask;
import entity.dto.StudentGradingDto;
import entity.dto.TaskDto;
import service.GradeService;
import service.TaskService;
import service.impl.GradeServiceImpl;
import service.impl.TaskServiceImpl;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class gradeView extends JFrame {
    private JTable gradeTable;
    private TaskService ts = new TaskServiceImpl();
    private GradeService gs = new GradeServiceImpl();
    private List<String> columns ;
    private List<Integer> subTaskIdList;
    private List<StudentGradingDto> rowDataList;
    private HashMap<Integer,Integer> subTaskPositionMap = new HashMap<>();
    public static void main(String[] args){
        gradeView frame = new gradeView();
     //   frame.setVisible(true);

    }
    public gradeView(){
    	columns  = new ArrayList<>();
    	subTaskIdList = new ArrayList<>();
    	subTaskIdList.add(0, -1);
    	rowDataList = new ArrayList<>();
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
       
        JTableHeader header = gradeTable.getTableHeader();
        header.addMouseListener(new TableHeaderMouseListener(gradeTable,this.rowDataList,this.subTaskIdList));
        
        
        
        setTableContent();
        return tablePanel;
    }
    protected void setTableContent()
    {
    	DefaultTableModel tableModel = new DefaultTableModel();
    	//ArrayList<String> column = new ArrayList<String>(Arrays.asList("Name","hw1","hw2","midterm1"));
    	//set column
    	List<TaskDto> l =ts.getTaskList(2);
    	
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
    	List<StudentGradingDto> studentGradingList = gs.getGradingList(2);
    	for(StudentGradingDto sgdto : studentGradingList) {
    		this.rowDataList.add(sgdto);
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
  	}
