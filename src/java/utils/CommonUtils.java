/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.StartupProjectDAO;
import models.StartupProjectDTO;
import models.UserDTO;

/**
 *
 * @author Admin
 */
public class CommonUtils {

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        UserDTO user = null;
        HttpSession s = request.getSession(false);
        if (s != null) {
            user = (UserDTO) s.getAttribute("user");
        }
        return user;
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDTO user = getCurrentUser(request);
        if (user != null) {
            return user.getRole().equalsIgnoreCase(role.trim());
        }
        return false;
    }

    public static boolean isFounder(HttpServletRequest request) {
        return hasRole(request, "Founder");
    }

    public static boolean isMember(HttpServletRequest request) {
        return hasRole(request, "Team Member");
    }

    public static void getAccessDeniedMessage(HttpServletRequest request, String message) {
        request.setAttribute("accessDenied", message);
    }
    static StartupProjectDAO spdao = new StartupProjectDAO();
    
    public static void pushListProject(HttpServletRequest request) {
        List<StartupProjectDTO> listProjects = spdao.getAllProjects();
        if (listProjects == null || listProjects.isEmpty()) {
            request.setAttribute("message", "No project found!!!");
        } else {
            request.setAttribute("listProjects", listProjects);
        }
    }
    public static void pushListStatusProject(HttpServletRequest request){
        List<String> listStatus = spdao.getStatuProjects();
        request.setAttribute("listStatus", listStatus);
    }
    public static void prepareProjectPageData(HttpServletRequest request){
        pushListProject(request);
        pushListStatusProject(request);
    }
}
