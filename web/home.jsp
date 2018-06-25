<%-- 
    Document   : home
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
        <script>
            function myFunc1() {
                var t = document.getElementById("tableOfLeague").value;
                t--;
                if (t < 1)
                    t = 5;
                switch (t) {
                    case 1:
            <c:set var="xmldoc" value="${sessionScope.xmlEng}"/>;
                        break;
                    case 2:
            <c:set var="xmldoc" value="${sessionScope.xmlIta}"/>;
                        break;
                    case 3:
            <c:set var="xmldoc" value="${sessionScope.xmlSpa}"/>;
                        break;
                    case 4:
            <c:set var="xmldoc" value="${sessionScope.xmlGer}"/>;
                        break;
                    case 5:
            <c:set var="xmldoc" value="${sessionScope.xmlFra}"/>;
                        break;
                }
            };
            function myFunc2() {
                var t = document.getElementById("tableOfLeague").value;
                t++;
                if (t > 5)
                    t = 1;
                switch (t) {
                    case 1:
            <c:set var="xmldoc" value="${sessionScope.xmlEng}"/>;
                        break;
                    case 2:
            <c:set var="xmldoc" value="${sessionScope.xmlIta}"/>;
                        break;
                    case 3:
            <c:set var="xmldoc" value="${sessionScope.xmlSpa}"/>;
                        break;
                    case 4:
            <c:set var="xmldoc" value="${sessionScope.xmlGer}"/>;
                        break;
                    case 5:
            <c:set var="xmldoc" value="${sessionScope.xmlFra}"/>;
                        break;
                }
            };
        </script>

        <c:set var="listSpain" value="${sessionScope.listSpain}"></c:set>
        <c:set var="listClub" value="${sessionScope.listClub}"></c:set>

        <c:import url="WEB-INF/xsl/teamRankings.xsl" charEncoding="UTF-8" var="xsldoc"/>
        <c:set var="xmldoc" value="${sessionScope.xmlSpa}"/>

        <c:import url="WEB-INF/xsl/topPlayers.xsl" charEncoding="UTF-8" var="xslTopDoc"/>
        <c:set var="xmlTopDoc" value="${sessionScope.xmlTop}"/>

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
                    <div class="h2"> Top 10 players with highest ratings</div>
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
                <div class="col-lg-4">
                    <div class="h3" style="text-align: center">Spain</div>
                    <input type="hidden" id="tableOfLeague" value="3" />
                    <div class="btn btn-info" id="pre" onclick="myFunc1();">Pre</div>
                    <div class="btn btn-info pull-right" id="next" onclick="myFunc2()">Next</div>
                    <table class="table" style="position: absolute;">
                        <thead class="top">
                            <tr>
                                <th class="col-md-4">Team</th>
                                <th class="col-md-2">P</th>
                                <th class="col-md-1">W</th>
                                <th class="col-md-1">D</th>
                                <th class="col-md-1">L</th>
                                <th class="col-md-2">PTS</th>
                            </tr>
                        </thead>
                        <tbody>
                            <x:transform xml="${xmldoc}" xslt="${xsldoc}"/>
                        </tbody>
                    </table>
                </div>
            </div>  
        </div>
    </body>
</html>
