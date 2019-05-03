package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
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
				try {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (selectedNode != null && selectedNode.getParent() != null) {
						if(taskNodeIdMap.containsKey(selectedNode)) {
							ts.deleteTask(new Task(taskNodeIdMap.get(selectedNode), 0, null, 0));
						}
						else if (subTaskNodeIdMap.containsKey(selectedNode)) {
							ts.deleteSubTask(new SubTask(subTaskNodeIdMap.get(selectedNode), null, 0, 0));
						}
						model.removeNodeFromParent(selectedNode);
						
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Please select a category!");
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
				List<Double> totalTaskPerce = new ArrayList<>();
				List<Double> totalSubTaskPerce = new ArrayList<>();
				List<TreeNode> taskNodeToBeAdded = new ArrayList<>();
				List<Task> taskToBeEdited = new ArrayList<>();
				List<Task> taskToBeAdded = new ArrayList<>();
				List<SubTask> subTaskToBeEdited = new ArrayList<>();
				List<SubTask> subTaskToBeAdded = new ArrayList<>();
				
				
				
				// add task to db
				root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				taskNodeIdMap = ((LoadJTreePanel) treePanel).getTaskIdMap(); 
				int numOfTask = root.getChildCount();
				for (int i = 0; i < numOfTask; i++) {
					TreeNode taskNode = root.getChildAt(i);
					if(taskNodeIdMap.containsKey(taskNode)) {
						int taskId = taskNodeIdMap.get(taskNode);
						String taskString =  ((DefaultMutableTreeNode) taskNode).getUserObject().toString();
						String[] arrayOfTask = taskString.split("/");
						String taskName = arrayOfTask[0];
						String taskPerce = arrayOfTask[1].substring(0, arrayOfTask[1].length() - 1);
						totalTaskPerce.add(Double.valueOf(taskPerce));
						Task task = new Task(taskId, 2, taskName, Double.valueOf(taskPerce));
						//ts.editTask(task);
						taskToBeEdited.add(task);
					}
					else {
						String taskString = ((DefaultMutableTreeNode) taskNode).getUserObject().toString();
						String[] arrayOfTask = taskString.split("/");
						String taskName = arrayOfTask[0];
						String taskPerce = arrayOfTask[1].substring(0, arrayOfTask[1].length() - 1);
						totalTaskPerce.add(Double.valueOf(taskPerce));
						Task task = new Task(0, 2, taskName, Double.valueOf(taskPerce));
						taskToBeAdded.add(task);
						taskNodeToBeAdded.add(taskNode);
//						System.out.println(task.toString());
						//int taskId = ts.addTask(task);
						//taskNodeIdMap.put((DefaultMutableTreeNode)taskNode, taskId);
					}
				}
				if(!inputValidation(totalTaskPerce)) {
					JOptionPane.showMessageDialog(null,"total Task percentage must be equal to 100");
				}
				else {
					for(Task t: taskToBeEdited) {
						ts.editTask(t);
					}
					for(int i = 0; i < taskToBeAdded.size(); i++) {
						Task t = taskToBeAdded.get(i);
						DefaultMutableTreeNode taskNode = (DefaultMutableTreeNode) taskNodeToBeAdded.get(i);
						int taskId = ts.addTask(t);
						taskNodeIdMap.put((DefaultMutableTreeNode)taskNode, taskId);
					}
				}
				
				
				
				
				
				
				subTaskNodeIdMap = ((LoadJTreePanel) treePanel).getSubTaskIdMap();
				for (int i = 0; i < numOfTask; i++) {
					DefaultMutableTreeNode taskNode = (DefaultMutableTreeNode)root.getChildAt(i);		
					totalSubTaskPerce = new ArrayList<>();
					subTaskToBeEdited = new ArrayList<>();
					subTaskToBeAdded = new ArrayList<>();
					
					
					String taskString =  ((DefaultMutableTreeNode) taskNode).getUserObject().toString();
					String[] arrayOfTask = taskString.split("/");
					
					String taskPerce = arrayOfTask[1].substring(0, arrayOfTask[1].length() - 1);
					Double target = Double.valueOf(taskPerce);
					
					
					int taskId = taskNodeIdMap.get(taskNode);
					int numOfSubTask = taskNode.getChildCount();
					for (int j = 0; j < numOfSubTask; j++) {
						DefaultMutableTreeNode subTaskNode = (DefaultMutableTreeNode) taskNode.getChildAt(j);

						if(subTaskNodeIdMap.containsKey(subTaskNode)) {
							int subTaskId = subTaskNodeIdMap.get(subTaskNode);
							Object obj = subTaskNode.getUserObject();
							String subTaskString = obj.toString();
							String[] arryOfSubTask = subTaskString.split("/");
							String subTaskName = arryOfSubTask[0];
							String subTaskPerce = arryOfSubTask[1].substring(0, arryOfSubTask[1].length() - 1);
							totalSubTaskPerce.add(Double.valueOf(subTaskPerce));
							SubTask subTask = new SubTask(subTaskId, subTaskName, taskId, Double.valueOf(subTaskPerce));
							subTaskToBeEdited.add(subTask);
							//ts.editSubTask(subTask);
						}
						else {
							Object obj = subTaskNode.getUserObject();
							String subTaskString = obj.toString();
							String[] arryOfSubTask = subTaskString.split("/");
							String subTaskName = arryOfSubTask[0];
							String subTaskPerce = arryOfSubTask[1].substring(0, arryOfSubTask[1].length() - 1);
							totalSubTaskPerce.add(Double.valueOf(subTaskPerce));
							//System.out.println("The double perce is " + subTaskPerce);
							SubTask subTask = new SubTask(0, subTaskName, taskId, Double.valueOf(subTaskPerce));
							//ts.addSubTask(subTask);
							subTaskToBeAdded.add(subTask);
						}
					}
					if(!inputValidation(totalSubTaskPerce,target)) {
						JOptionPane.showMessageDialog(null,"total subTask percentage must be equal to " + target);
					}else {
						for(SubTask sbt : subTaskToBeEdited) {
							ts.editSubTask(sbt);
						}
						for(SubTask sbt: subTaskToBeAdded) {
							ts.addSubTask(sbt);
						}
					}
				}
				
				//gradeView.setTableContent();
			}
		});
		panel.add(saveButton);

		this.getContentPane().add(panel, BorderLayout.SOUTH);
		this.pack();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	protected boolean inputValidation(List<Double> list) {
		double total = 0;
		for(double d : list) {
			total += d;
		}
		if(total != 100) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	protected boolean inputValidation(List<Double>list , double target) {
		double total = 0;
		for(double d : list) {
			total += d;
		}
		if(total != target) {
			return false;
		}
		else {
			return true;
		}
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public static void main(String[] args) {
		new EditGradingCriteria(2).init();
	}
}