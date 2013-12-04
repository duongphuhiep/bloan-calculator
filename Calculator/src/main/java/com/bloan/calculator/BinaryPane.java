package com.bloan.calculator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.java.dev.designgridlayout.DesignGridLayout;

public class BinaryPane extends JPanel
{
	private static final long serialVersionUID = -6487988247126956202L;

	private JTextArea textField;

	JPanel bitsPanel = new JPanel();
	JPanel intPane = new JPanel();
	JPanel doublePane = new JPanel();

	ArrayList<JCheckBox> intBitsCheck = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> doubleBitsCheck = new ArrayList<JCheckBox>();

	private int skipChangeEvt = 0;
	private boolean isDoublePaneShowed = false;

	ActionListener actionListener;

	/**
	 * Create the panel.
	 */
	public BinaryPane()
	{
		setLayout(new BorderLayout(0, 0));

		textField = new JTextArea();
		textField.setLineWrap(true);
		textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//Dimension preferredSize = textField.getPreferredSize();
		//preferredSize.height = 50;
		//textField.setPreferredSize(preferredSize);
		add(textField, BorderLayout.NORTH);
		//textField.setColumns(10);

		add(bitsPanel, BorderLayout.CENTER);
		bitsPanel.setLayout(new CardLayout(0, 0));

		bitsPanel.add(intPane, "32");
		bitsPanel.add(doublePane, "64");
		bitsPanel.setBorder(BorderFactory.createEtchedBorder());

		initIntPane();
		initDoublePane();

		textField.getDocument().addDocumentListener(new DocumentListener()
		{
			private void onChanged() {
				if (skipChangeEvt>0) {
					return;
				}
				skipChangeEvt++;
				try {
					setBinaryStringToGrid(textField.getText());
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				finally {
					skipChangeEvt--;
				}
				fireActionListener();
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

		//showIntPane();
		//showDoublePane();
	}

	private void initIntPane()
	{
		for (int i = 0; i<32; i++) {
			JCheckBox bit = new JCheckBox();
			intBitsCheck.add(bit);
			bit.addActionListener(bitActionListener);
		}
		DesignGridLayout layout = new DesignGridLayout(intPane);
		layout.row().grid().add(intBitsCheck.subList(0, 8).toArray(new JCheckBox[0]));
		layout.row().grid().add(intBitsCheck.subList(8, 16).toArray(new JCheckBox[0]));
		layout.row().grid().add(intBitsCheck.subList(16, 24).toArray(new JCheckBox[0]));
		layout.row().grid().add(intBitsCheck.subList(24, 32).toArray(new JCheckBox[0]));
	}

	private void initDoublePane()
	{
		for (int i = 0; i<64; i++) {
			JCheckBox bit = new JCheckBox();
			doubleBitsCheck.add(bit);
			bit.addActionListener(bitActionListener);
		}

		DesignGridLayout layout = new DesignGridLayout(doublePane);
		layout.row().grid().add(doubleBitsCheck.get(0));

		layout.emptyRow();
		layout.row().grid().add(doubleBitsCheck.subList(1, 12).toArray(new JCheckBox[0])).empty(2);

		layout.emptyRow();
		layout.row().grid().add(doubleBitsCheck.subList(12, 25).toArray(new JCheckBox[0]));
		layout.row().grid().add(doubleBitsCheck.subList(25, 38).toArray(new JCheckBox[0]));
		layout.row().grid().add(doubleBitsCheck.subList(38, 51).toArray(new JCheckBox[0]));
		layout.row().grid().add(doubleBitsCheck.subList(51, 64).toArray(new JCheckBox[0]));
	}

	private ActionListener bitActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (skipChangeEvt > 0) {
				return;
			}
			skipChangeEvt++;
			try {
				textField.setText(getBinaryStringFromGrid());
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			finally {
				skipChangeEvt--;
			}
			fireActionListener();
		}
	};


	public void showIntPane()
	{
		CardLayout cl = (CardLayout)(bitsPanel.getLayout());
		cl.show(bitsPanel, "32");
		isDoublePaneShowed = false;
		textField.setText(getBinaryStringFromGrid());
	}

	public void showDoublePane()
	{
		CardLayout cl = (CardLayout)(bitsPanel.getLayout());
		cl.show(bitsPanel, "64");
		isDoublePaneShowed = true;
		textField.setText(getBinaryStringFromGrid());
	}

	public void setBinaryString(String s) {
		this.textField.setText(s); //it will fire event to update the grid
		//this.setBinaryStringToGrid(s);
	}

	public String getBinaryString() {
		return this.textField.getText();
	}

	public void setActionListener(ActionListener actionListener)
	{
		this.actionListener = actionListener;
	}

	/**
	 * Fire when user change value bit
	 */
	private void fireActionListener() {
		if (actionListener!=null) {
			actionListener.actionPerformed(null);
		}
	}

	/**
	 * Retreive the binary string from the checkbox grids
	 */
	private String getBinaryStringFromGrid() {
		StringBuilder resu = new StringBuilder();
		if (isDoublePaneShowed) {
			for (int i = 0; i<64; i++) {
				resu.append(doubleBitsCheck.get(i).isSelected() ? "1" : "0");
			}
		}
		else {
			for (int i = 0; i<32; i++) {
				resu.append(intBitsCheck.get(i).isSelected() ? "1" : "0");
			}
		}
		return RadixConverter.trimFirstZero(resu.toString());
	}

	/**
	 * Set checkbox grids with a binary string
	 */
	private void setBinaryStringToGrid(String s) {
		if (isDoublePaneShowed) {
			setBinaryStringToGrid(doubleBitsCheck, s);
		}
		else {
			setBinaryStringToGrid(intBitsCheck, s);
		}
	}

	private void setBinaryStringToGrid(ArrayList<JCheckBox> checkBits, String s)
	{
		int maxBits = checkBits.size();
		if (s.length() > maxBits) {
			throw new InvalidParameterException("The string is too long to be display in the "+maxBits+" bits grids");
		}
		int d = maxBits-s.length();
		for (int i = 0;  i<d; i++) {
			checkBits.get(i).setSelected(false);
		}
		for (int i = 0; i<s.length(); i++) {
			checkBits.get(i+d).setSelected(s.charAt(i) == '1');
		}
	}

}
