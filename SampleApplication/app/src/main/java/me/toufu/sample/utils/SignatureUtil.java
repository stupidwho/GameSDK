package me.toufu.sample.utils;

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
    public static boolean isSignatureLegal(String content, String ciphertext, String strPublicKey) {
        try {
            Signature sign = Signature.getInstance("SHA1WithRSA");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey;
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(strPublicKey, Base64.DEFAULT)));
            sign.initVerify(publicKey);
            sign.update(content.getBytes());
            return sign.verify(ciphertext.getBytes());
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
}
