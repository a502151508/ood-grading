package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import entity.SubTask;
import entity.dto.TaskDto;
import service.TaskService;
import service.impl.TaskServiceImpl;

public class LoadJTreePanel extends JPanel {
	
	int classId;
	JTree tree;  
    DefaultTreeModel model;  
    TaskService ts = new TaskServiceImpl();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Grading Criteria");  
//    DefaultMutableTreeNode assignment = new DefaultMutableTreeNode("Assignments");  
//    DefaultMutableTreeNode exam = new DefaultMutableTreeNode("Exams");  
    TreePath movePath;  

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LoadJTreePanel(int classId) {
		this.classId=classId;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		init();
	}
	public void init() {
		List<TaskDto> tl = ts.getTaskList(classId);
		tree = new JTree(root);
        tree.setRootVisible(false);
        model = (DefaultTreeModel) tree.getModel();
        DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
        tree.setEditable(true);
        for(TaskDto td:tl) {
        	 DefaultMutableTreeNode level1 = new DefaultMutableTreeNode(td.getTaskName()+"/"+td.getWeight()+"%");
        	 root.add(level1);
        	 for(SubTask st : td.getSubTaskList()) {
        		 DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(st.getSubTaskName()+"/"+st.getWeight()+"%");
        		 level1.add(level2);
        	 }
        }
        TreePath tp = new TreePath(dtm.getPathToRoot(root.getFirstChild()));
        tree.setSelectionPath(tp);
        
        MouseListener ml = new MouseAdapter() {  
            public void mousePressed(MouseEvent e) { 
                TreePath tp = tree.getPathForLocation(e.getX(), e.getY());  
                if (tp != null) {  
                    movePath = tp;  
                }  
            }  

            public void mouseReleased(MouseEvent e) {  
                TreePath tp = tree.getPathForLocation(e.getX(), e.getY());  
  
                if (tp != null && movePath != null) {    
                    if (movePath.isDescendant(tp) && movePath != tp) {  
                        JOptionPane.showMessageDialog(null,  
                                "Cannot Move", "Illegal Transaction",  
                                JOptionPane.ERROR_MESSAGE);  
                        return;  
                    }  
                    else if (movePath != tp) {  
                        System.out.println(tp.getLastPathComponent());  
                        ((DefaultMutableTreeNode) tp.getLastPathComponent())  
                                .add((DefaultMutableTreeNode) movePath  
                                        .getLastPathComponent());  
                        movePath = null;  
                        tree.updateUI();  
                    }  
                }  
            }  
        };  
        tree.addMouseListener(ml); 
        JScrollPane scrollPane = new JScrollPane(tree);
        this.add(scrollPane);
	}

}
