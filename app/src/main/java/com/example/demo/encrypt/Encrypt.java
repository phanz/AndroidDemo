package com.example.demo.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encrypt {
    private static final String KEY = "password_text";//例： ern#%432b2@s5c89bgD-_54
    private static final String ALGORITHM = "PBEWITHMD5andDES";
    private static byte[] salt = new byte[]{(byte)24, (byte)-121, (byte)-109, (byte)109, (byte)53, (byte)58, (byte)106, (byte)-96};

    public Encrypt() {

    }

    private static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        StringBuilder stringBuilder = new StringBuilder(iLen * 2);

        for(int i = 0; i < iLen; ++i) {
            int intTmp;
            for(intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
                ;
            }
            if(intTmp < 16) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toString(intTmp, 16));
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];

        for(int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    private static Key toKey(String password) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(keySpec);
    }

    public static byte[] encrypt(byte[] data) {
        if(data == null) {
            return null;
        } else {
            try {
                Key e = toKey(KEY);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, e, paramSpec);
                return cipher.doFinal(data);
            } catch (Exception var4) {
                return null;
            }
        }
    }

    public static byte[] encrypt(byte[] data, char[] password) {
        if(data == null) {
            return null;
        } else {
            try {
                PBEKeySpec e = new PBEKeySpec(password);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
                SecretKey key = keyFactory.generateSecret(e);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                return cipher.doFinal(data);
            } catch (Exception var7) {
                return null;
            }
        }
    }

    public static String encrypt(String data) {
        if(data == null) {
            return "";
        } else {
            byte[] tmp = encrypt(data.getBytes());
            return byteArr2HexStr(tmp);
        }
    }

    public static String encrypt(String data, String password) {
        if(data == null) {
            return "";
        } else {
            byte[] tmp = encrypt(data.getBytes(), password.toCharArray());
            return byteArr2HexStr(tmp);
        }
    }

    public static byte[] decrypt(byte[] data) {
        if(data == null) {
            return null;
        } else {
            try {
                Key e = toKey(KEY);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, e, paramSpec);
                return cipher.doFinal(data);
            } catch (Exception var4) {
                return null;
            }
        }
    }

    public static byte[] decrypt(byte[] data, char[] password) {
        if(data == null) {
            return null;
        } else {
            try {
                PBEKeySpec e = new PBEKeySpec(password);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
                SecretKey key = keyFactory.generateSecret(e);
                PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                return cipher.doFinal(data);
            } catch (Exception var7) {
                return null;
            }
        }
    }

    public static String decrypt(String data) {
        if(data == null) {
            return "";
        } else {
            byte[] tmp = decrypt(hexStr2ByteArr(data));
            return new String(tmp);
        }
    }

    public static String decrypt(String data, String password) {
        if(data == null) {
            return "";
        } else {
            byte[] tmp = decrypt(hexStr2ByteArr(data), password.toCharArray());
            return new String(tmp);
        }
    }
}
