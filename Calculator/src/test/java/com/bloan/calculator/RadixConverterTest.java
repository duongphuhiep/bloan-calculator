package com.bloan.calculator;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import junit.framework.Assert;

import org.junit.Test;

import com.bloan.calculator.utils.RadixConverter;

public class RadixConverterTest
{

	@Test
	public void testHexToDouble()
	{
		assertEquals(0, RadixConverter.hexToDouble("0000000000000000"), 1e-120);
		assertEquals(-148e-60, RadixConverter.hexToDouble("B3EDBA756557A01C"),
				1e-120);
		assertEquals(120e+6, RadixConverter.hexToDouble("419C9C3800000000"),
				1e-120);
		assertEquals(-120e+6, RadixConverter.hexToDouble("C19C9C3800000000"),
				1e-120);
		assertEquals(-123456789.987654321e-128,
				RadixConverter.hexToDouble("A70980F2A52A4E6A"), 1e-200);
	}

	@Test
	public void testHexToInt()
	{
		assertEquals(0, RadixConverter.hexToInt("00000000"));
		assertEquals(-1, RadixConverter.hexToInt("FFFFFFFF"));
		assertEquals(1, RadixConverter.hexToInt("00000001"));
		assertEquals(-16, RadixConverter.hexToInt("FFFFFFF0"));
		assertEquals(16, RadixConverter.hexToInt("00000010"));
	}

	@Test
	public void testDoubleToHex()
	{
		assertEquals("B3EDBA756557A01C", RadixConverter.doubleToHex(-148e-60));
		assertEquals("419C9C3800000000", RadixConverter.doubleToHex(120e+6));
		assertEquals("C19C9C3800000000", RadixConverter.doubleToHex(-120e+6));
		assertEquals("0", RadixConverter.doubleToHex(0));
		assertEquals("AAAAAAAAAAAAAAAA",
				RadixConverter
						.doubleToHex(-3.7206620809969885439391603375E-103));
		assertEquals("A70980F2A52A4E6A",
				RadixConverter.doubleToHex(-123456789.987654321e-128));
	}

	@Test
	public void testIntToHex()
	{
		assertEquals("0", RadixConverter.intToHex(0));
		assertEquals("FFFFFFFF", RadixConverter.intToHex(-1));
		assertEquals("1", RadixConverter.intToHex(1));
		assertEquals("FFFFFFF0", RadixConverter.intToHex(-16));
		assertEquals("10", RadixConverter.intToHex(16));
	}

	@Test
	public void testTrimFirstZero()
	{
		assertEquals("0", RadixConverter.trimFirstZero("0000"));
		assertEquals("ABC", RadixConverter.trimFirstZero("00ABC"));
		assertEquals("0.0321", RadixConverter.trimFirstZero("0.0321"));
		assertEquals("0.321", RadixConverter.trimFirstZero("0000.321"));
	}

	@Test(expected = InvalidParameterException.class)
	public void testHexToBin()
	{
		assertEquals("0", RadixConverter.hexToBin("0"));
		assertEquals("101", RadixConverter.hexToBin("5"));
		assertEquals("10101010111100", RadixConverter.hexToBin("2ABC"));
		RadixConverter.hexToBin("-2ABC");
	}

	@Test
	public void testBinToHex()
	{
		assertEquals("0", RadixConverter.binToHex("0"));
		assertEquals("2", RadixConverter.binToHex("10"));
		assertEquals("5", RadixConverter.binToHex("101"));
		assertEquals("B", RadixConverter.binToHex("1011"));
		assertEquals("1B", RadixConverter.binToHex("011011"));
		assertEquals("9B", RadixConverter.binToHex("10011011"));
		assertEquals("39B", RadixConverter.binToHex("001110011011"));
	}

	@Test
	public void testBinHexToInt()
	{
		final int[] numbersToTest = new int[] {
				0,
				12345,
				-12345,
				Integer.MIN_VALUE,
				Integer.MAX_VALUE };

		for (final int x : numbersToTest)
		{
			String hexStr = RadixConverter.intToHex(x);
			String binStr = RadixConverter.hexToBin(hexStr);
			Assert.assertEquals(x, RadixConverter.binToInt(binStr));
		}
	}

	@Test
	public void testBinHexToDouble()
	{
		final double[] numbersToTest = new double[] {
				0,
				12345e120,
				-12345e120,
				-54321e120,
				54321e-210,
				Double.MIN_VALUE,
				Double.MAX_VALUE,
				Double.NaN,
				Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY };

		for (final double x : numbersToTest)
		{
			String hexStr = RadixConverter.doubleToHex(x);
			String binStr = RadixConverter.hexToBin(hexStr);
			Assert.assertEquals(x, RadixConverter.binToDouble(binStr));
		}
	}

	@Test
	public void testSubString()
	{
		assertEquals("1", "123456789".substring(0, 1));
		assertEquals("6789", "123456789".substring(9 - 4));
		assertEquals("5678", "123456789".substring(4, 8));
		assertEquals("6789", "123456789".substring(5, 9));

		assertEquals(-3.7206620809969885E-103,
				Double.parseDouble("-3.7206620809969885439391603375E-103"),
				1e-150);
	}
}
