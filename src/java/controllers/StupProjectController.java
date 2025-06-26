/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.StartupProjectDAO;
import models.StartupProjectDTO;
import utils.*;

/**
 *
 * @author Admin
 */
@WebServlet(name = "StupProjectController", urlPatterns = {"/StupProjectController"})
public class StupProjectController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String PROJECT_PAGE = "projectForm.jsp";
    StartupProjectDAO spdao = new StartupProjectDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = WELCOME_PAGE;
        String action = request.getParameter("action");
        try {
            if (action.equals("search")) {
                url = handleProjectSearching(request, response);
            } else if (action.equals("addProject")) {
                url = handleProjectAdding(request, response);
            } else if (action.equals("createProject")) {
                url = handleProjectCreating(request, response);
            } else if (action.equals("updateStatus")) {
                url = handleProjectStatusUpdating(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleProjectSearching(HttpServletRequest request, HttpServletResponse response) {
        if (CommonUtils.isFounder(request)) {
            String projectName = request.getParameter("txtSearch");
            List<StartupProjectDTO> listProjects = spdao.getProjectsByName(projectName);
            if (listProjects != null && !listProjects.isEmpty()) {
                request.setAttribute("listProjects", listProjects);
                request.setAttribute("searchName", projectName);
                CommonUtils.pushListStatusProject(request);
            } else {
                request.setAttribute("message", "No projects have this name.");
            }
        } else {
            CommonUtils.getAccessDeniedMessage(request, "You do not have permission to use this feature.");
        }
        return WELCOME_PAGE;
    }
    //?action=search&txtSearch=Health

    private String handleProjectAdding(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtils.isFounder(request)) {
            CommonUtils.getAccessDeniedMessage(request, "You do not have permission to use this feature.");
            return WELCOME_PAGE;
        }
        CommonUtils.pushListStatusProject(request);
        return PROJECT_PAGE;
    }

    private String handleProjectCreating(HttpServletRequest request, HttpServletResponse response) {
        try {
            String projectName = request.getParameter("project_name");
            String description = request.getParameter("description");   
            String status = request.getParameter("status");
            String estimated_launch = request.getParameter("estimated_launch");

            if (projectName == null || projectName.isEmpty()
                    || status == null || status.isEmpty() || estimated_launch == null || estimated_launch.isEmpty()) {
                request.setAttribute("errorMsg", "please fill all of information");
                keepFormData(request, projectName, description, status, estimated_launch);
                CommonUtils.pushListStatusProject(request);
                return PROJECT_PAGE;
            }
            Date estimated_launch_date = new SimpleDateFormat("yyyy-MM-dd").parse(estimated_launch);
            Date today = new Date();
            if (estimated_launch_date.before(today)) {
                request.setAttribute("errorMsg", "Estimated launch date must be in the future.");
                CommonUtils.pushListStatusProject(request);
                keepFormData(request, projectName, description, status, estimated_launch);
                return PROJECT_PAGE;
            }
            StartupProjectDTO newProject = new StartupProjectDTO(projectName, description, status, estimated_launch_date);
            boolean created = spdao.addProject(newProject);
            if (created) {
                request.setAttribute("message", "New project created successfully.");
                CommonUtils.prepareProjectPageData(request);
                return WELCOME_PAGE;
            } else {
                CommonUtils.pushListStatusProject(request);
                keepFormData(request, projectName, description, status, estimated_launch);
                request.setAttribute("errorMsg", "Failed to create new project.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PROJECT_PAGE;
    }

    private String handleProjectStatusUpdating(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtils.isFounder(request)) {
            CommonUtils.getAccessDeniedMessage(request, "You don't have permission to update project status.");
            CommonUtils.prepareProjectPageData(request);
            return WELCOME_PAGE;
        }
        try {
            String projectID = request.getParameter("projectID");
            String status = request.getParameter("newStatus");
            int projectID_value = Integer.parseInt(projectID);
            boolean updated = spdao.updateStatus(projectID_value, status);
            if (updated) {
                request.setAttribute("message", "Update successfully");
            } else {
                request.setAttribute("message", "Update unsuccessfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonUtils.prepareProjectPageData(request);
        return WELCOME_PAGE;
    }

    private void keepFormData(HttpServletRequest req,
            String name, String desc,
            String stt, String launch) {
        req.setAttribute("projectName", name);
        req.setAttribute("description", desc);
        req.setAttribute("statusSelected", stt);
        req.setAttribute("estimatedLaunch", launch);
    }
}
