package com.bloan.calculator.utils;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.bloan.calculator.utils.Utils.IStringValidator;

public class JTextFieldEx extends JTextField
{
	private static final long serialVersionUID = -62212732190370631L;

	public JTextFieldEx()
	{
		super();
		this.setDocument(new PlainDocumentLimit());
		((AbstractDocument)this.getDocument()).setDocumentFilter(new DocumentStringFilter(this, Utils.DefaultValidator));
	}

	public void setMaxLength(int maxLength) {
		((PlainDocumentLimit)this.getDocument()).setLimit(maxLength);
	}

	public void setValidator(IStringValidator validator) {
		((DocumentStringFilter)((AbstractDocument)this.getDocument()).getDocumentFilter()).setStringValidator(validator);
	}
}
