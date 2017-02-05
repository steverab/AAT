<%@ page import="org.json.JSONObject" %>
<%
    Cookie authCookie = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++){

            if (cookies[i].getName().equals("authenticate")) {
                authCookie = cookies[i];
            }
        }
    }
    JSONObject jsonAuthObject = null;
    String userType = null;
    String email = null;
    Long groupId = null;
    if (authCookie != null) {
        jsonAuthObject = new JSONObject(authCookie.getValue().replaceAll("%22","\"").replaceAll("%2C",","));
        userType = jsonAuthObject.getString("type");
        email = jsonAuthObject.getString("email");
        if (userType.equals("student")) {
            groupId = jsonAuthObject.getLong("groupId");
        }
    }
%>
<html>
<head>
    <title>Automated Attendance Tracker</title>
    <script src="js/jquery-3.1.1.min.js"></script>
    <script src="js/js.cookie.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <link href="style.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="header clearfix">
            <nav>
                <ul class="nav nav-pills float-right">
                    <%
                        if (authCookie != null) {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="/home.jsp"><%= email %></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="logout.jsp">Logout</a>
                    </li>
                    <%
                    } else {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a><!--active-->
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </nav>
            <h3 class="text-muted">Automated Attendance Tracker (AAT)</h3>
        </div>