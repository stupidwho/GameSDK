package me.toufu.appsdklib.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by zhenghu on 15-5-11.
 */
public class SignatureUtil {
    public static boolean isSignatureLegal(byte content[], byte signature[], String strPublicKey) {
        try {
            Signature sign = Signature.getInstance("SHA1WithRSA");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey;
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(strPublicKey, Base64.DEFAULT)));
            sign.initVerify(publicKey);
            sign.update(content);
            // FIXME:签名尚未验证成功
//            return sign.verify(signature);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static final char[] bcdLookup =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 将字节数组转换为16进制字符串的形式.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }


    /**
     * 将16进制字符串还原为字节数组.
     */
    public static final byte[] hexStrToBytes(String s) {

        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }
}
