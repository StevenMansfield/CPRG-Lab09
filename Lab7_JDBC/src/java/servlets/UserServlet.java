package servlets;

import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        
        try {
            HTTPSession session = request.getSession();
            String email = (String) session.getAttribute("add_email");
            List<User> users = us.getAll(email);
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        try {
            HTTPSession session = request.getSession();
            String firstName = (String) session.getAttribute("add_first_name");
            List<User> users = us.getAll(firstName);
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        try {
            HTTPSession session = request.getSession();
            String lastName = (String) session.getAttribute("add_last_name");
            List<User> users = us.getAll(lastName);
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            try {
                // need to double check the user id part here based on the completed JSP
                int id = Integer.parseInt(request.getParameter("userId"));
                User user = us.get(id);
                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
