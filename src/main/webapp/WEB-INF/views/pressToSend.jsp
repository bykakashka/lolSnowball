<%--
  Created by IntelliJ IDEA.
  User: byka
  Date: 20.12.18
  Time: 23.40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Press to throw</title>
</head>
<script>
    function throwBalls(ids) {
        if (ids !== undefined) {
            for (var id in ids) {
                var http = new XMLHttpRequest();
                var url = "https://ny.ru.leagueoflegends.com/api/snowballs/history/";
                var params = "{\"summoner_to\":" + ids[id] + "}";

                http.open("POST", url, true);
                http.setRequestHeader('Content-type', 'application/json;charset=utf-8');

                http.send(params);
            }
        }
    }
</script>
<body>
<input type="hidden" value="${ids}" id="list">
<input type="button" onclick="throwBalls(${ids})" value="press to throw">
</body>
</html>
