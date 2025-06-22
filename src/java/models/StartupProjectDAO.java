/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class StartupProjectDAO {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static final String getProjects = "SELECT * FROM tblStartupProjects ";
    private static final String addProjects = "INSERT INTO tblStartupProjects(project_id, project_name, Description, Status, estimated_launch)"
                                                +"VALUES(?,?,?,?,?)";
    private static final String updateStatus = "UPDATE tblStartupProjects SET Status = ? WHERE project_id=?";
    public List<StartupProjectDTO> getAllProjects (){
        List<StartupProjectDTO> listProjects = new ArrayList<>();
        String sql = getProjects;
        try {
            cn = DbUtils.getConnection();
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               int project_id = rs.getInt("project_id");
               String project_name = rs.getString("project_name");
               String description = rs.getString("Description");
               String status = rs.getString("Status");
               Date estimated_launch = rs.getDate("estimated_launch");
               listProjects.add(new StartupProjectDTO(project_id, project_name, description, status, estimated_launch));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProjects;
    }
    public List<StartupProjectDTO> getProjectsByName (String projectName){
        List<StartupProjectDTO> listProjects = new ArrayList<>();
        String sql = getProjects + "WHERE project_name like ? ";
        try {
            cn = DbUtils.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, "%"+projectName+"%");
            rs = pst.executeQuery();
            while(rs.next()){
               int project_id = rs.getInt("project_id");
               String project_name = rs.getString("project_name");
               String description = rs.getString("Description");
               String status = rs.getString("Status");
               Date estimated_launch = rs.getDate("estimated_launch");
               listProjects.add(new StartupProjectDTO(project_id, project_name, description, status, estimated_launch));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProjects;
    }
    public List<String> getStatuProjects(){
        List<String> listStatus = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT Status FROM tblStartupProjects";
            cn = DbUtils.getConnection();
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String status = rs.getString("Status");
                listStatus.add(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStatus;
    }
    
    public boolean addProject(StartupProjectDTO project){
        String sql = addProjects;
        try {
            cn = DbUtils.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setInt(1,generateIdProject());
            pst.setString(2, project.getProject_name());
            pst.setString(3,project.getDescription());
            pst.setString(4,project.getStatus());
            pst.setDate(5, new java.sql.Date(project.getEstimated_launch().getTime()));
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateStatus(int projectID, String status){
        String sql = updateStatus;
        try {
            cn = DbUtils.getConnection();
            pst=cn.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, projectID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private int generateIdProject(){
        int id=-1;
        try {
            String sql = "SELECT MAX(project_id) FROM tblStartupProjects";
            cn = DbUtils.getConnection();
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                id = rs.getInt(1)+1;
            }else{
                id=1;
            }
        } catch (Exception e) {
        }
        return id;
    }
    
}
