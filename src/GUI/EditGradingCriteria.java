package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import entity.SubTask;
import entity.Task;
import entity.dto.TaskDto;
import service.TaskService;
import service.impl.TaskServiceImpl;

public class EditGradingCriteria extends JFrame {
	public EditGradingCriteria(int classId) {
		this.classId = classId;
	}

	private int classId;
	JTree tree;
	DefaultTreeModel model;
	JPanel treePanel;

	TaskService ts = new TaskServiceImpl();
	Map<DefaultMutableTreeNode, Integer> taskNodeIdMap = new HashMap<>();
	Map<DefaultMutableTreeNode, Integer> subTaskNodeIdMap = new HashMap<>();
	

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Grading Criteria");
	DefaultMutableTreeNode assignment = new DefaultMutableTreeNode("Assignments/50%");
	DefaultMutableTreeNode exam = new DefaultMutableTreeNode("Exams/50%");

	TreePath movePath;

	JButton addParentButton = new JButton("Add Category");
	JButton addChildButton = new JButton("Add Sub-Category");
	JButton deleteButton = new JButton("Delete");
	JButton editButton = new JButton("Edit");
	JButton saveButton = new JButton("Save");

	public void init() {

		treePanel = new LoadJTreePanel(classId);
		this.tree = ((LoadJTreePanel) treePanel).getTree();
		this.getContentPane().add(treePanel, BorderLayout.NORTH);

		this.setTitle("Grading Criteria");
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

		JPanel panel = new JPanel();

		addParentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
				EditCategory cateInfo = new EditCategory();
				cateInfo.setVisible(true);

				JButton save = cateInfo.btnSave;
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = cateInfo.getTaskName();
						String percent = cateInfo.getTaskPercent();
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name + "/" + percent + "%");
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

		addChildButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				EditCategory cateInfo = new EditCategory();
				cateInfo.setVisible(true);
				;

				JButton save = cateInfo.btnSave;
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = cateInfo.getTaskName();
						String percent = cateInfo.getTaskPercent();
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name + "/" + percent + "%");
						selectedNode.add(newNode);
						TreeNode[] nodes = model.getPathToRoot(newNode);
						TreePath path = new TreePath(nodes);
						tree.scrollPathToVisible(path);
						tree.updateUI();
					}
				});
			}
		});
		panel.add(addChildButton);

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (selectedNode != null && selectedNode.getParent() != null) {
					model.removeNodeFromParent(selectedNode);
				}
			}
		});
		panel.add(deleteButton);

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				EditCategory cateInfo = new EditCategory();
				cateInfo.setVisible(true);
				;

				JButton save = cateInfo.btnSave;
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = cateInfo.getTaskName();
						String percent = cateInfo.getTaskPercent();
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name + "/" + percent + "%");
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						selectedNode.setUserObject(newNode);
						TreeNode[] nodes = model.getPathToRoot(newNode);
						TreePath path = new TreePath(nodes);
						tree.scrollPathToVisible(path);
						tree.updateUI();
					}
				});
			}
		});
		panel.add(editButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// add task to db
				root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				taskNodeIdMap = ((LoadJTreePanel) treePanel).getTaskIdMap(); 
				int numOfTask = root.getChildCount();
				for (int i = 0; i < numOfTask; i++) {
					TreeNode taskNode = root.getChildAt(i);
					if(taskNodeIdMap.containsKey(taskNode)) {
						int taskId = taskNodeIdMap.get(taskNode);
						String taskString = (String) ((DefaultMutableTreeNode) taskNode).getUserObject();
						String[] arrayOfTask = taskString.split("/");
						String taskName = arrayOfTask[0];
						String taskPerce = arrayOfTask[1].substring(0, arrayOfTask[1].length() - 1);
						Task task = new Task(taskId, 2, taskName, Double.valueOf(taskPerce));
						ts.editTask(task);

					}
					else {
						String taskString = (String) ((DefaultMutableTreeNode) taskNode).getUserObject();
						String[] arrayOfTask = taskString.split("/");
						String taskName = arrayOfTask[0];
						String taskPerce = arrayOfTask[1].substring(0, arrayOfTask[1].length() - 1);
						Task task = new Task(0, 2, taskName, Double.valueOf(taskPerce));
//						System.out.println(task.toString());
						int taskId = ts.addTask(task);
						taskNodeIdMap.put((DefaultMutableTreeNode)taskNode, taskId);
					}
				}
				// get task id from db
//				List<TaskDto> taskDtoList = ts.getTaskList(2);
//				for (TaskDto tdto : taskDtoList) {
//					String taskName = tdto.getTaskName();
//					int taskId = tdto.getTaskId();
//					taskNameIdMap.put(taskName, taskId);
//				}
				// add subtasks to db
				subTaskNodeIdMap = ((LoadJTreePanel) treePanel).getSubTaskIdMap();
				for (int i = 0; i < numOfTask; i++) {
					TreeNode taskNode = root.getChildAt(i);				
					//					String taskString = (String) ((DefaultMutableTreeNode) taskNode).getUserObject();
					//					String[] arrayOfTask = taskString.split("/");
					//					String taskName = arrayOfTask[0];
					int taskId = taskNodeIdMap.get(taskNode);
					int numOfSubTask = taskNode.getChildCount();
					for (int j = 0; j < numOfSubTask; j++) {
						TreeNode subTaskNode = taskNode.getChildAt(j);

						if(subTaskNodeIdMap.containsKey(subTaskNode)) {
							int subTaskId = subTaskNodeIdMap.get(subTaskNode);
							String subTaskString = (String) ((DefaultMutableTreeNode) subTaskNode).getUserObject();
							String[] arryOfSubTask = subTaskString.split("/");
							String subTaskName = arryOfSubTask[0];
							String subTaskPerce = arryOfSubTask[1].substring(0, arryOfSubTask[1].length() - 1);
							SubTask subTask = new SubTask(subTaskId, subTaskName, taskId, Double.valueOf(subTaskPerce));
							ts.editSubTask(subTask);
						}
						else {
							String subTaskString = (String) ((DefaultMutableTreeNode) subTaskNode).getUserObject();
							String[] arryOfSubTask = subTaskString.split("/");
							String subTaskName = arryOfSubTask[0];
							String subTaskPerce = arryOfSubTask[1].substring(0, arryOfSubTask[1].length() - 1);
							SubTask subTask = new SubTask(0, subTaskName, taskId, Double.valueOf(subTaskPerce));
							ts.addSubTask(subTask);
						}
					}
				}
			}
		});
		panel.add(saveButton);

		this.getContentPane().add(panel, BorderLayout.SOUTH);
		this.pack();
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}
	

//	public static void main(String[] args) {
//		new EditGradingCriteria().init();
//	}
}