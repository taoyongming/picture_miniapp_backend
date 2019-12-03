package web.util;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class RandomUtil {
	
	public static String randomString(Integer length){
		String randomString="";
		randomString=RandomStringUtils.randomAlphanumeric(length);
		return randomString;
	}
	
	public static int randNum(int min, int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}
	
	public static void main(String args[]){
//		System.out.println(RandomUtil.randomString(10));
		Random ra = new Random();
		System.out.println(ra.nextInt(1));
	}
	
}

