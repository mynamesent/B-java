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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class ApplicationData {

    public static final int SEMESTER_FIRST = 1;
    public static final int SEMESTER_SUMMER = 3;
    public static final int SEMESTER_SECOND = 2;
    private static final String TABLE_NAME = "application_data";

    private Date currentAcademicYear;
    private Integer currentSemester;
    private Integer s1Begin;
    private Integer s2Begin;
    private Integer summerBegin;

    public static ApplicationData getApplicationData(String year, Connection connection) {
        ApplicationData applicationData = null;
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE current_academic_year = ?");
            ppstm.setString(1, year);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                applicationData = new ApplicationData();
                ORM(rs, applicationData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return applicationData;
    }
    
    public static List<ApplicationData> getApplicationData(Connection con){
        List<ApplicationData> applicationDataList = new ArrayList<ApplicationData>();
        try {
            PreparedStatement ppstm = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                ApplicationData applicationData = new ApplicationData();
                ORM(rs, applicationData);
                applicationDataList.add(applicationData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return applicationDataList;
    }
    
    private static void ORM(ResultSet rs, ApplicationData applicationData) throws SQLException{
        if(applicationData == null){
            applicationData = new ApplicationData();
        }
        
        applicationData.setCurrentAcademicYear(rs.getDate("current_academic_year"));
        applicationData.setCurrentSemester(rs.getString("current_semester"));
        applicationData.setS1Begin(rs.getInt("s1_begin_month"));
        applicationData.setS2Begin(rs.getInt("s2_begin_month"));
        applicationData.setSummerBegin(rs.getInt("summer_begin_month"));
        
    }
    
    public Date getCurrentAcademicYear() {
        return currentAcademicYear;
    }

    public void setCurrentAcademicYear(Date currentAcademicYear) {
        this.currentAcademicYear = currentAcademicYear;
    }

    public Integer getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void setCurrentSemester(String currentSemester){
        setCurrentSemester(getSemesterNumber(currentSemester));
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
        final ApplicationData other = (ApplicationData) obj;
        if (!Objects.equals(this.currentAcademicYear, other.currentAcademicYear)) {
            return false;
        }
        return true;
    }
    
    public static String getSemesterEnum(int semesterInt){
        String result = "Not Found";
        switch(semesterInt)
        {
            case 1: 
                result = "1";
                break;
            case 2:
                result = "2";
                break;
            case 3:
                result = "S";
                break;
        }
        return result;
    }
    
    public static Integer getSemesterNumber(String semesterEnum){
        int result = 0;
        switch(semesterEnum){
            case "1" : 
                result = SEMESTER_FIRST; 
                break; 
            case "2" : 
                result = SEMESTER_SECOND; 
                break;
            case "s":
            case "S" : 
                result = SEMESTER_SUMMER; 
                break;
        }
        return result;
    }

    public Integer getS1Begin() {
        return s1Begin;
    }

    public void setS1Begin(Integer s1Begin) {
        this.s1Begin = s1Begin;
    }

    public Integer getS2Begin() {
        return s2Begin;
    }

    public void setS2Begin(Integer s2Begin) {
        this.s2Begin = s2Begin;
    }

    public Integer getSummerBegin() {
        return summerBegin;
    }

    public void setSummerBegin(Integer summerBegin) {
        this.summerBegin = summerBegin;
    }

}
