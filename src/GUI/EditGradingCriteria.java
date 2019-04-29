package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.event.MouseListener;  
  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import javax.swing.JScrollPane;  
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeModel;  
import javax.swing.tree.TreeNode;  
import javax.swing.tree.TreePath;
  
public class EditGradingCriteria extends JFrame {  
    //JFrame jf;  
  
    JTree tree;  
    DefaultTreeModel model;  
  
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Grading Criteria");  
    DefaultMutableTreeNode assignment = new DefaultMutableTreeNode("Assignments");  
    DefaultMutableTreeNode exam = new DefaultMutableTreeNode("Exams");  
    
    TreePath movePath;  
  
    JButton addParentButton = new JButton("Add Category");
    JButton addSiblingButton = new JButton("Add Sub-Category"); 
    JButton deleteButton = new JButton("Delete");  
    JButton editButton = new JButton("Edit");  
    
  
    public void init() {
    	
    	root.add(assignment);
    	root.add(exam);
        this.setTitle("Grading Criteria");  
        tree = new JTree(root);
        tree.setRootVisible(false);
        model = (DefaultTreeModel) tree.getModel();
        DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
        tree.setEditable(true);
        TreePath tp = new TreePath(dtm.getPathToRoot(assignment));
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
  
        JPanel panel = new JPanel();  
  
        addParentButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree  
                        .getLastSelectedPathComponent();  
        		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode  
                        .getParent();  
        		EditCategory cateInfo = new EditCategory();
        		cateInfo.setVisible(true);
        		
        		JButton save = cateInfo.btnSave;	
        		save.addActionListener(new ActionListener() {
        		    public void actionPerformed(ActionEvent e) {
        		    	String name = cateInfo.getTaskName();
                		String percent = cateInfo.getTaskPercent();
                		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(  
                                name + " " + percent + "%"); 
                		parent.add(newNode); 
                		TreeNode[] nodes = model.getPathToRoot(newNode);  
                        TreePath path = new TreePath(nodes);  
                        tree.scrollPathToVisible(path);  
                        tree.updateUI();
        		    }
        		});
        	}
        });
        panel.add(addParentButton);
        
        addSiblingButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent event) {                  
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree  
                        .getLastSelectedPathComponent();  
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(  
                        "New Sub-Category");  
                selectedNode.add(newNode);  
                TreeNode[] nodes = model.getPathToRoot(newNode);  
                TreePath path = new TreePath(nodes);  
                tree.scrollPathToVisible(path);  
                tree.updateUI();  
            }  
        });  
        panel.add(addSiblingButton);  
  
        deleteButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent event) {  
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree  
                        .getLastSelectedPathComponent();  
                if (selectedNode != null && selectedNode.getParent() != null) {   
                    model.removeNodeFromParent(selectedNode);  
                }  
            }  
        });  
        panel.add(deleteButton);  
  
        editButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent event) {  
                TreePath selectedPath = tree.getSelectionPath();  
                if (selectedPath != null) {  
                    EditCategory edit = new EditCategory();
                    edit.setVisible(true);
                }  
            }  
        });  
        panel.add(editButton);  
  
        JScrollPane scrollPane = new JScrollPane(tree);
        this.getContentPane().add(scrollPane);
       
        

        this.getContentPane().add(panel, BorderLayout.SOUTH);  
        this.pack();  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true);  
    }  
  
    public static void main(String[] args) {  
        new EditGradingCriteria().init();  
    }  
}  