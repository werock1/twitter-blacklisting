
<%--
    Document   : main
    Created on : 23 Dec, 2015, 11:58:50 AM
    Author     : Ram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="tweetss.analyse,tweetss.classify,tweetss.ds " %>
<%

    String s=request.getParameter("twee");
    if(s.isEmpty())
    {
        response.sendRedirect("index.jsp");

    %>

    <%}
    else
    {
     analyse a=new analyse();
      ds d=new ds();
      int flag=a.analysis(s);
      //classify cl=new classify();

      byte[] signature=a.sign();
     // byte[] sign1=a.sign(new ds());
      boolean check= d.verify(signature,s,a);
      System.out.println("\nTHE TWEET IS \n"+s+"\n");
      if(check && flag==1)
      {
          System.out.println("The signature is from an authorized analyser.The tweet is negative\n");
          //response.getWriter().write("The signature is from an authorized analyser.\nThe tweet is NEGATIVE\nMay have vulgar content\nDo you still want to post");
       %>
       <script style="font-size: x-large">
           alert("<%=s%>"+" is NEGATIVE");
       </script>
     <%} else if(check && flag==0)
           {

          System.out.println("The signature is from an authorized analyser.The tweet may or may not be negative\n");
          System.out.println("\nGiving the tweet to a sentimental analyser");
          String sentiment=classify.sentiment_analyse(s);
          System.out.println(sentiment+ " : "+s);
          //response.getWriter().write("The tweet is of "+ sentiment+" sentiment");
          if(sentiment.equalsIgnoreCase("positive") || sentiment.equalsIgnoreCase("very positive"))
          {
       %>
       <script>
           alert("<%=s%>"+" is positive"+" ");
           var textToTweet="<%=s%>";
             if (textToTweet.length > 140) {
 alert('Tweet should be less than 140 Chars');
 }
 else {
 var twtLink = 'http://twitter.com/home?status=' +encodeURIComponent(textToTweet);
 window.open(twtLink,'_blank');
 }
       </script>
       <%}
          else if(sentiment.equalsIgnoreCase("neutral"))
          {
        %>
        <script>
            alert("<%=s%>"+" is neutral.");
            var t=confirm("You will be responsible for the consequences that arises from posting this tweet.\nDo you still want to post?");
            if(t===true)
            {
                var textToTweet="<%=s%>";
             if (textToTweet.length > 140) {
                alert('Tweet should be less than 140 Chars');
                 }
              else {
                   var twtLink = 'http://twitter.com/home?status=' +encodeURIComponent(textToTweet);
                    window.open(twtLink,'_blank');
                    }
            }
            else
            {
             alert("You are not ready to post the tweet");
            }
        </script>
          <%}
          else if(sentiment.equalsIgnoreCase("negative") || sentiment.equalsIgnoreCase("very negative"))
          {%>
          <script>
              alert("<%=s%>"+" is NEGATIVE");
          </script>

         <%}
           else
           {
           System.out.println("The signature is not from an authorized analyser.\n");
           %>
           <script>
               alert("The signature is not from an authorized analyser.\n");
           </script>
           <% //response.getWriter().write("The signature is not from an authorized analyser.Do you still want to post?");
             }
}}

       %>

%>