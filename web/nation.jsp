<%-- 
    Document   : nation
    Created on : Nov 1, 2017, 8:22:20 AM
    Author     : haleduykhang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

        <title>Football Statistics | Football Livescore</title>

        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/myStyle.css" rel="stylesheet">
    </head>
    <body>

        <c:import url="WEB-INF/xsl/topPlayers.xsl" charEncoding="UTF-8" var="xslTopDoc"/>
        <c:set var="xmlTopDoc" value="${sessionScope.xmlTopLeague}"/>

        <div class="container">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">WhoScored.com</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="#">Statistics</a></li>
                            <li><a href="ProcessServlet?btAction=nationServlet&nation=Eng">Premier League</a></li>
                            <li><a href="ProcessServlet?btAction=nationServlet&nation=Spa">La Liga</a></li>
                            <li><a href="ProcessServlet?btAction=nationServlet&nation=Ita">Seria A</a></li>
                            <li><a href="ProcessServlet?btAction=nationServlet&nation=Ger">Bundesliga</a></li>
                            <li><a href="ProcessServlet?btAction=nationServlet&nation=Fra">Ligue 1</a></li>
                            <li><a href="#">Comparison</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div><!--/.container-fluid -->
            </nav>
        </div> <!-- /container -->

        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="h2"> Top 20 players with highest ratings</div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Player</th>
                                <th>Team</th>
                                <th>Apps</th>
                                <th>Rating</th>
                            </tr>
                        </thead>
                        <tbody>
                            <x:transform xml="${xmlTopDoc}" xslt="${xslTopDoc}"/>
                        </tbody>  
                    </table>     
                </div>
            </div>  
        </div>
    </body>
</html>
