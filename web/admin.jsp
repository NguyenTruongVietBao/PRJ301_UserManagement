

<%@page import="java.util.List"%>
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

            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }

        %>
        <h1>Day la trang admin</h1>
        <h1>Welcome: <%= loginUser.getFullName()%></h1>


        <form action="MainController" method="POST">
            Search<input type="text" name="search" value="<%= search%>" >
            <input type="submit"  name="action" value="Search">
        </form>


        <%
            List<UserDTO> listUser = (List<UserDTO>) request.getAttribute("LIST_USER");

            if (listUser != null) {
                if (listUser.size() > 0) {

        %>


        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Role ID</th>
                    <th>Password</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%                    int count = 1;
                    for (UserDTO user : listUser) {


                %>


                <tr>
                    <td><%= count++%></td>
                    <td><%= user.getUserID()%></td>
                    <td><%= user.getFullName()%></td>
                    <td><%= user.getRoleID()%></td>

                    <td><%= user.getPassword()%></td>
                    <td>
                        <!--<a href="MainController?action=Delete&userID=<%=user.getUserID()%>&search=<%= search%>">Delete</a>-->
                        <a href="#" onclick="doDelete('<%= user.getUserID() %>')">Delete</a>

                    </td>
                </tr>
                <%

                    }

                %>
            </tbody>
        </table>
        <%    
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%= error%>

        <%        }

            }
        %>
        <script>
            function doDelete(id){
                if(confirm("are you sure to delete '"+id+"' ?")){
                    window.location = "MainController?action=Delete&userID="+id+"&search=<%= search%>";
                }
            }
        </script>
    </body>

</html>






