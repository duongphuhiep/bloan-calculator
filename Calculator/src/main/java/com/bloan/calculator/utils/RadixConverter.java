package com.bloan.calculator.utils;

import java.math.BigInteger;
import java.security.InvalidParameterException;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Convert between base.
 *<p/>
 * Int: 32 bits, signed with two's complement<br/>
 * Double: 64 bits IEEE 754 (1 bit signed + 11 bits exponents + 52 bits fraction)
 */
public class RadixConverter {

	public final static BiMap<String, String> HexBinRef = HashBiMap.create(16);
	static
	{
		HexBinRef.put("0", "0000");
		HexBinRef.put("1", "0001");
		HexBinRef.put("2", "0010");
		HexBinRef.put("3", "0011");
		HexBinRef.put("4", "0100");
		HexBinRef.put("5", "0101");
		HexBinRef.put("6", "0110");
		HexBinRef.put("7", "0111");
		HexBinRef.put("8", "1000");
		HexBinRef.put("9", "1001");
		HexBinRef.put("A", "1010");
		HexBinRef.put("B", "1011");
		HexBinRef.put("C", "1100");
		HexBinRef.put("D", "1101");
		HexBinRef.put("E", "1110");
		HexBinRef.put("F", "1111");
	};

	public static int hexToInt(String hexString) {
		return Long.valueOf(hexString.trim(), 16).intValue();
	}
	public static double hexToDouble(String hexString) {
		return Double.longBitsToDouble(new BigInteger(hexString.trim(), 16)
				.longValue());
	}
	public static int binToInt(String binString) {
		return Long.valueOf(binString.trim(), 2).intValue();
	}
	public static double binToDouble(String binString) {
		return Double
				.longBitsToDouble(new BigInteger(binString.trim(), 2).longValue());
	}
	public static String intToHex(int n) {
		return String.format("%X", n);
	}
	public static String doubleToHex(double n) {
		return String.format("%X", Double.doubleToLongBits(n));
	}

	/**
	 * Convert a hexadecimal string to the binary presentation.<p/>
	 *
	 * {@link #hexToBin(String, boolean)} with trimFirstZero = true
	 * @param hexString
	 */
	public static String hexToBin(String hexString) {
		return hexToBin(hexString, true);
	}
	/**
	 * Convert a hexadecimal string to the binary presentation.<p/>
	 *
	 *  Algorithm: use a simple mapping table to convert directly from hexadecimal to binary (for the best performance)
	 *  <br/>
	 *  1 digit in hexadecimal ('0'..'F') equivalent to 4 digits in binary ('0','1').<br/>
	 *  So the result string is often 4 times longer than the hexString
	 * <br/>
	 *  set trimFirstZero = true, to trim the meaning less zero which prefix the result
	 *  See: {@link #trimFirstZero(String) trimFirstZero} method.
	 */
	public static String hexToBin(String hexString, boolean trimFirstZero) {
		StringBuilder resu = new StringBuilder();
		for (char c : hexString.trim().toCharArray()) {
			String cs = Character.toString(c);
			if (!HexBinRef.containsKey(cs)) {
				throw new InvalidParameterException(String.format("Invalid hexadecimal character '%s' in '%s'", cs, hexString));
			}
			resu.append(HexBinRef.get(cs));
		}
		if (trimFirstZero) {
			return trimFirstZero(resu.toString());
		}
		return resu.toString();
	}
	/**
	 *  Convert a binary string to the hexadecimal presentation.<p/>
	 *
	 * @param binString
	 * @return
	 */
	public static String binToHex(String binString) {
		binString = binString.trim();
		if (binString.isEmpty()) {
			return "";
		}

		binString = trimFirstZero(binString);

		String resu = "";

		int p = 0;
		for (p = binString.length(); p > 4; p-=4) {
			String bin4 = binString.substring(p-4, p);
			if (!HexBinRef.inverse().containsKey(bin4)) {
				throw new InvalidParameterException(String.format("Ivalid binary character in '%s' of '%s'", bin4, binString));
			}
			resu = HexBinRef.inverse().get(bin4) + resu;
		}

		if (p>0) {
			String bin4 = Strings.repeat("0", 4-p)+binString.substring(0,p);
			if (!HexBinRef.inverse().containsKey(bin4)) {
				throw new InvalidParameterException(String.format("Ivalid binary character in '%s' of '%s'", bin4, binString));
			}
			resu = HexBinRef.inverse().get(bin4) + resu;
		}

		return resu;
	}

	/**
	 * Trim meaning less zeros prefix of numbers
	 * <pre>
	 * "0000012.34" => "12.34"
	 * "0000000FFA" => "FFA"
	 * </pre>
	 * Particular case
	 * <pre>
	 * "0000000.34" => "0.34"
	 * "0000000000" => "0"
	 *  </pre>
	 */
	public static String trimFirstZero(String s) {

		if (s.isEmpty()) {
			return "";
		}

		//trim all zero

	    int i = 0;
	    int l = s.length();
	    while (i < l && s.charAt(i)=='0') {
	        i++;
	    }
	    String resu = s.substring(i);

	    //particular case

		if (resu.isEmpty()) { // 0000 => 0
			return "0";
		}
		else if (resu.startsWith(".")) { //000.34 => 0.34
			return "0"+resu;
		}
		else {
			return resu;
		}
	}

}
