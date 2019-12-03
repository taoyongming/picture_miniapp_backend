package web.util;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesSecurity {
	private final static BASE64Encoder ENCODER = new BASE64Encoder();
	private final static BASE64Decoder DECODER = new BASE64Decoder();

	private Cipher enCipher;
	private Cipher deCipher;

	/**
	 * @param key 加密密钥
	 * @param iv 初始化向量
	 * @throws Exception
	 */
	public DesSecurity() throws Exception {
		initCipher(KEY.getBytes(), IV.getBytes());
	}

	private void initCipher(byte[] secKey, byte[] secIv) throws Exception {
		// 获得DES密钥
		DESKeySpec dks = new DESKeySpec(secKey);
		// 获得DES加密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 生成加密密钥
		SecretKey key = keyFactory.generateSecret(dks);
		// 创建初始化向量对象
		IvParameterSpec iv = new IvParameterSpec(secIv);
		AlgorithmParameterSpec paramSpec = iv;
		// 为加密算法指定填充方式，创建加密会话对象
		enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 为加密算法指定填充方式，创建解密会话对象
		deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 初始化加解密会话对象
		enCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		deCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密二进制数据
	 * @return 经BASE64编码过的加密数据
	 * @throws Exception
	 */
	public String encrypt(byte[] data) throws Exception {
		return ENCODER.encode(enCipher.doFinal(data)).replaceAll("\n", "")
				.replaceAll("\r", "");
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密字符串（经过BASE64编码）
	 * @return 解密后的二进制数据
	 * @throws Exception
	 */
	public byte[] decrypt(String data) throws Exception {
		return deCipher.doFinal(DECODER.decodeBuffer(data));
	}

	/**
	 * MD5
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	// TEST
	private static final String KEY = "yc$@ycgk";
	private static final String IV = "salt#&@!"; 

	public static void main(String[] args) throws Exception {
		String uri = "/SanofiWechatServer/api/department/user/list";
		String params = "departmentCode=1";
//		String str = uri + "_&" + params + "&";
		String headerParam="_&appId=4&content=thisisatest&touser=jane_test&";
		String str="/SanofiWechatServer/api/message/sendText"+headerParam;
		System.out.println(str);
		String desStr1 = new DesSecurity().encrypt(str.getBytes("utf-8"));
		System.out.println("signature:" + desStr1);
		System.out.println("signature:" + md5(desStr1));
		 
		String desStr =new String(new DesSecurity().decrypt(desStr1));
		
		System.out.println("signature:" + desStr);
	}
}
