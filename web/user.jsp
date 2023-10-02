

<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                loginUser = new UserDTO();
            }

        %>



        <h1>Day la trang User</h1>
        UserID:<%= loginUser.getUserID() %><br>
        FullName: <%= loginUser.getFullName() %><br>
        RoleID: <%= loginUser.getRoleID() %>
        
    </body>
</html>
