package com.bloan.calculator.utils;

import com.google.common.base.Strings;


public class Utils
{
	public static interface IStringValidator
	{
		public boolean isValid(String text);
	}

	public static IStringValidator DoubleValidator = new IStringValidator()
	{
		@Override
		public boolean isValid(String text)
		{
			return true;
//			try
//			{
//				Double.parseDouble(text);
//				return true;
//			}
//			catch (NumberFormatException e)
//			{
//				return false;
//			}
		}
	};
	public static IStringValidator IntValidator = new IStringValidator()
	{
		@Override
		public boolean isValid(String text)
		{
			try
			{
				if (Strings.isNullOrEmpty(text)) {
					return true;
				}

				Integer.parseInt(text);
				return true;
			}
			catch (NumberFormatException e)
			{
				return false;
			}
		}
	};
	public static IStringValidator HexValidator = new IStringValidator()
	{
		@Override
		public boolean isValid(String text)
		{
			for (char c : text.trim().toUpperCase().toCharArray())
			{
				if (!RadixConverter.HexBinRef.containsKey(Character.toString(c)))
				{
					return false;
				}
			}
			return true;
		}
	};
	public static IStringValidator BinValidator = new IStringValidator()
	{
		@Override
		public boolean isValid(String text)
		{
			for (char c : text.trim().toCharArray())
			{
				if (c != '0' && c != '1')
				{
					return false;
				}
			}
			return true;
		}
	};
	public static IStringValidator DefaultValidator = new IStringValidator()
	{
		@Override
		public boolean isValid(String text)
		{
			return true;
		}
	};
}
