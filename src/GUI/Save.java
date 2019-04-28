package GUI;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

public class Save extends JPanel {

	/**
	 * Create the panel.
	 */
	public Save() {
		setLayout(new GridBagLayout());
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 0;
		add(btnSave, gbc_btnSave);
	}

}
