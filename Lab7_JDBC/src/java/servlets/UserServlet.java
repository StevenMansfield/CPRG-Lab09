package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        String myAction = request.getParameter("action");
        String email = request.getParameter("add_email");
        String firstName = request.getParameter("add_first_name");
        String lastName = request.getParameter("add_last_name");
        String password = request.getParameter("add_password");
        String userType = request.getParameter("add_user_type");
        int userTypeInt = 0;

        switch (userType) {
            case "sys_admin":
                userTypeInt = 1;
                break;
            case "reg_user":
                userTypeInt = 2;
                break;
            case "comp_admin":
                userTypeInt = 3;
                break;
        }

        UserService userService = new UserService();

        if (myAction != null) {
            switch (myAction) {
                case "add_save": {
                    Boolean valid = false;
                    String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.matches()) {
                        valid = true;
                    }
                    regex = "^[a-zA-Z0-9._-]{3,}$";
                    pattern = Pattern.compile(regex);
                    matcher = pattern.matcher(firstName);
                    if (!matcher.matches()) {
                        valid = false;
                    }
                    matcher = pattern.matcher(lastName);
                    if (!matcher.matches()) {
                        valid = false;
                    }

                    if (valid) {
                        try {
                            userService.insert(email, true, firstName, lastName, password, userTypeInt);
                            request.setAttribute("message", "added user " + email + " successfully");
                        } catch (Exception ex) {
                            request.setAttribute("message", "user " + email + " not successfully added. DB error");
                            java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        request.setAttribute("message", "user " + email + " not successfully added. Input not valid");
                    }
                }
                break;

                case "edit_save":

                    break;
            }
        }

        getServletContext()
                .getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

        return;
    }
}
