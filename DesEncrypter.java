//package edu.binghamton.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

class DesEncrypter {
  Cipher ecipher;
  Cipher dcipher;

  DesEncrypter(SecretKey key) throws Exception {
    ecipher = Cipher.getInstance("DES");
    dcipher = Cipher.getInstance("DES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }

  public byte[] encryptCounter(String str) throws Exception {
    byte[] utf8 = str.getBytes("UTF8");
    byte[] enc = ecipher.doFinal(utf8);
    return enc;
  }

  // public byte[] decryptCounter(String str) throws Exception {
  //   byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
  //   byte[] utf8 = dcipher.doFinal(dec);
  //   return dec;
  // }
}