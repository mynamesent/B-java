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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oFNo's PC
 */
public class CourseInstance {
    
    private static final String TABLE_NAME = "course_instances";
    
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Date year;
    private Integer semester;
    private String courseInstanceName;

    public static boolean update(CourseInstance courseInstance, Connection connection){
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    public static List<CourseInstance> getCourseInstanceBySemester(Integer semester, String year, Connection con){
        List<CourseInstance> courseInstances = null;
        switch(semester){
            case ApplicationData.SEMESTER_FIRST : courseInstances = getCourseInstanceBySemester(year,semester+"",con); break;
            case ApplicationData.SEMESTER_SECOND : courseInstances = getCourseInstanceBySemester(year,semester+"",con); break;
            case ApplicationData.SEMESTER_SUMMER : courseInstances = getCourseInstanceBySemester(year,"S",con); break;
        }
        return courseInstances;
    }
    public static List<CourseInstance> getCourseInstanceBySemester(String year, String semester, Connection con){
        List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
        try {
            PreparedStatement ppstm = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE academic_year = ? and semester = ?");
            ppstm.setString(1, year);
            ppstm.setString(2, semester);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                CourseInstance courseInstance = new CourseInstance();
                ORM(rs, courseInstance);
                courseInstances.add(courseInstance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseInstances;
    }
    
    public static CourseInstance getCourseInstance(Long courseId, Connection connection) {
        CourseInstance courseInstance = null;
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE crsinstance_id = ?");
            ppstm.setLong(1, courseId);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                courseInstance = new CourseInstance();
                ORM(rs, courseInstance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courseInstance;
    }
    
    public static long insert(CourseInstance courseInstance, Connection connection){
        long generatedkey = -1;
        //คำใบ้ดูได้จาก Assessment.java
        //ต้องมีการ return generatedkey กลับไปด้วยเพื่อให้ Controllor สามารถนำไปใช้งานต่อได้
            // ResultSet rs = ppstm.getGeneratedKeys();
            // if (rs.next()) {
            //     generatedkey = rs.getInt(1);
            // }
        return generatedkey;
    }
    
    public static boolean delete(CourseInstance courseInstance, Connection connection){
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static List<CourseInstance> getAllCourseInstance(Connection connection) {
        List<CourseInstance> crsList = new ArrayList<CourseInstance>();
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = ppstm.executeQuery();
            while (rs.next()) {
                CourseInstance crs = new CourseInstance();
                ORM(rs, crs);
                crsList.add(crs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Officer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return crsList;
    }
    
    public static void ORM(ResultSet rs, CourseInstance courseInstance) throws SQLException{
        if(courseInstance == null){
            courseInstance = new CourseInstance();
        }
        
        courseInstance.setCourseId(rs.getLong("crsinstance_id"));
        courseInstance.setCourseCode(rs.getString("course_code"));
        courseInstance.setCourseName(rs.getString("course_name"));
        courseInstance.setYear(rs.getDate("academic_year"));
        courseInstance.setSemester(rs.getString("semester"));
        courseInstance.setCourseInstanceName(rs.getString("crsinstance_name"));
        
    }
    
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public void setSemester(String semester){
        setSemester(ApplicationData.getSemesterNumber(semester));
    }

    public String getCourseInstanceName() {
        return courseInstanceName;
    }

    public void setCourseInstanceName(String courseInstanceName) {
        this.courseInstanceName = courseInstanceName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final CourseInstance other = (CourseInstance) obj;
        if (this.courseId != other.courseId) {
            return false;
        }
        return true;
    }
    
    
}
