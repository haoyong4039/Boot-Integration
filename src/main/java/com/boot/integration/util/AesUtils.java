package com.boot.integration.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @desc AES 加密工具类
 */
public class AesUtils
{

    private static final String KEY_ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password)
    {
        try
        {
            SecretKeySpec sKey = new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);// 生成加密秘钥

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器

            cipher.init(Cipher.ENCRYPT_MODE, sKey);// 使用密钥初始化，设置为加密模式

            // 1.序列化
            byte[] byteContent = content.getBytes("utf-8");

            // 2.加密
            byte[] result = cipher.doFinal(byteContent);// 加密

            // 3.Base64转码为字符串
            return Base64.encodeBase64String(result);
        }
        catch (Exception ex)
        {
            Logger.getLogger(AesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password)
    {
        try
        {
            SecretKeySpec sKey = new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);// 生成加密秘钥

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器

            cipher.init(Cipher.DECRYPT_MODE, sKey);// 使用密钥初始化，设置为解密模式

            // 1.base64转码为字节
            byte[] bytes = Base64.decodeBase64(content);

            // 2.解密
            byte[] result = cipher.doFinal(bytes);

            // 3.反序列化
            return new String(result, "utf-8");
        }
        catch (Exception ex)
        {
            Logger.getLogger(AesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void main(String[] args)
    {
        String content = "{\"password\":\"670b14728ad9902aecba32e22fa4f6bd\",\"phone\":\"18351845667\"}";

        String password = "42d5a42ddebfc247";

        //加密
        String enResult = AesUtils.encrypt(content, password);
        System.out.println(enResult);

        //解密
        String deResult = AesUtils.decrypt(enResult, password);
        System.out.println(deResult);
    }

}
