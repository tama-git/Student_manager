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

    /**
     * 基本となるSELECT文
     * 学生名や科目名を表示するために各テーブルと結合しています
     */
    private String baseSql = "SELECT t.STUDENT_NO, s.NAME AS STUDENT_NAME, t.SUBJECT_CD, sub.NAME AS SUBJECT_NAME, t.SCHOOL_CD, t.NO, t.POINT, t.CLASS_NUM "
                           + "FROM TEST t "
                           + "JOIN STUDENT s ON t.STUDENT_NO = s.NO "
                           + "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.CD AND t.SCHOOL_CD = sub.SCHOOL_CD ";

    /**
     * 特定の成績データを1件取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        String sql = baseSql + "WHERE t.STUDENT_NO = ? AND t.SUBJECT_CD = ? AND t.SCHOOL_CD = ? AND t.NO = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);
            
            ResultSet rSet = statement.executeQuery();
            List<Test> list = postFilter(rSet, school);
            if (!list.isEmpty()) {
                test = list.get(0);
            }
        }
        return test;
    }

    /**
     * ResultSetからTestオブジェクトのリストへ変換する
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            
            // Student情報のセット
            Student student = new Student();
            student.setNo(rSet.getString("STUDENT_NO"));
            student.setName(rSet.getString("STUDENT_NAME"));
            test.setStudent(student);
            
            // Subject情報のセット
            Subject subject = new Subject();
            subject.setCd(rSet.getString("SUBJECT_CD"));
            subject.setName(rSet.getString("SUBJECT_NAME"));
            test.setSubject(subject);
            
            // その他のフィールドセット
            test.setSchool(school);
            test.setNo(rSet.getInt("NO"));
            test.setPoint(rSet.getInt("POINT"));
            test.setClassNum(rSet.getString("CLASS_NUM"));
            
            list.add(test);
        }
        return list;
    }

    /**
     * 【科目検索用】
     * 入学年度、クラス、科目の3項目を指定して成績リストを取得する
     */
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

    /**
     * 【学生番号検索用】
     * 学生番号のみを指定して成績リストを取得する
     */
    public List<Test> filter(Student student, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        String sql = baseSql + "WHERE t.STUDENT_NO = ? AND t.SCHOOL_CD = ? "
                           + "ORDER BY t.SUBJECT_CD ASC, t.NO ASC";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, student.getNo());
            statement.setString(2, school.getCd());
            
            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        }
        return list;
    }

    /**
     * 成績リストを一括保存する
     */
    public boolean save(List<Test> list) throws Exception {
        boolean result = true;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // トランザクション開始
            try {
                for (Test test : list) {
                    if (!save(test, connection)) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
        return result;
    }

    /**
     * 1件の成績データを保存（登録または更新）する
     */
    public boolean save(Test test, Connection connection) throws Exception {
        int count = 0;
        // H2 Database等のMERGE構文（存在すれば更新、なければ挿入）
        String sql = "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) "
                   + "KEY(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());
            
            count = statement.executeUpdate();
        }
        return count > 0;
    }
}