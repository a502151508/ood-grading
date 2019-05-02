package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	TreePath movePath;
	Map<DefaultMutableTreeNode, Integer> taskIdMap;
	Map<DefaultMutableTreeNode, Integer> subTaskIdMap;
	DefaultMutableTreeNode assignment = new DefaultMutableTreeNode("Assignments/50%");
	DefaultMutableTreeNode exam = new DefaultMutableTreeNode("Exams/50%");

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LoadJTreePanel(int classId) {
		this.classId = classId;
		init();
	}

	public void init() {
		taskIdMap = new HashMap<>();
		subTaskIdMap = new HashMap<>();
		List<TaskDto> tl = ts.getTaskList(classId);
		tree = new JTree(root);
		tree.setRootVisible(false);
		model = (DefaultTreeModel) tree.getModel();
		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		tree.setEditable(true);
		if (tl.size() == 0) {
			root.add(assignment);
			root.add(exam);
		} else {
			for (TaskDto td : tl) {
				DefaultMutableTreeNode level1 = new DefaultMutableTreeNode(
						td.getTaskName() + "/" + td.getWeight() + "%");
				taskIdMap.put(level1, td.getTaskId());
				root.add(level1);
				for (SubTask st : td.getSubTaskList()) {
					DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(
							st.getSubTaskName() + "/" + st.getWeight() + "%");
					subTaskIdMap.put(level2, st.getSubTaskId());
					level1.add(level2);
				}
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
						JOptionPane.showMessageDialog(null, "Cannot Move", "Illegal Transaction",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else if (movePath != tp) {
						System.out.println(tp.getLastPathComponent());
						((DefaultMutableTreeNode) tp.getLastPathComponent())
								.add((DefaultMutableTreeNode) movePath.getLastPathComponent());
						movePath = null;
						tree.updateUI();
					}
				}
			}
		};
		tree.addMouseListener(ml);
		JScrollPane treePane = new JScrollPane(tree);
		this.add(treePane);
	}

	public JTree getTree() {
		return (this.tree);
	}

	public Map<DefaultMutableTreeNode, Integer> getTaskIdMap() {
		return taskIdMap;
	}

	public void setTaskIdMap(Map<DefaultMutableTreeNode, Integer> taskIdMap) {
		this.taskIdMap = taskIdMap;
	}

	public Map<DefaultMutableTreeNode, Integer> getSubTaskIdMap() {
		return subTaskIdMap;
	}

	public void setSubTaskIdMap(Map<DefaultMutableTreeNode, Integer> subTaskIdMap) {
		this.subTaskIdMap = subTaskIdMap;
	}

}
