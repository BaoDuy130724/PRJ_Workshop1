<%-- 
    Document   : projectForm
    Created on : Jun 21, 2025, 11:21:40 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f2f4f8;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                min-height: 100vh;
            }

            .container {
                background-color: #fff;
                margin-top: 40px;
                padding: 30px 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                width: 100%;
                max-width: 600px;
            }

            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 25px;
            }

            form label {
                display: block;
                margin-top: 15px;
                font-weight: bold;
                color: #333;
            }

            form input[type="text"],
            form input[type="date"],
            form select,
            form textarea {
                width: 100%;
                padding: 10px 12px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                box-sizing: border-box;
            }

            form textarea {
                resize: vertical;
                min-height: 100px;
            }

            form input[type="submit"] {
                margin-top: 20px;
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: white;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            form input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .error {
                color: red;
                text-align: center;
                margin-top: 15px;
                font-weight: bold;
            }
            .back-container {
                display: flex;
                justify-content: center;
                margin-top: 25px;
            }

            .back-link {
                display: inline-block;
                text-decoration: none;
                padding: 10px 20px;
                background-color: #e0e0e0;
                color: #333;
                border-radius: 5px;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            .back-link:hover {
                background-color: #c0c0c0;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'Founder'}">
                    <h1>Project Information</h1>
                    <form action="StupProjectController" method="POST">
                        <input type="hidden" name="action" value="createProject"/>

                        <label for="project_name">Project Name:</label>
                        <input type="text" name="project_name" id="project_name"
                               required="required" value="${requestScope.projectName!=null?requestScope.projectName : ''}"/>

                        <label for="description">Description:</label>
                        <textarea id="description" name="description">${requestScope.description}</textarea>

                        <label for="status">Status:</label>
                        <c:if test="${not empty requestScope.listStatus}">
                            <select id="status" name="status" required="required">
                                <c:forEach var="s" items="${requestScope.listStatus}">
                                    <option value="${s}" ${s eq requestScope.statusSelected ? 'selected':''}>${s}</option>
                                </c:forEach>
                            </select>
                        </c:if>

                        <label for="estimated_launch">Estimated Launch:</label>
                        <input type="date" id="estimated_launch" name="estimated_launch" required="required"/>

                        <input type="submit" value="Create"/>
                    </form>
                    <div class="back-container">
                        <a class="back-link" href="MainController?action=back">← Back to Home</a>
                    </div>
                    <c:if test="${not empty requestScope.errorMsg}">
                        <div class="error">${requestScope.errorMsg}</div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <div class="error">${requestScope.accessDenied}!!!</div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
