package GUI;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

public class TaskPercent extends JPanel {
	private JTextField txtPercentage;

	/**
	 * Create the panel.
	 */
	public TaskPercent() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		
		txtPercentage = new JTextField();
		txtPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		txtPercentage.setText("Percentage");
		GridBagConstraints gbc_txtPercentage = new GridBagConstraints();
		gbc_txtPercentage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPercentage.gridx = 0;
		gbc_txtPercentage.gridy = 0;
		add(txtPercentage, gbc_txtPercentage);
		txtPercentage.setColumns(10);
		
		txtPercentage.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				txtPercentage.setText("");
			}
		});
	}

}
