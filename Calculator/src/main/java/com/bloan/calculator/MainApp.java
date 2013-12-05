package com.bloan.calculator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bloan.calculator.utils.JTextFieldEx;
import com.bloan.calculator.utils.RadixConverter;
import com.bloan.calculator.utils.Utils;
import com.google.common.base.Strings;

public class MainApp
{
	private JFrame frmRadixConverter;
	private int skipChangeEvt = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainApp window = new MainApp();
					window.frmRadixConverter.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp()
	{
		initialize();
	}

	JRadioButton intRdo = new JRadioButton("Integer (32 bits)", true);
	JRadioButton doubleRdo = new JRadioButton("Double (64 bits)", false);
	JTextFieldEx decTextField = new JTextFieldEx();
	JTextFieldEx hexTextField = new JTextFieldEx();
	JButton logResultBtn = new JButton("Log Result");
	JButton helpBtn = new JButton();
	JTextArea logTextArea = new JTextArea();
	BinaryPane binPane = new BinaryPane();


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmRadixConverter = new JFrame();
		frmRadixConverter.setTitle("Radix Converter");
		Container contentPane = frmRadixConverter.getContentPane();

		frmRadixConverter.setSize(new Dimension(800, 600));

		JLabel lblDec = new JLabel("Dec");
		JLabel lblHex = new JLabel("Hex");
		JLabel lblBin = new JLabel("Bin");

		GroupLayout groupLayout = new GroupLayout(contentPane);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblDec)
							.addComponent(lblHex))
						.addComponent(lblBin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGap(1)
								.addComponent(helpBtn)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(logResultBtn))
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(intRdo)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(doubleRdo, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
									.addComponent(hexTextField, Alignment.LEADING)
									.addComponent(decTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))))
						.addComponent(binPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(logTextArea, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(logTextArea, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(doubleRdo)
								.addComponent(intRdo))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDec)
								.addComponent(decTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(hexTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHex))
							.addGap(8)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(binPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(logResultBtn)
										.addComponent(helpBtn)))
								.addComponent(lblBin))))
					.addGap(11))
		);
		contentPane.setLayout(groupLayout);

		frmRadixConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initComponents();
		initEvents();
	}

	private void refreshConstraintCharacter()
	{
		if (intRdo.isSelected())
		{
			hexTextField.setMaxLength(8);

			decTextField.setValidator(Utils.IntValidator);

			binPane.refreshConstraintCharacter(32);
		}
		else {

			hexTextField.setMaxLength(16);

			decTextField.setValidator(Utils.DoubleValidator);

			binPane.refreshConstraintCharacter(64);
		}
	}

	private void initComponents()
	{
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(intRdo);
		buttonGroup.add(doubleRdo);

		ImageIcon helpIcon = new ImageIcon(getClass().getResource(
				"help_icon.jpg"));
		helpBtn.setIcon(helpIcon);
		helpBtn.setContentAreaFilled(false);
		helpBtn.setBorder(BorderFactory.createEmptyBorder());

		logTextArea.setBorder(BorderFactory.createTitledBorder("Log results"));

		Font f = decTextField.getFont();
		Font bigText = new Font(f.getName(), f.getStyle(), f.getSize()+3);
		decTextField.setFont(bigText);
		hexTextField.setFont(bigText);

		hexTextField.setValidator(Utils.HexValidator);

		refreshConstraintCharacter();
	}

	private void initEvents() {
		intRdo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					binPane.showIntPane();
					cleanForm();
					refreshConstraintCharacter();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		doubleRdo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					binPane.showDoublePane();
					cleanForm();
					refreshConstraintCharacter();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		decTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onChanged() {
				if (skipChangeEvt>0) {
					return;
				}
				String dec = decTextField.getText().trim();
				if (Strings.isNullOrEmpty(dec)) {
					cleanForm();
					return;
				}

				skipChangeEvt++;
				try {
					String hex;
					if (intRdo.isSelected()) {
						int value = Integer.parseInt(dec);
						hex = RadixConverter.intToHex(value);
					}
					else {
						double value = Double.parseDouble(dec);
						hex = RadixConverter.doubleToHex(value);
					}
					String bin = RadixConverter.hexToBin(hex);

					hexTextField.setText(hex);
					binPane.setBinaryString(bin);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				finally {
					skipChangeEvt--;
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onChanged();
			}
		});

		hexTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onChanged() {
				if (skipChangeEvt>0) {
					return;
				}

				String hex = hexTextField.getText().trim();
				if (Strings.isNullOrEmpty(hex)) {
					cleanForm();
					return;
				}

				skipChangeEvt++;
				try {

					String bin = RadixConverter.hexToBin(hex);;
					String dec;

					if (intRdo.isSelected()) {
						dec = Integer.toString(RadixConverter.hexToInt(hex));
					}
					else {
						dec = Double.toString(RadixConverter.hexToDouble(hex));
					}

					decTextField.setText(dec);
					binPane.setBinaryString(bin);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				finally {
					skipChangeEvt--;
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onChanged();
			}
		});

		binPane.setActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (skipChangeEvt>0) {
					return;
				}

				String bin = binPane.getBinaryString();
				if (Strings.isNullOrEmpty(bin)) {
					cleanForm();
					return;
				}

				skipChangeEvt++;
				try {

					String hex = RadixConverter.binToHex(bin);
					String dec;
					if (intRdo.isSelected()) {
						dec = Integer.toString(RadixConverter.binToInt(bin));
					}
					else {
						dec = Double.toString(RadixConverter.binToDouble(bin));
					}
					decTextField.setText(dec);
					hexTextField.setText(hex);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				finally {
					skipChangeEvt--;
				}
			}
		});
	}

	private void cleanForm() {
		skipChangeEvt++;
		try {
			decTextField.setText("");
			hexTextField.setText("");
			binPane.setBinaryString("");
		}
		finally {
			skipChangeEvt--;
		}

	}
}
