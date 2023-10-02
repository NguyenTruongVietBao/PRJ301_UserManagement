package sample.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.user.UserDAO;
import sample.user.UserDTO;

public class LoginController extends HttpServlet {
    
    private static final String ERROR = "login.jsp";
    
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String USER_PAGE = "user.jsp";
    
    private static final String US = "US";
    private static final String AD = "AD";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        try {
            // get value of paremeters sent by MainController
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");                     
            // get a valid account
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);   //loginUser('admin','Toi la admin','AD','');     password="";
            // xac thuc o day
            if (loginUser != null) {
                String roleID = loginUser.getRoleID();
                // create a session
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", loginUser);                           
                // phan quyen o day
                if (US.equals(roleID)) {
                    url = USER_PAGE;
                } else if (AD.equals(roleID)) {
                    url = ADMIN_PAGE;
                } else {
                    request.setAttribute("ERROR", "Your role is not support");
                }
            } else {
                request.setAttribute("ERROR", "Incorrect userID or password");
            }
        } catch (Exception e) {
            log("Error at LoginCOntroller: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward   (request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
