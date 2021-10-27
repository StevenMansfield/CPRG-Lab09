package servlets;

import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        
        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
