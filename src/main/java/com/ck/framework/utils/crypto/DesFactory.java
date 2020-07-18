package com.ck.framework.utils.crypto;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/5/21
 * @Content:
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesFactory {
    /**
     * des 加密
     * @param data  需要加密内容
     * @param key 密令
     * @return
     */
    public static byte[] desCrypto(byte[] data, String key) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(data);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * des 解密
     * @param data  需要加密内容
     * @param key 密令
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        try{
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return cipher.doFinal(data);
        }catch(Exception e ){
            e.printStackTrace();
            System.out.println("使用第二种解密");
            try{

                byte[] b = Base64Utils.decode(new String(data,"UTF-8")); //hexStr2ByteArr(n);
                // DES算法要求有一个可信任的随机数源
                SecureRandom random = new SecureRandom();
                // 创建一个DESKeySpec对象
                DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey securekey = keyFactory.generateSecret(desKey);
                // Cipher对象实际完成解密操作
                Cipher cipher = Cipher.getInstance("DES");
                // 用密匙初始化Cipher对象
                cipher.init(Cipher.DECRYPT_MODE, securekey, random);
                // 真正开始解密操作
                return cipher.doFinal(b);
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes("UTF-8");
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }


    /*
     * //字符串加密
     * */
    public static String encrypt(String str, String sKey,String sIv){
        String result = "";
        byte[] bKey;
        byte[] bIv;
        try {
            bKey = sKey.getBytes("utf-8");
            bIv = sIv.getBytes("utf-8");
            DESedeKeySpec keySpec=new DESedeKeySpec(bKey);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("desede");
            SecretKey key=keyFactory.generateSecret(keySpec);
            Cipher cipher=Cipher.getInstance("desede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(bIv));
            byte[] encrypted = cipher.doFinal(str.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decrypt(String src, String sKey ,String sIv) {
        byte[] key;
        byte[] bIv;
        String result = "";
        try {
            key = sKey.getBytes("utf-8");
            bIv = sIv.getBytes("utf-8");
            IvParameterSpec iv = new IvParameterSpec(bIv);
            DESedeKeySpec desKey = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(src);
            result = new String(cipher.doFinal(encrypted1),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String timestamp = "1531382430000"; // 时间戳 String
        String nonce = "91b9a8bd6b174fee833a5ef5cc60d0be"; // 随机定义一随机码 String token =
        String token  = "c1e89908-d565-44f3-ab7e-614d47240292";//"53cce612-dfd2-4956-a19b-7f6407c3368d";//"612c219c-9c7d-40fe-a6f7-990aad14ef01";
        String userName="rmyy5";//appDataTest zzsdlrmyy
        String passWord = "88888888";
        System.out.println(passWord);
        // 调用接口方法进行SHA加密
        //String signature = new BigInteger(SHA1.encryptSHA(timestamp, nonce, token)).toString(64);
        //System.out.println("-------:"+signature);
//        String signature = SHA1.encryptSHA1(timestamp, nonce, token);
//        System.out.println("-------:"+signature);

    }
}
