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
import java.util.List;
import utils.DbUtils;
/**
 *
 * @author Admin
 */
public class UserDAO {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static final String getUser = "SELECT * FROM tblUsers ";
    public boolean checkLogin (String username, String password){
        String sql = getUser + "WHERE Username = ? AND Password = ?";
        try {
            cn = DbUtils.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public UserDTO getUserByUsername (String username){
        UserDTO user = new UserDTO();
        try {
            String sql = getUser + "WHERE Username = ?";
            cn=DbUtils.getConnection();
            pst=cn.prepareStatement(sql);
            pst.setString(1, username);
            rs=pst.executeQuery();
            if(rs.next()){
                String Username = rs.getString("Username");
                String Name = rs.getString("Name");
                String Password = rs.getString("Password");
                String Role = rs.getString("Role");
                user = new UserDTO(Username, Name, Password, Role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<UserDTO> getAllUsers(){
        List<UserDTO> users = new ArrayList<>();
        String sql = getUser;
        try {
            cn = DbUtils.getConnection();
            Statement stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String username = rs.getString("Username");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                users.add(new UserDTO(username, name, password, role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE tblUsers SET Password = ? WHERE Username = ?";
        try {
            cn = DbUtils.getConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, newPassword);
            pst.setString(2, username);
            boolean rowinserted = pst.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
