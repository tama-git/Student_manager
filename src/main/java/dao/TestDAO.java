package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDAO extends DAO {

    // 修正ポイント：s.ENT_YEAR を SELECT 句に追加
    private String baseSql = "SELECT t.STUDENT_NO, s.NAME AS STUDENT_NAME, s.ENT_YEAR, t.SUBJECT_CD, sub.NAME AS SUBJECT_NAME, t.SCHOOL_CD, t.NO, t.POINT, t.CLASS_NUM "
                           + "FROM TEST t "
                           + "JOIN STUDENT s ON t.STUDENT_NO = s.NO "
                           + "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD AND t.SCHOOL_CD = sub.SCHOOL_CD ";

    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            
            Student student = new Student();
            student.setNo(rSet.getString("STUDENT_NO"));
            student.setName(rSet.getString("STUDENT_NAME"));
            // 修正ポイント：ResultSet から ENT_YEAR を取得してセット
            student.setEntYear(rSet.getInt("ENT_YEAR")); 
            test.setStudent(student);
            
            Subject subject = new Subject();
            subject.setCd(rSet.getString("SUBJECT_CD"));
            subject.setName(rSet.getString("SUBJECT_NAME"));
            test.setSubject(subject);
            
            test.setSchool(school);
            test.setNo(rSet.getInt("NO"));
            test.setPoint(rSet.getInt("POINT"));
            test.setClassNum(rSet.getString("CLASS_NUM"));
            
            list.add(test);
        }
        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        String sql = baseSql + "WHERE s.ENT_YEAR = ? AND t.CLASS_NUM = ? AND t.SUBJECT_CD = ? AND t.SCHOOL_CD = ? "
                           + "ORDER BY t.STUDENT_NO ASC, t.NO ASC";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setString(4, school.getCd());
            
            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        }
        return list;
    }
    // (他のメソッドは既存のままでOKです)
}