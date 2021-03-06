package com.bloan.calculator.utils;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;

import com.bloan.calculator.utils.Utils.IStringValidator;

public class JTextAreaEx extends JTextArea
{
	private static final long serialVersionUID = -62212732190370631L;

	public JTextAreaEx()
	{
		super();
		((AbstractDocument)this.getDocument()).setDocumentFilter(new DocumentStringFilter(this, Utils.DefaultValidator));
	}

	public void setValidator(IStringValidator validator) {
		((DocumentStringFilter)((AbstractDocument)this.getDocument()).getDocumentFilter()).setStringValidator(validator);
	}
}
