/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class Officer {
    
    public static final int ROLE_LECTURER = 1;
    public static final int ROLE_AA = 2;
    public static final int ROLE_ADMIN = 3;
    private static final String TABLE_NAME = "officers";
    
    private Long officerId;
    private String name;
    private Integer role;
    private String username;

    public Officer() {
    }

    public Officer(Long officerId, String name, String role) {
        this.officerId = officerId;
        this.name = name;
        this.setRole(role);
    }
    
    public Officer(Long officerId, String name, Integer role) {
        this.officerId = officerId;
        this.name = name;
        this.role = role;
    }

    public static boolean update(Officer officer, Connection connection){
        boolean result = false;
       //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static boolean delete(Officer off, Connection connection) {
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static Officer getOfficerByUsername(String username, Connection con){
        Officer officer = null;
        try {
            PreparedStatement ppstm = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE username = ?");
            ppstm.setString(1, username);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                officer = new Officer();
                ORM(rs, officer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return officer;
    }
    
    public static boolean insert(Officer officer, Connection connection){
        
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static void ORM(ResultSet rs, Officer officer) throws SQLException{
        if(officer == null){
            officer = new Officer();
        }
        
        officer.setOfficerId(rs.getLong("officer_id"));
        officer.setName(rs.getString("name"));
        officer.setRole(rs.getString("role"));
        officer.setUsername(rs.getString("username"));
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setRole(String role){
        switch(role){
            case "LECTURER" : setRole(ROLE_LECTURER); break;
            case "AA" : setRole(ROLE_AA); break;
            case "ADMIN" : setRole(ROLE_ADMIN); break;
        }
    }
    
    public static String getRoleEnum(Integer roleNum){
        String result = "Not Found";
        switch(roleNum){
            case 1 : result = "LECTURER"; break;
            case 2 : result = "AA"; break;
            case 3 : result = "ADMIN"; break;
        }
        return result;
    }
    
    public static Integer getRoleNum(String roleEnum){
        Integer result = 0;
        switch(roleEnum){
            case "LECTURER" : result = ROLE_LECTURER; break;
            case "AA" : result = ROLE_AA; break;
            case "ADMIN" : result = ROLE_ADMIN; break;
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Officer other = (Officer) obj;
        if (!Objects.equals(this.officerId, other.officerId)) {
            return false;
        }
        return true;
    }
    
}
