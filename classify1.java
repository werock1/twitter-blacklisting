package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import static java.awt.SystemColor.text;
import java.util.List;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Happy
 */
public class classify1 {
    public static void main(String args[])
    {
        String text="hi dude";
          Properties props = new Properties();
	props.setProperty("annotators", "tokenize,ssplit,pos,parse,sentiment");
	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	edu.stanford.nlp.pipeline.Annotation annotation = pipeline.process(text);
	List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	for (CoreMap sentence : sentences) {
	  String sentiment = sentence.get(SentimentCoreAnnotations.ClassName.class);
              System.out.println(sentiment + "\t" + sentence);
              //String result = sentiment;
             
    }
    }
}

