<%-- 
    Document   : login
    Created on : Jun 21, 2025, 6:26:20 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                min-height: 100vh;
                align-items: center;
            }

            .container {
                background-color: #fff;
                padding: 30px 40px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                width: 100%;
                max-width: 800px;
            }

            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 25px;
            }

            a {
                color: #007bff;
                text-decoration: none;
                margin-right: 10px;
            }

            a:hover {
                text-decoration: underline;
            }

            form {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="password"],
            input[type="submit"],
            select,
            textarea {
                width: 100%;
                padding: 10px 12px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: white;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                margin-top: 20px;
                box-shadow: 0 0 5px rgba(0,0,0,0.1);
            }

            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: center;
            }

            th {
                background-color: #007bff;
                color: white;
            }

            select {
                min-width: 120px;
            }

            span {
                display: block;
                margin-top: 10px;
            }

            .message {
                color: green;
                font-weight: bold;
                text-align: center;
            }

            .error {
                color: red;
                font-weight: bold;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Login Form</h1>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <c:redirect url="welcome.jsp"/>
                </c:when>
                <c:otherwise>
                    <form action="MainController" method="POST">
                        <input type="hidden" name="action" value="login">

                        <label for="username">User Name</label>
                        <input type="text" name="username" id="username" placeholder="User name...." required="required" /><br/>

                        <label for="password">Password</label>
                        <input type="password" name="password" id="password" placeholder="password...." required="required"/><br/>

                        <input type="submit" value="Login"/>
                    </form>
                    <c:if test="${not empty message}">
                        <span style="color: red">${message}</span>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
