package com.fdmgroup.GenshinImpactLoadoutManager.controller;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

public class Utils
{
	public static Boolean checkLogin(HttpSession session, String userType)
	{
		if (userType.equals("admin") && userType.equals(session.getAttribute("role")))
		{
			return true;
		}
		if (userType.equals("user") && userType.equals(session.getAttribute("role")))
		{
			return true;
		}

		return false;
	}

	public static String encryptPass(String input) throws NoSuchAlgorithmException
	{
		return toHexString(getSHA(input));
	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String toHexString(byte[] hash)
	{
		BigInteger number = new BigInteger(1, hash);

		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 32)
		{
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}
}
