<%--
    Document   : index
    Created on : 23 Dec, 2015, 10:22:36 AM
    Author     : Ram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>
 </title>
 <style type="text/css">
 #btnTweet {

 width: 159px;
 height: 38px;
 background-color:#4099FF;
 font-family: cursive;
 border: ghostwhite;
 }
 .box
 {

     background-color: cyan;
 }
 .b
 {
   font-size:20 px;
   text-align: center;
 }
 </style>
</head>
<body>
     <h1 align="center" style=" font-family: monospace" >Tweet content filtering . Tweet from safe mode </h1>
 <form method="post" action="main.jsp">
     <input type="text" size="140" class="box" name="twee" style=" font-size: x-large"/>
 <br/>
 <br/>
 <div class="b">
 <input type="submit" value="Tweet" id="btnTweet"/>
 </div>
 </form>
 </body>
</html>
