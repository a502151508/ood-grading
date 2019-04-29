package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class EditCategory extends JFrame {

	JTree jt;
	JPanel contentPane;
	JTextField txtPercentage;
	JTextField txtName;
	JButton btnSave;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditCategory frame = new EditCategory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditCategory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 79);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(6, 12, 244, 33);
		contentPane.add(splitPane);
		
		splitPane.setResizeWeight(0.5);
		
		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setText("Name");
		splitPane.setLeftComponent(txtName);
		txtName.setColumns(10);
		
		txtPercentage = new JTextField();
		txtPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		txtPercentage.setText("Percentage");
		splitPane.setRightComponent(txtPercentage);
		txtPercentage.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(288, 14, 117, 29);
		contentPane.add(btnSave);

	}
	
	String getTaskName() {
		return txtName.getText();
	}
	
	String getTaskPercent() {
		return txtPercentage.getText();
	}
	
	
}
