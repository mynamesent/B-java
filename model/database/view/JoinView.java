/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.model.database.view;

import bootcamp.model.database.ApplicationData;
import bootcamp.model.database.Assessment;
import bootcamp.model.database.CourseInstance;
import bootcamp.model.database.CourseInstanceOfficer;
import bootcamp.model.database.Officer;
import bootcamp.model.database.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class JoinView {
    
    public static Map<String, Object> getScoreByCourse(Long assessmentId, Long courseId, Connection con){
        Map<String, Object> result = new TreeMap<>();
        List<Assessment> assessmentsResult = new ArrayList<>();
        List<Score> scoresResult = new ArrayList<>();
        List<Assessment> assessments = Assessment.getAssessments(courseId, con);
        List<Score> scores = Score.getScoreByStudentId(courseId, con);
        for (Iterator<Score> iterator = scores.iterator(); iterator.hasNext();) {
            Score next = iterator.next();
            if(next.getAssessmentId() == assessmentId){
                for (Iterator<Assessment> iterator1 = assessments.iterator(); iterator1.hasNext();) {
                    Assessment next1 = iterator1.next();
                    if(next1.getAssessmentId() == courseId){
                        assessmentsResult.add(next1);
                        scoresResult.add(next);
                    }
                }
            }
        }
        
        result.put("assessments", assessmentsResult);
        result.put("scores", scoresResult);
        
        return result;
    }
    
    
    public static List<CourseInstance> getEnrollCourse(Long studentId, Integer semester, String year, Connection con){
        List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
        try {
            PreparedStatement ppstm = con.prepareStatement("SELECT c.* FROM enrollments e "
                                                                + "JOIN course_instances c "
                                                                + "ON e.crsinstance_id = c.crsinstance_id "
                                                                + "WHERE c.semester = ? AND c.academic_year = ? AND e.student_id = ?");
            ppstm.setInt(1, semester);
            ppstm.setString(2, year);
            ppstm.setLong(3, studentId);
            
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                CourseInstance courseInstance = new CourseInstance();
                CourseInstance.ORM(rs, courseInstance);
                courseInstances.add(courseInstance);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JoinView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseInstances;
    }
    
    public static void getLastSemester(String studentId, String currentYear, String currentSemester, Connection con){
        Date currentDate = new Date();
        ApplicationData currentYearApplicationData=  ApplicationData.getApplicationData((currentDate.getYear()+1900)+"", con);
        ApplicationData previousYearApplicationData = ApplicationData.getApplicationData((currentDate.getYear()+1900-1)+"", con);
        
        //Find Last Semester
        if(currentYearApplicationData != null){
            if(currentDate.getMonth() > currentYearApplicationData.getS1Begin()){
                //current year
                currentYear = (currentDate.getYear() + 1900) + "";
                
                if(currentYearApplicationData.getS2Begin() > currentYearApplicationData.getS1Begin()){
                    //Semester 1 & 2 in the same year
                    if(currentDate.getMonth() > currentYearApplicationData.getS2Begin()){
                        currentSemester = "2";
                    }else if(currentDate.getMonth() > currentYearApplicationData.getS1Begin()){
                        currentSemester = "1";
                    }
                }else{
                    //Semester 1 & 2 in the different year
                    if(currentDate.getMonth() > currentYearApplicationData.getS1Begin()){
                        currentYear = (currentDate.getYear() + 1900) + "";
                        currentSemester = "1";
                    }
                }
            }else{
                //previous year
                currentYear = (currentDate.getYear() + 1900 - 1) + "";
                
                if(previousYearApplicationData.getS2Begin() < previousYearApplicationData.getSummerBegin()){
                    //Semester 2 & summer in the same year
                    if(currentDate.getMonth() > previousYearApplicationData.getSummerBegin()){
                        currentSemester = "3";
                    }else if(currentDate.getMonth() > previousYearApplicationData.getS2Begin()){
                        currentSemester = "2";
                    }
                    
                }else {
                    //Semester 2 & summer in the different year
                    if(currentDate.getMonth() > previousYearApplicationData.getSummerBegin()){
                        currentSemester = "3";
                    }
                    
                }
            }
        }else{
            //previous year
            currentYear = (currentDate.getYear() + 1900 - 1) + "";

            if(previousYearApplicationData.getS2Begin() < previousYearApplicationData.getSummerBegin()){
                //Semester 2 & summer in the same year
                if(currentDate.getMonth() > previousYearApplicationData.getSummerBegin()){
                    currentSemester = "3";
                }else if(currentDate.getMonth() > previousYearApplicationData.getS2Begin()){
                    currentSemester = "2";
                }

            }else {
                //Semester 2 & summer in the different year
                if(currentDate.getMonth() > previousYearApplicationData.getSummerBegin()){
                    currentSemester = "3";
                }

            }
            
        }
    }
    
}
