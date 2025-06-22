<%-- 
    Document   : welcome
    Created on : Jun 21, 2025, 7:03:02 AM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                min-height: 100vh;
                align-items: flex-start;
            }

            /* Container chính giữa */
            .container {
                background-color: #fff;
                margin-top: 40px;
                padding: 30px 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                width: 100%;
                max-width: 1000px;
            }

            /* Tiêu đề & liên kết */
            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 25px;
            }

            a {
                color: #007bff;
                text-decoration: none;
                margin-right: 15px;
            }

            a:hover {
                text-decoration: underline;
            }

            /* Form tìm kiếm */
            form {
                margin: 20px 0;
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }

            input[type="text"],
            input[type="submit"],
            select {
                padding: 8px 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                font-size: 14px;
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

            /* Bảng hiển thị */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #fff;
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

            /* Dropdown */
            select {
                min-width: 120px;
            }

            /* Thông báo */
            span,
            .message {
                display: block;
                margin-top: 15px;
                text-align: center;
                font-weight: bold;
            }

            .message {
                color: green;
            }
            /* Căn giữa header Welcome và Logout */
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
                margin-bottom: 20px;
            }

            .header h1 {
                font-size: 24px;
                color: #333;
                margin: 0;
            }

            .header a.logout {
                background-color: #dc3545;
                color: #fff;
                padding: 8px 16px;
                border-radius: 5px;
                font-weight: bold;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .header a.logout:hover {
                background-color: #c82333;
            }

            /* Khu vực Founder - tìm kiếm + thêm dự án */
            .founder-actions {
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 15px;
                margin: 20px 0;
                flex-wrap: wrap;
            }

            .founder-actions form {
                flex-grow: 1;
                display: flex;
                gap: 10px;
            }

            .founder-actions input[type="text"] {
                flex-grow: 1;
                min-width: 200px;
            }

            .founder-actions a.add-project {
                background-color: #28a745;
                color: white;
                padding: 8px 16px;
                border-radius: 5px;
                font-weight: bold;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .founder-actions a.add-project:hover {
                background-color: #218838;
            }

            /* Căn giữa nội dung cho trang chưa đăng nhập */
            p {
                text-align: center;
                margin-top: 40px;
                font-size: 18px;
            }

            p a {
                color: #007bff;
                font-weight: bold;
            }
            
            .error {
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <c:choose >
                <c:when test="${not empty sessionScope.user}">
                    <div class="header">
                        <h1>Welcome ${sessionScope.user.name}</h1>
                        <a class="logout" href="MainController?action=logout">Logout</a>
                    </div>

                    <c:if test="${sessionScope.user.role eq 'Founder'}">
                        <div class="founder-actions">
                            <form action="MainController" method="get">
                                <input type="hidden" name="action" value="search">
                                <input type="text" name="txtSearch" placeholder="Enter name of project..."
                                       value="${searchName != null ? searchName : ''}"/>
                                <input type="submit" value="Search"/>
                            </form>
                            <a class="add-project" href="MainController?action=addProject">Add Project</a>
                        </div>
                    </c:if>

                    <c:if test="${not empty requestScope.accessDenied}">
                        <span class="error">${requestScope.accessDenied}</span>
                    </c:if>

                    <c:if test="${(not empty requestScope.listProjects and fn:length(listProjects) == 0) or not empty message}">
                        <span class="error">${message}</span>
                    </c:if>

                    <c:if test="${not empty requestScope.listProjects and fn:length(listProjects) > 0}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Project ID</th>
                                    <th>Project Name</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <th>Estimated-Launch</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="project" items="${requestScope.listProjects}">
                                    <tr>
                                        <td>${project.project_id}</td>
                                        <td>${project.project_name}</td>
                                        <td>${project.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sessionScope.user.role eq 'Founder'}">
                                                    <form action="MainController" method="POST">
                                                        <input type="hidden" name="action" value="updateStatus"/>
                                                        <input type="hidden" name="projectID" value="${project.project_id}"/>
                                                        <select name="newStatus" onchange="this.form.submit()">
                                                            <c:forEach var="s" items="${requestScope.listStatus}">
                                                                <option value="${s}" ${s==project.status? 'selected':''}>${s}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    ${project.status}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${project.estimated_launch}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table> 
                    </c:if>
                </c:when>
                <c:otherwise>
                    <p>You do not have permission to access. Please login to access!!!
                        <a href="MainController">Login</a>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
