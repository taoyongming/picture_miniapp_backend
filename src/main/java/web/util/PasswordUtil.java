package web.util;


import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordUtil {

	/**
	 * 利用md5进行加密
	 */
	public static String md5(String source) {
		Md5Hash hash = new Md5Hash(source);
		return hash.toString();
	}
	
	/**
	 * 利用md5进行加密,提供盐
	 */
	public static String md5(String source,Object salt) {
		//执行1024次hash加密.
		Md5Hash hash = new Md5Hash(source,salt,1024);
		return hash.toString();
	}
	
	/**
	 * 利用md5进行加密,提供盐,指定了加密次数.
	 */
	public static String md5(String source,Object salt,int hashIterations) {
		//执行1024次hash加密.
		Md5Hash hash = new Md5Hash(source,salt,hashIterations);
		return hash.toString();
	}
}
