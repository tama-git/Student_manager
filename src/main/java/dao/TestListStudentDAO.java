package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDAO extends DAO {

    private String baseSql =
        "select s.name as subject_name, " +
        "       t.subject_cd, " +
        "       t.no, " +
        "       t.point " +
        "from test t " +
        "inner join subject s on t.subject_cd = s.cd and t.school_cd = s.school_cd " +
        "where t.student_no = ? " +
        "order by t.subject_cd asc, t.no asc";

    private List<TestListStudent> postFilter(ResultSet rs) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        while (rs.next()) {
            TestListStudent test = new TestListStudent();
            test.setSubjectName(rs.getString("subject_name"));
            test.setSubjectCd(rs.getString("subject_cd"));
            test.setNum(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));
            list.add(test);
        }

        return list;
    }

    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(baseSql)
        ) {
            st.setString(1, student.getNo());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }
        }

        return list;
    }
}