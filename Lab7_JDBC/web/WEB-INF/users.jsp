<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./sources/css/users.css"></link>
        <title>User Management</title>
    </head>
    <body>
        <form method="POST" action="add_user" id="add_form">            
            <h2 class="add_title">Add User</h2>
            <div class="add_first_input">
                <label>First Name</label>
                <input id="email" type="text" name="add_email" placeholder=" ">
            </div>
            <div>
                <label>First Name</label>
                <input type="text" name="add_first_name">
            </div>
            <div>
                <label>Last Name</label>
                <input type="text" name="add_last_name">
            </div>
            <div>
                <label>Password</label>
                <input type="text" name="add_password">
            </div>
            <div>
                <select name="user_type">
                    <option value="sys_admin">System Admin</option>
                    <option value="reg_user">Regular User</option>
                    <option value="comp_admin">Company Admin</option>
                </select> 
            </div>
            <div>
            <input type="submit" value="Save">
            <input type="hidden" name="action" value="save">
            </div>
        </form>
        
        <h2 class="manage_title">Manage Users</h2>
        
        <form method="POST" action="add_user" id="edit_form">
            <h2 class="edit_title">Edit User</h2>
            <div>
                <label>Email</label>
                <input type="text" name="add_email">
            </div>
            <div>
                <label>First Name</label>
                <input type="text" name="add_first_name">
            </div>
            <div>
                <label>Last Name</label>
                <input type="text" name="add_last_name">
            </div>
            <div>
                <select name="user_type">
                    <option value="sys_admin">System Admin</option>
                    <option value="reg_user">Regular User</option>
                    <option value="comp_admin">Company Admin</option>
                </select> 
            </div>
            <div>
            <input type="submit" value="Save">
            <input type="hidden" name="action" value="save">
            </div>
        </form>
    </body>
</html>

<!--            <h2 class="add_title">Add User</h2>
            <div class="entry_container first_input">
                <input id="email" class="input" type="text" name="add_email" placeholder=" ">
                <div class="cut"></div>
                <label for="email" class="placeholder">Email</label>
            </div>-->