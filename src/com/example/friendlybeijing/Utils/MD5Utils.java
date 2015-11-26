package com.example.friendlybeijing.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils 
{
	private static StringBuffer sb;

	public static String enCode(String inputString)
	{
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			byte [] digest = instance.digest(inputString.getBytes());
			sb = new StringBuffer();
			for(byte b:digest)
			{
				int i =  b & 0xff;
				String hexString = Integer.toHexString(i);
				if(hexString.length()<2)
				{
					hexString ="0"+hexString;    //如果是1位的话,补0
				}
				sb.append(hexString);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // 获取MD5算法对象
		return sb.toString();
	}
}
