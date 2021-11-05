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
import javax.servlet.http.HttpSession;
import models.Role;
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
        String editEmail = "";

        int userTypeInt = 0;

        UserService userService = new UserService();

        if (myAction != null) {
            switch (myAction) {
                case "add_save": {
                    // retrieves data from request page
                    String email = request.getParameter("add_email");
                    String firstName = request.getParameter("add_first_name");
                    String lastName = request.getParameter("add_last_name");
                    String password = request.getParameter("add_password");
                    String userType = request.getParameter("add_user_type");
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
                    // checks if input is valid
                    Boolean valid = false;
                    String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.matches()) {
                        valid = true;
                    }
                    regex = "^[a-zA-Z0-9._-]{2,}$";
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
                            userService.insert(email, true, firstName, lastName, password, new Role(userTypeInt));
                        } catch (Exception ex) {
                            java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        // fills in the fields with the information provided if there was an error
                        request.setAttribute("add_email", email);
                        request.setAttribute("add_first_name", firstName);
                        request.setAttribute("add_last_name", lastName);
                        request.setAttribute("add_password", password);
                        request.setAttribute("add_user_type", userTypeInt);
                        request.setAttribute("addMessage", "Invalid Entry. Email must be valid, names must be at least two characters and contain valid characters. Password must contain valid characters.");
                    }
                }
                break;
                case "edit_user": {
                    try {
                        HttpSession session = request.getSession();
                        
                        request.setAttribute("edit_clicked", true);
                        editEmail = request.getParameter("user_email");
                        session.setAttribute("userToEdit", editEmail);
                        
                        // get the user based on the email
                        User editUser = userService.get(editEmail);

                        // populate the fields with the current values
                        request.setAttribute("edit_email", editEmail);
                        request.setAttribute("edit_active", editUser.getActive());
                        request.setAttribute("edit_first_name", editUser.getFirstName());
                        request.setAttribute("edit_last_name", editUser.getLastName());
                        request.setAttribute("edit_password", editUser.getPassword());
                        request.setAttribute("edit_user_type", editUser.getRole().getRoleId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "edit_save": {
                    try {
                        // uses a session to retain fields if there is an error
                        HttpSession session = request.getSession();
                        // get the fields in the edit window

                        editEmail = request.getParameter("edit_email");
                        String editFirstName = request.getParameter("edit_first_name");
                        String editLastName = request.getParameter("edit_last_name");
                        String editActive = request.getParameter("status");
                        boolean active = false;
                        if (editActive.equals("active")) {
                            active = true;
                        }
                        String editPassword = request.getParameter("edit_password");
                        String editUserType = request.getParameter("edit_user_type");
                        
                        // assigns an int corresponding to the relevant role
                        int editRole = 0;
                        switch (editUserType) {
                            case "sys_admin":
                                editRole = 1;
                                break;
                            case "reg_user":
                                editRole = 2;
                                break;
                            case "comp_admin":
                                editRole = 3;
                                break;
                        }
                        
                        // checks if input is valid
                        Boolean valid = true;
                        
                        String regex = "^[a-zA-Z0-9._-]{2,}$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(editFirstName);
                        if (!matcher.matches()) {
                            valid = false;
                        }
                        matcher = pattern.matcher(editLastName);
                        if (!matcher.matches()) {
                            valid = false;
                        }
                        regex = "^[a-zA-Z0-9!@#$%^&*+._-]{1,}$";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(editPassword);
                        if (!matcher.matches()) {
                            valid = false;
                        }
                        if (valid) {
                            try {
                                session.invalidate();
                                userService.update(editEmail, active, editFirstName, editLastName, editPassword, new Role(editRole));
                            } catch (Exception ex) {
                                java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        else {
                            // fills in the fields with the information provided if there was an error
                            request.setAttribute("edit_clicked", true);
                            editEmail = (String)session.getAttribute("userToEdit");
                        
                            User editUser = userService.get(editEmail);

                            // populate the fields with the current values
                            request.setAttribute("edit_email", editEmail);
                            request.setAttribute("edit_active", editUser.getActive());
                            request.setAttribute("edit_first_name", editUser.getFirstName());
                            request.setAttribute("edit_last_name", editUser.getLastName());
                            request.setAttribute("edit_password", editUser.getPassword());
                            request.setAttribute("edit_user_type", editUser.getRole());
                            request.setAttribute("editMessage", "Invalid Entry. Names must be at least two characters, contain valid characters and password must contain valid characters.");
                        }
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "delete_user": {
                    try {
                        String deleteEmail = request.getParameter("user_email");
                        userService.delete(deleteEmail);
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
        }
        List<User> usersList = null;
        try {
            usersList = userService.getAll();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", usersList);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

        return;
    }
}
