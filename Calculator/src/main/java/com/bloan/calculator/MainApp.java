package com.bloan.calculator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.google.common.base.Strings;

import net.java.dev.designgridlayout.DesignGridLayout;

public class MainApp
{
	private JFrame frame;
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
					window.frame.setVisible(true);
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
	JTextField decTextField = new JTextField();
	JTextField hexTextField = new JTextField();
	JButton logResultBtn = new JButton("Log Result");
	JButton helpBtn = new JButton();
	JTextArea logTextArea = new JTextArea();
	BinaryPane binPane = new BinaryPane();


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();

		JPanel panelCalculator = new JPanel();
		{
			DesignGridLayout layout = new DesignGridLayout(panelCalculator);
			layout.disableSmartVerticalResize();
			layout.row().grid().add(intRdo, doubleRdo);
			layout.row().grid(new JLabel("Dec")).add(decTextField);
			layout.row().grid(new JLabel("Hex")).add(hexTextField);
			layout.row().grid(new JLabel("Bin")).add(binPane);
			layout.emptyRow();
			layout.row().bar().left(helpBtn).right(logResultBtn);
		}

		frame.setBounds(100, 100, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.getContentPane().add(logTextArea, BorderLayout.CENTER);
		frame.getContentPane().add(panelCalculator, BorderLayout.WEST);

		initComponents();
		initEvents();
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
