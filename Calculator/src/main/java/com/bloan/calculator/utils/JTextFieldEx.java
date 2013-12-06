package com.bloan.calculator.utils;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;

import com.bloan.calculator.utils.Utils.IStringValidator;
import com.google.common.base.Strings;

public class JTextFieldEx extends JTextField
{
	private static final long serialVersionUID = -62212732190370631L;
	private final Border normalBorder;


	public JTextFieldEx()
	{
		super();
		((AbstractDocument)this.getDocument()).setDocumentFilter(new DocumentStringFilter(this, Utils.DefaultValidator));
		normalBorder = this.getBorder();
	}

	public void setValidator(IStringValidator validator) {
		((DocumentStringFilter)((AbstractDocument)this.getDocument()).getDocumentFilter()).setStringValidator(validator);
	}

	public void setErrorState(String errorMessage)
	{
		this.setToolTipText(errorMessage);
		if (Strings.isNullOrEmpty(errorMessage))
		{
			this.setBorder(normalBorder);
		}
		else
		{
			this.setBorder(Utils.redBorder);
		}
	}
}
