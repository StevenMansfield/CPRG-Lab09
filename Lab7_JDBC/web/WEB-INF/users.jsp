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
        
        
        <div id="add_container">
            <form method="POST" action="add_user" id="add_form">            
                <h2 id="add_title">Add User</h2>
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
                    <label>Password</label>
                    <input type="text" name="add_password">
                </div>
                <div>
                    <select name="add_user_type">
                        <option value="sys_admin">System Admin</option>
                        <option value="reg_user">Regular User</option>
                        <option value="comp_admin">Company Admin</option>
                    </select> 
                </div>
                <div>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="add_save">
                </div>
            </form>
        </div>
        
        
        
        
        <div id="manage_container">
            <h2 id="manage_title">Manage Users</h2>
            <div id='head_manage_users' class='header'>
                <span id='head_email'>Email</span>
                <span id='head_first_name'>First Name</span>
                <span id='head_last_name'>Last Name</span>
                <span id='head_role'>Role</span>
                <span id='head_edit'>Edit</span>
                <span id='head_delete'>Delete</span>
            </div>
        </div>
        
        
        
        
        <div id="edit_container">
            <form method="POST" action="add_user" id="edit_form">
                <h2 id="edit_title">Edit User</h2>
                <div>
                    <label>Email</label>
                    <input type="text" name="edit_email" readonly>
                </div>
                <div>
                    <label>First Name</label>
                    <input type="text" name="edit_first_name">
                </div>
                <div>
                    <label>Last Name</label>
                    <input type="text" name="edit_last_name">
                </div>
                <div>
                    <select name="edit_user_type">
                        <option value="sys_admin">System Admin</option>
                        <option value="reg_user">Regular User</option>
                        <option value="comp_admin">Company Admin</option>
                    </select> 
                </div>
                <div>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="edit_save">
                </div>
            </form>
        </div>
        
        
    </body>
</html>