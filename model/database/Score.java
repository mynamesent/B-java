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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class Score {

    private Long scoreId;
    private Long studentId;
    private Long assessmentId;
    private Double score;
    private String grade;
    private Date last_upadate;
    private static final String TABLE_NAME = "scores";

    public static List<Score> getScoreByAssessment(Long assessmentId, Connection con){
        List<Score> scores = new ArrayList<Score>();
        try {
            PreparedStatement ppstm = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE assessment_id = ?");
            ppstm.setLong(1, assessmentId);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                Score score = new Score();
                Score.ORM(rs, score);
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }
    
    public static Boolean delete(Long scoreId, Connection connection) {
        Boolean result = false;
       //คำใบ้ดูได้จาก Assessment.java
        
        return result;
    }
    
    public static boolean update(Score score, Connection connection) {
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static List<Score> getScoreByStudentId(Long studentId, Connection connection){
        List<Score> scoreList = new ArrayList<Score>();
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE STUDENT_ID = ?");
            ppstm.setLong(1, studentId);
            ResultSet rs = ppstm.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                ORM(rs, score);
                scoreList.add(score);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scoreList;
    }
    
    public static List<Score> getLatestScore(Long studentId, Connection connection){
        List<Score> scoreList = new ArrayList<Score>();
        // ดึงค่า 5 คะแนนล่าสุด
        return scoreList;
    }

    public static boolean insert(Score score, Connection connection) {

        boolean result = false;
    //คำใบ้ดูได้จาก Assessment.java
        return result;
    }

    private static void ORM(ResultSet rs, Score score) throws SQLException {
        if (score == null) {
            score = new Score();
        }

        score.setScoreId(rs.getLong("score_id"));
        score.setStudentId(rs.getLong("student_id"));
        score.setAssessmentId(rs.getLong("assessment_id"));
        score.setScore(rs.getDouble("score"));
        score.setGrade(rs.getString("grade"));
        score.setLast_upadate(rs.getDate("last_update"));

    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
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
        final Score other = (Score) obj;
        if (!Objects.equals(this.scoreId, other.scoreId)) {
            return false;
        }
        return true;
    }

    public Date getLast_upadate() {
        return last_upadate;
    }

    public void setLast_upadate(Date last_upadate) {
        this.last_upadate = last_upadate;
    }

}
