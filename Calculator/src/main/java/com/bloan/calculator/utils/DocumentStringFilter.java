package com.bloan.calculator.utils;

import javax.swing.JComponent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import com.bloan.calculator.utils.Utils.IStringValidator;

/**
	 * Filter component with a IStringValidator, set red border to component if
	 * the value is not valid
	 */
	public class DocumentStringFilter extends DocumentFilter
	{
		private IStringValidator stringValidator;
		private final JComponent source;
//		private final Border originalBorder;
//		private final static Border redBorder = BorderFactory.createLineBorder(
//				Color.RED, 3);

		public DocumentStringFilter(JComponent source, IStringValidator stringValidator)
		{
			this.source = source;
			this.stringValidator = stringValidator;
//			this.originalBorder = source.getBorder();
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException
		{
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);

			if (stringValidator.isValid(sb.toString()))
			{
				super.insertString(fb, offset, string, attr);
//				this.source.setBorder(originalBorder);
			}
			else
			{
//				this.source.setBorder(redBorder);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length,
				String text, AttributeSet attrs) throws BadLocationException
		{

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.replace(offset, offset + length, text);

			if (stringValidator.isValid(sb.toString()))
			{
				super.replace(fb, offset, length, text, attrs);
//				this.source.setBorder(originalBorder);
			}
			else
			{
//				this.source.setBorder(redBorder);
			}
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException
		{
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset + length);

			if (stringValidator.isValid(sb.toString()))
			{
				super.remove(fb, offset, length);
//				this.source.setBorder(originalBorder);
			}
			else
			{
//				this.source.setBorder(redBorder);
			}
		}

		public IStringValidator getStringValidator()
		{
			return stringValidator;
		}
		public void setStringValidator(IStringValidator stringValidator)
		{
			this.stringValidator = stringValidator;
		}
	}