/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tweetss;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Ram
 */
public class ds {
   KeyPairGenerator kpg ;
   KeyPair keyPair;
   Signature sig;
  public ds()throws Exception
  {
      sig = Signature.getInstance("MD5WithRSA");
   kpg= KeyPairGenerator.getInstance("RSA");
   kpg.initialize(1024);
   keyPair = kpg.genKeyPair();
  }
  public PublicKey getPublicKey()
  {
      return keyPair.getPublic();
  }
  public  boolean verify(byte[] signature,String tweet,analyse a) throws Exception
  {

    sig.initVerify(a.getPubkey());
    byte[] data=tweet.getBytes("UTF8");
    sig.update(data);

    return sig.verify(signature);
  }
  public  byte[] signature(String tweet) throws Exception {


    byte[] data = tweet.getBytes("UTF8");


    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
   // System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));
    return signatureBytes;

  }
}
