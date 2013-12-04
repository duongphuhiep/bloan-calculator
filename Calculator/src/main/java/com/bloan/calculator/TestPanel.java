package com.bloan.calculator;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.Button;
import javax.swing.JButton;

public class TestPanel extends JPanel
{
	private JTextField textField;
	private JTextField decTextField;
	private JTextField hexTextField;

	/**
	 * Create the panel.
	 */
	public TestPanel()
	{

		JLabel lblDec = new JLabel("Dec");

		decTextField = new JTextField();
		decTextField.setColumns(10);

		JLabel lblHex = new JLabel("Hex");

		hexTextField = new JTextField();
		hexTextField.setColumns(10);

		JRadioButton intRdo = new JRadioButton("Integer (32 bits)");

		JRadioButton doubleRdo = new JRadioButton("Double (64 bits)");

		JTextArea logTextArea = new JTextArea();

		JLabel lblBin = new JLabel("Bin");

		JPanel binPane = new JPanel();
		binPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton helpBtn = new JButton("Help");

		JButton logResultBtn = new JButton("Log Result >");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblHex)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(hexTextField, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblBin)
							.addGap(18)
							.addComponent(binPane, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDec)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(helpBtn)
										.addComponent(intRdo))
									.addGap(77)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(logResultBtn)
										.addComponent(doubleRdo, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
								.addComponent(decTextField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(logTextArea, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(logTextArea, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(intRdo)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(decTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDec))
									.addGap(6)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(hexTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblHex))
									.addGap(9)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblBin)
										.addComponent(binPane, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
									.addGap(15)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(logResultBtn)
										.addComponent(helpBtn))))
							.addGap(11))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(doubleRdo)
							.addContainerGap(362, Short.MAX_VALUE))))
		);
		setLayout(groupLayout);
	}
}
