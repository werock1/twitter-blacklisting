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
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import static java.awt.SystemColor.text;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import static tweetss.analyse.cursor;

/**
 *
 * @author Ram
 */
public class classify {
    private static String result;
    public static String sentiment_analyse(String text) throws UnknownHostException
    {
       //String text = "what the hell";
       Properties props = new Properties();
	props.setProperty("annotators", "tokenize,ssplit,pos,parse,sentiment");
	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	edu.stanford.nlp.pipeline.Annotation annotation = pipeline.process(text);
	List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	for (CoreMap sentence : sentences) {
	  String sentiment = sentence.get(SentimentCoreAnnotations.ClassName.class);
	  //System.out.println(sentiment + "\t" + sentence);
	  result = sentiment;
	}
        System.out.println("after sentiment");
         MongoClient mongoClient = new MongoClient();
           DB db = mongoClient.getDB("tweetDB");

        if(result.equalsIgnoreCase("negative"))
        {
             String delim="[\\s\\.,]+";
         String token[]=text.split(delim);
         for(int i=0;i<token.length;i++)
         {
             System.out.println(token[i]);
             edu.stanford.nlp.pipeline.Annotation annotation1 = pipeline.process(token[i]);
	List<CoreMap> sentences1 = annotation1.get(CoreAnnotations.SentencesAnnotation.class);
	for (CoreMap sentence1 : sentences1) {
	  String sentiment1 = sentence1.get(SentimentCoreAnnotations.ClassName.class);
	  System.out.println(sentiment1 + "\t" + sentences1);
	 // result = sentiment;
          if(sentiment1.equalsIgnoreCase("negative")||sentiment1.equalsIgnoreCase("very negative"))
          {
              if(!token[i].equalsIgnoreCase("not") && !token[i].equalsIgnoreCase("bad"))
              {
                 DBCollection coll=db.getCollection("swearwords");
                 Random randomGenerator = new Random();
                  int randomInt = randomGenerator.nextInt(500);
                      System.out.println("The negative word found is "+token[i]);
                      BasicDBObject doc = new BasicDBObject("word",token[i].toLowerCase()).append("frequency",randomInt);
                      coll.insert(doc);
              }


          }
	  }
         }
        }
        else if(result.equalsIgnoreCase("neutral"))
        {
             BasicDBObject  query=new BasicDBObject("frequency",new BasicDBObject("$gt",-1));
      // BasicDBObject fields = new BasicDBObject("word",true).append("_id",false).append("frequency",false);
              DBCollection coll=db.getCollection("banned");
             DBCursor cursor;
             cursor =coll.find(query);
             while(cursor.hasNext()) {

                DBObject o= cursor.next();
                //System.out.println(o.get("word"));
                 String w=o.get("word").toString();
                 if(text.contains(w))
                 {
                     System.out.println("The text has vulgar content");
                     result="Negative";
                     break;
                 }
                 }

           }

         return result;
    }
}
