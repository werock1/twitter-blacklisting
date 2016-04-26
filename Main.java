/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tweetss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ram
 */
public class Main {
    public static void main(String args[]) throws Exception
    {

         System.out.println("Enter a sentence:");
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      String s=br.readLine();
      analyse a=new analyse();
      ds d=new ds();
      int flag=a.analysis(s);
      //classify cl=new classify();

      byte[] signature=a.sign();
     // byte[] sign1=a.sign(new ds());
      boolean check= d.verify(signature,s,a);
      System.out.println("THE TWEET IS \n"+s+"\n");
      if(check && flag==1)
      {
          System.out.println("The signature is from an authorized analyser.The tweet is negative\n");
      }
      else if(check && flag==0)
      {

          System.out.println("The signature is from an authorized analyser.The tweet may or may not be negative\n");
          System.out.println("Giving the tweet to a sentimental analyser");
          String sentiment=classify.sentiment_analyse(s);
          System.out.println(sentiment+ " : "+s);
      }
      else
      {
          System.out.println("The signature is not from an authorized analyser.\n");
      }
    }
}
