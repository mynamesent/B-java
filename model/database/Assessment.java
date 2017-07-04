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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class Assessment {
    
    private static final String TABLE_NAME = "assessments";
    
    private Long assessmentId;
    private String name;
    private Double score;
    private Boolean visible;
    private Long courseInstanceId;
    
    
    public static boolean update(Assessment assessment, Connection connection){
        boolean result = false;
        //Method นี้ใช้สำหรับอัพเดทข้อมูล Assessment เช่น ในระบบมี Midterm คะแนนเต็ม 50 แต่ต้องการแก้เป็น 25
        //Update SQL
        return result;
    }
    
    public static List<Assessment> getAssessment(Connection connection){
        List<Assessment> assessmentsList = new ArrayList<Assessment>();
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = ppstm.executeQuery();
            while (rs.next()) {
                Assessment assessment = new Assessment();
                ORM(rs, assessment);
                assessmentsList.add(assessment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assessmentsList;
    }
    
    public static List<Assessment> getAssessments(Long courseInstanceId, Connection connection){
        List<Assessment> assessmentsList = new ArrayList<Assessment>();
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE crsinstance_id = ?");
            ppstm.setLong(1, courseInstanceId);
            ResultSet rs = ppstm.executeQuery();
            while (rs.next()) {
                Assessment assessment = new Assessment();
                ORM(rs, assessment);
                assessmentsList.add(assessment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assessmentsList;
    }
    
    public static Assessment getAssessment(Long assessmentId, Connection connection) {
        Assessment assessment = null;
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE assessment_id = ?");
            ppstm.setLong(1, assessmentId);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                assessment = new Assessment();
                ORM(rs, assessment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return assessment;
    }
    
    public static Boolean delete(Long assessmentId, Connection connection) {
        Boolean result = false;
        //สร้างได้ก็ต้องลบได้สิอิอิ
        //DELETE Query
        return result;
    }
    
    private static void ORM(ResultSet rs, Assessment assessment) throws SQLException{
        if(assessment == null){
            assessment = new Assessment();
        }
        
        assessment.setAssessmentId(rs.getLong("assessment_id"));
        assessment.setName(rs.getString("name"));
        assessment.setScore(rs.getDouble("score"));
        assessment.setVisible(rs.getBoolean("visible"));
        assessment.setCourseInstanceId(rs.getLong("crsinstance_id"));
        
    }
    
    public static boolean insert(Assessment assessment, Connection connection){
        
        boolean result = false;
        //method นี้เอาไว้สร้าง Assessment นะจ้ะ
        //INSERT Query
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
        final Assessment other = (Assessment) obj;
        if (this.assessmentId != other.assessmentId) {
            return false;
        }
        return true;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getCourseInstanceId() {
        return courseInstanceId;
    }

    public void setCourseInstanceId(Long courseInstanceId) {
        this.courseInstanceId = courseInstanceId;
    }
    
}
