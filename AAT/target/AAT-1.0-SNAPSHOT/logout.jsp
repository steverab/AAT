<%
    Cookie authCookie = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++){
            if (cookies[i].getName().equals("authenticate")) {
                authCookie = cookies[i];
            }
        }
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
    }
    response.sendRedirect("/index.jsp");
%>