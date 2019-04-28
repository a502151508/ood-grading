package GUI;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;

public class TaskEdit extends JPanel {

	/**
	 * Create the panel.
	 */
	public TaskEdit() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		add(splitPane, gbc_splitPane);
		
		splitPane.setResizeWeight(0.5);
		
		JPanel leftPanel = new TaskName();
		JPanel rightPanel = new TaskPercent();
		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
	}

}
