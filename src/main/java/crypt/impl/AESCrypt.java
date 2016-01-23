package crypt.impl;

import crypt.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.Properties;

public class AESCrypt implements Crypt {
  @Inject
  private Properties properties;

  public AESCrypt() {
  }

  @Override
  public String encode(String plainText) {
    Cipher cipher;
    try {
      cipher = createCipher(true);
    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidParameterSpecException | InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    byte[] encryptedBytes;
    try {
      encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
    } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  @Override
  public String decode(String encodedText) {
    Cipher cipher;
    try {
      cipher = createCipher(false);
    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidParameterSpecException | InvalidKeySpecException | InvalidKeyException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    try {
      final byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encodedText));
      return new String(bytes, "UTF-8");
    } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private Cipher createCipher(boolean encryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException {
    final int mode = encryptMode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

    final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(mode, getSecretKeySpec(), getIvParameterSpec());

    return cipher;
  }

  private SecretKeySpec getSecretKeySpec() throws UnsupportedEncodingException {
    final String key = properties.getProperty("key");
    return new SecretKeySpec(Base64.getDecoder().decode(key.getBytes("UTF-8")), "AES");
  }

  private IvParameterSpec getIvParameterSpec() throws UnsupportedEncodingException {
    final String iv = properties.getProperty("iv");
    return new IvParameterSpec(Base64.getDecoder().decode(iv.getBytes("UTF-8")));
  }
}