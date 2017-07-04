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
public class CourseInstanceOfficer {

    private Long courseInstanceOfficerId;
    private Long officerId;
    private Long courseInstanceId;
    private static final String TABLE_NAME = "crs_instance_officers";

    public static List<CourseInstance> getCourseInstanceOfficerByOfficerId(Long officerId, Connection connection) {
//        ApplicationData appData = new ApplicationData();
        List<ApplicationData> applicationDatas = ApplicationData.getApplicationData(connection);
        ApplicationData appData = applicationDatas.get(0);
        String year = appData.getCurrentAcademicYear().getYear()+1900+"";
        int semester = appData.getCurrentSemester();
        
        List<CourseInstance> crsInstList = new ArrayList<CourseInstance>();
        try {
            PreparedStatement ppstm = connection.prepareStatement("SELECT ci.* FROM " + TABLE_NAME + " cio INNER JOIN course_instances ci ON cio.crsinstance_id = ci.crsinstance_id WHERE cio.officer_id = ? AND ci.academic_year = ? AND ci.semester = ?");
            ppstm.setLong(1, officerId);
            ppstm.setString(2, year);
            ppstm.setInt(3, semester);
            ResultSet rs = ppstm.executeQuery();
            while(rs.next()){
                CourseInstance crsInst = new CourseInstance();
                CourseInstance.ORM(rs, crsInst);
                crsInstList.add(crsInst);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return crsInstList;
    }
    
    public static boolean update(CourseInstanceOfficer courseInstanceOfficer, Connection connection) {
        boolean result = false;
        //คำใบ้ดูได้จาก Assessment.java
        return result;
    }
    
    public static boolean insert(CourseInstanceOfficer courseInstanceOfficer, Connection connection) {

        boolean result = false;
    //คำใบ้ดูได้จาก Assessment.java

        return result;
    }

    private static void ORM(ResultSet rs, CourseInstanceOfficer courseInstanceOfficer) throws SQLException {
        if (courseInstanceOfficer == null) {
            courseInstanceOfficer = new CourseInstanceOfficer();
        }

        courseInstanceOfficer.setCourseInstanceOfficerId(rs.getLong("crsinstofficer_id"));
        courseInstanceOfficer.setOfficerId(rs.getLong("officer_id"));
        courseInstanceOfficer.setCourseInstanceId(rs.getLong("crsinstance_id"));

    }

    public Long getCourseInstanceId() {
        return courseInstanceId;
    }

    public void setCourseInstanceId(Long courseInstanceId) {
        this.courseInstanceId = courseInstanceId;
    }

    public Long getCourseInstanceOfficerId() {
        return courseInstanceOfficerId;
    }

    public void setCourseInstanceOfficerId(Long courseInstanceOfficerId) {
        this.courseInstanceOfficerId = courseInstanceOfficerId;
    }

    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
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
        final CourseInstanceOfficer other = (CourseInstanceOfficer) obj;
        if (!Objects.equals(this.courseInstanceOfficerId, other.courseInstanceOfficerId)) {
            return false;
        }
        return true;
    }

}
