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

    public List<Test> filter(Student student, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        
        // 学生番号(STUDENT_NO)で絞り込むSQL
        String sql = baseSql + "WHERE t.STUDENT_NO = ? AND t.SCHOOL_CD = ? "
                           + "ORDER BY t.SUBJECT_CD ASC, t.NO ASC";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getNo());
            statement.setString(2, school.getCd());
            
            ResultSet rSet = statement.executeQuery();
            // 共通の postFilter メソッドを使って List<Test> に変換
            list = postFilter(rSet, school);
        }
        return list;
    }
    
 // TestDAO.java に追加
 // 1件分の保存処理を「本当の登録処理」に書き換える
 // ① リストを受け取って保存するメソッド（Actionから呼ばれる方）
    public boolean save(List<Test> list) throws Exception {
        int count = 0;
        for (Test test : list) {
            // 下にある ② のメソッドを1件ずつ呼び出す
            boolean result = save(test); 
            if (result) {
                count++;
            }
        }
        // 全件分、正しく処理できたら true
        return count == list.size();
    }

    // ② 1件分をDBに保存するメソッド
    public boolean save(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // まずはUPDATE（更新）を試みる
            String updateSql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND NO = ? AND SCHOOL_CD = ?";
            statement = connection.prepareStatement(updateSql);
            statement.setInt(1, test.getPoint());
            statement.setString(2, test.getStudent().getNo());
            statement.setString(3, test.getSubject().getCd());
            statement.setInt(4, test.getNo());
            statement.setString(5, test.getSchool().getCd());
            
            count = statement.executeUpdate();

            // 更新できなかったら（新規データなら）INSERT（挿入）する
            if (count == 0) {
                String insertSql = "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (?, ?, ?, ?, ?, ?)";
                statement.close(); // 前のstatementを閉じる
                statement = connection.prepareStatement(insertSql);
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
                
                count = statement.executeUpdate();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
    
}

