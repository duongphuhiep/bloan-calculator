package com.bloan.calculator.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PlainDocumentLimit extends PlainDocument
{
	private static final long serialVersionUID = 6996483430089972321L;
	private int limit = Integer.MAX_VALUE;

	PlainDocumentLimit()
	{
		super();
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException
	{
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit)
		{
			super.insertString(offset, str, attr);
		}
	}
}