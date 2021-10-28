package servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        String editEmail = "";
        
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

        List<User> users = null;
        try {
            users = userService.getAll();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);

        if (myAction != null) {
            switch (myAction) {
                case "add_save": {
                    if (email == null || email.length() != 0) {
                        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(email);
                        if (matcher.matches()) {
                            try {
                                userService.insert(email, true, firstName, lastName, password, userTypeInt);
                                request.setAttribute("message", "added user " + email + " successfully");
                            } catch (Exception ex) {
                                request.setAttribute("message", "user " + email + " not successfully added. DB error");
                                java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            request.setAttribute("message", "user " + email + " not successfully added. Check your email");
                        }
                    }

                }
                break;
                case "edit_user":
                    try {
                        editEmail = request.getParameter("user_email");
                        // get the user based on the email
                        User editUser = userService.get(editEmail);
                        
                        // populate the fields the current values
                        request.setAttribute("edit_email", editEmail);
                        request.setAttribute("edit_active", editUser.getActive());
                        request.setAttribute("edit_first_name", editUser.getFirst_name());
                        request.setAttribute("edit_last_name", editUser.getLast_name());
                        request.setAttribute("edit_password", editUser.getPassword());
                        request.setAttribute("edit_user_type", editUser.getRole());
                    } catch (Exception ex) {
                        
                    }
                    break;
                case "save_edits":
                        try {
                            // get the fields in the edit window
                            
                            editEmail = request.getParameter("edit_email");
                            String editFirstName = request.getParameter("edit_first_name");
                            String editLastName = request.getParameter("edit_last_name");
                            boolean editActive = request.getParameter("edit_active");
                            String editPassword = request.getParameter("edit_password");
                            String editUserType = request.getParameter("edit_user_type");
                            // assigns an int corresponding to the relevant role
                            int editRole = 0;
                                switch (editUserType) {
                                    case "sys_admin" :
                                        editRole = 1;
                                    break;
                                    case "reg_user" :
                                        editRole = 2;
                                    break;
                                    case "comp_admin" :
                                        editRole = 3;
                                    break;
                                }
                                // updates the database with the new info
                            userService.update(editEmail, editActive, editFirstName, editLastName, editPassword, editRole);
                        } catch (Exception ex){
                            
                        }
                    break;
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }
}
