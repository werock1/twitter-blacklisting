/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tweetss;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import java.io.*;
import java.net.UnknownHostException;
import java.security.PublicKey;
import javax.swing.text.Document;
import static tweetss.PrefixTree.printTree;
/**
 *
 * @author Ram
 */

public class analyse {

    static MongoClient mongoClient;
    static DB db;
    static DBCollection coll;
    static DBCursor cursor,cursor1,cursor2;
    static HashTablesChainingListHeadsTest h=new HashTablesChainingListHeadsTest();
    static PrefixTree t=new PrefixTree();
    static binary b=new binary();
    static String text;
    ds digital=new ds();
    public analyse() throws Exception
    {
                 digital=new ds();
    }
    public PublicKey getPubkey()
    {
        return digital.getPublicKey();
    }
   public byte[] sign() throws Exception
   {
       return digital.signature(text);
   }
     static int input(String tweet)
    {
        int i;
         String delim="[\\s\\.,]+";
         String token[];
        token = tweet.split(delim);
         int m=token.length;
         int flag=0;

        // System.out.println("inside input function");
         //classifying tweet using trie search;
        long then = System.nanoTime();
         for(i=0;i<m;i++)
         {
             if(token[i].equals("."))
                continue;
             if(t.findw(token[i])==1)
                 flag=1;

             if(flag==1)
             {
               //  System.out.println("\nThe tweet is of negative sentiment."+"negtive word:"+token[i]);
                 break;
             }
         }
         /*if(flag==0)
         {
             System.out.println("\nThe tweet may or may not be negative");
         }*/
         long dur = System.nanoTime() - then;
         System.out.println("trie search " + dur + " nanoseconds.");


         //classifying tweet using database search
        flag=0;
        then = System.nanoTime();
        BasicDBObject  query=new BasicDBObject("frequency",new BasicDBObject("$gt",-1));
       for(i=0;i<m;i++)
       {
         cursor = coll.find(query);
       try {
           while(cursor.hasNext()) {
           DBObject o= cursor.next();
           String w=o.get("word").toString();
            if(token[i].equals(w))
             {
                flag=1;
            //    System.out.println("\nThe tweet is of negative sentiment."+"negtive word:"+token[i]);
                break;
             }
            }
           }
        finally
        {cursor.close();}
       if(flag==1)
           break;
       }
        /* if(flag==0)
         {
             System.out.println("\nThe tweet may or may not be negative");
         }*/
         dur = System.nanoTime() - then;
         System.out.println("database search " + dur + " nanoseconds.");


         //classify tweet using hash search
         flag=0;
         then = System.nanoTime();
         for(i=0;i<m;i++)
         {
             if(h.get1(token[i])==1)
             {
                 flag=1;
                 // System.out.println("\nThe tweet is of negative sentiment."+"negtive word:"+token[i]);
                 break;
             }
         }
         /* if(flag==0)
         {
             System.out.println("\nThe tweet may or may not be negative");
         }*/
          dur = System.nanoTime() - then;
          System.out.println("hash search " + dur + " nanoseconds.");


          //classify tweet using binary search tree
          flag=0;
          then = System.nanoTime();
          for(i=0;i<m;i++)
          {
            if(b.find(token[i]))
            {   flag=1;
              // System.out.println("\nThe tweet is of negative sentiment."+"negtive word:"+token[i]);
                 break;
            }
          }
         /* if(flag==0)
         {
             System.out.println("\nThe tweet may or may not be negative");
         }*/

           dur = System.nanoTime() - then;
          System.out.println("binary search tree " + dur + " nanoseconds.");
          if(flag==1)
          {
              System.out.println("\nThe negative word present in the tweet is"+token[i]+"\n");
          }
          return flag;
    }




    public  int analysis(String s)throws UnknownHostException, IOException
    {
         text=s;
         mongoClient = new MongoClient();
         db = mongoClient.getDB("tweetDB");
         coll=db.getCollection("swearwords");
       BasicDBObject  query=new BasicDBObject("frequency",new BasicDBObject("$gt",-1));
      // BasicDBObject fields = new BasicDBObject("word",true).append("_id",false).append("frequency",false);
         cursor = coll.find(query);
         cursor1=coll.find(query);
         cursor2=coll.find(query);
        int n=0;
        //inserting data set of  negative words
       long freeMemory = Runtime.getRuntime().freeMemory();
       long totalMemory = Runtime.getRuntime().totalMemory();
        long used=totalMemory-freeMemory;
       //System.out.println("used memory:"+(totalMemory-freeMemory));

       /*insertion into trie */
   try {
        while(cursor.hasNext()) {
       n++;
       DBObject o= cursor.next();
       //System.out.println(o.get("word"));
      /* System.out.println(o.get("frequency"));
       System.out.println();*/
       String w=o.get("word").toString();
        t.insert(w);
      }
      //  System.out.println("memory used by trie:"+(used1-used));
     }
    finally
      {
   cursor.close();
      }

      /*space requirement for trie*/
       long freeMemory1 = Runtime.getRuntime().freeMemory();
       long totalMemory1 = Runtime.getRuntime().totalMemory();
       long used1=totalMemory1-freeMemory1;
      //System.out.println("MEMORY USED BY TRIE:"+(used1-used));

      /*insertion into hash */
    n=0;
       try {
        while(cursor1.hasNext()) {
       n++;
       DBObject o= cursor1.next();
      /* System.out.println(o.get("word"));
       System.out.println(o.get("frequency"));
       System.out.println();*/
       String w=o.get("word").toString();
      int f=Integer.parseInt(o.get("frequency").toString());
       h.insert(w,f);
     }
      //  System.out.println("memory used by trie:"+(used1-used));
         }
      finally
      {
        cursor1.close();
       }

        /*space requirement for hash*/
       long freeMemory2 = Runtime.getRuntime().freeMemory();
       long totalMemory2 = Runtime.getRuntime().totalMemory();
       long used2=totalMemory2-freeMemory2;

        /*insertion into binary */
       n=0;
        try {
        while(cursor2.hasNext()) {
       n++;
       DBObject o= cursor2.next();
      /* System.out.println(o.get("word"));
       System.out.println(o.get("frequency"));
       System.out.println();*/
       String w=o.get("word").toString();

       b.insert(w);
        }
        }
     finally
      {
      cursor2.close();
       }
         /*space requirement for trie*/
       // int a[];
       long freeMemory3= Runtime.getRuntime().freeMemory();
       long totalMemory3 = Runtime.getRuntime().totalMemory();
       long used3=totalMemory3-freeMemory3;

       System.out.println("\nMEMORY USED BY TRIE:"+(used1-used)+" bytes");
       System.out.println("MEMORY USED BY HASH:"+(used2-used1)+" bytes");
       System.out.println("MEMORY USED BY BINARY SEARCH:"+(used3-used2)+" bytes");


        System.out.println("\nno.of words in the blacklist "+n+"\n");

      //checking if the sentence is of negative sentiment
    // System.out.println(s);
     int i= input(s);
      return i;

    }

}

