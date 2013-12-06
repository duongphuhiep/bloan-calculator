package com.bloan.calculator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
	//private String preventLogResultMsg = null;

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

	JScrollPane scrollLogTextArea;

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
		scrollLogTextArea = new JScrollPane (logTextArea,
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
					.addComponent(scrollLogTextArea, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollLogTextArea, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
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
		cleanForm();
	}

	private void initComponents()
	{
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(intRdo);
		buttonGroup.add(doubleRdo);

		ImageIcon helpIcon = new ImageIcon(getClass().getResource(
				"help-24.png"));
		helpBtn.setIcon(helpIcon);
		helpBtn.setContentAreaFilled(false);
		helpBtn.setBorder(BorderFactory.createEmptyBorder());

		scrollLogTextArea.setBorder(BorderFactory.createTitledBorder("Log results"));
		logTextArea.setEditable(false);
		logTextArea.setAutoscrolls(true);

		Font f = decTextField.getFont();
		Font bigText = new Font(f.getName(), f.getStyle(), f.getSize()+3);
		decTextField.setFont(bigText);
		hexTextField.setFont(bigText);

		hexTextField.setValidator(Utils.HexValidator);
	}

	private void initEvents() {
		intRdo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				intRdo_onChange();
			}
		});

		doubleRdo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				doubleRdo_onChange();
			}
		});

		decTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				decTextField_onChange();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				decTextField_onChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				decTextField_onChange();
			}
		});

		hexTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				hexTextField_onChange();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				hexTextField_onChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				hexTextField_onChange();
			}
		});

		binPane.setActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				binPane_onChange();
			}
		});

		logResultBtn.addActionListener(new ActionListener()
		{
			/**
			 * Generate a log entry and insert it to the first line
			 */
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logResultBtn_onClick();
			}
		});

		helpBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				helpBtn_onClick();
			}
		});
	}

	private void intRdo_onChange()
	{
		try {
			binPane.showIntPane();
			cleanForm();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void doubleRdo_onChange()
	{
		try {
			binPane.showDoublePane();
			cleanForm();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void decTextField_onChange() {
		if (skipChangeEvt>0) {
			decTextField.setErrorState(null);
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
			decTextField.setErrorState(null);

			allowLogResult();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			decTextField.setErrorState(ex.toString());
			forbidLogResult(ex.toString());
		}
		finally {
			skipChangeEvt--;
		}
	}

	private void hexTextField_onChange() {
		if (skipChangeEvt>0) {
			hexTextField.setErrorState(null);
			return;
		}

		String hex = hexTextField.getText().trim().toUpperCase();
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

			hexTextField.setErrorState(null);
			allowLogResult();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			hexTextField.setErrorState(ex.toString());
			forbidLogResult(ex.toString());
		}
		finally {
			skipChangeEvt--;
		}
	}

	private void binPane_onChange()
	{
		if (skipChangeEvt>0) {
			binPane.setErrorState(null);
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

			binPane.setErrorState(null);
			allowLogResult();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			binPane.setErrorState(ex.toString());
			forbidLogResult(ex.toString());
		}
		finally {
			skipChangeEvt--;
		}
	}

	private void logResultBtn_onClick()
	{
		try {
			StringBuilder logEntry = new StringBuilder();

			Date now = new Date();
			String mode = intRdo.isSelected() ? "Integer":"Double";
			logEntry.append(now.toString()+"    ---- "+mode+" ----\n");
			logEntry.append("Dec: "+RadixConverter.trimFirstZero(decTextField.getText())+"\n");
			logEntry.append("Hex: "+RadixConverter.trimFirstZero(hexTextField.getText())+"\n");
			logEntry.append("Bin: "+RadixConverter.trimFirstZero(binPane.getBinaryString())+"\n\n");

			logTextArea.insert(logEntry.toString(), 0);
			logTextArea.setCaretPosition(0);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			displayExceptionDlg(ex);
		}
	}

	private void helpBtn_onClick()
	{
		try {
			StringBuilder aboutMessage = new StringBuilder();
			aboutMessage.append("Radix Converter 1.0 (c) 2013\n");
			aboutMessage.append("- Bich Loan TRAN\n");
			aboutMessage.append("- Peter SALVATOR\n");
			JOptionPane.showMessageDialog(frmRadixConverter, aboutMessage.toString(), "About", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			displayExceptionDlg(ex);
		}
	}

	private void allowLogResult() {
		logResultBtn.setEnabled(true);
		//preventLogResultMsg = null;
		logResultBtn.setToolTipText(null);
	}
	private void forbidLogResult(String reason) {
		logResultBtn.setEnabled(false);
		//preventLogResultMsg = reason;
		logResultBtn.setToolTipText(reason);
	}

	private void cleanForm() {
		skipChangeEvt++;
		try {
			binPane.setBinaryString("");
			decTextField.setText("");
			hexTextField.setText("");

			binPane.setErrorState(null);
			decTextField.setErrorState(null);
			hexTextField.setErrorState(null);

			forbidLogResult("Nothing to log");
		}
		finally {
			skipChangeEvt--;
		}
	}

	private void displayExceptionDlg(Exception ex) {
		JOptionPane.showMessageDialog(frmRadixConverter, ex.getStackTrace(), ex.toString(), JOptionPane.ERROR_MESSAGE);
	}


}
