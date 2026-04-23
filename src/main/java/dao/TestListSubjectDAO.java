package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDAO extends DAO {

    private String baseSql =
        "select st.ent_year, " +
        "       st.no as student_no, " +
        "       st.name as student_name, " +
        "       st.class_num, " +
        "       t.no as test_no, " +
        "       t.point " +
        "from student st " +
        "left join test t " +
        "       on st.no = t.student_no " +
        "      and st.school_cd = t.school_cd " +
        "      and t.subject_cd = ? " +
        "where st.ent_year = ? " +
        "  and st.class_num = ? " +
        "  and st.school_cd = ? " +
        "  and st.is_attend = true " +
        "order by st.no asc, t.no asc";

    private List<TestListSubject> postFilter(ResultSet rs) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        TestListSubject current = null;
        String currentStudentNo = null;

        while (rs.next()) {
            String studentNo = rs.getString("student_no");

            if (current == null || !studentNo.equals(currentStudentNo)) {
                current = new TestListSubject();
                current.setEntYear(rs.getInt("ent_year"));
                current.setStudentNo(studentNo);
                current.setStudentName(rs.getString("student_name"));
                current.setClassNum(rs.getString("class_num"));
                list.add(current);
                currentStudentNo = studentNo;
            }

            int testNo = rs.getInt("test_no");
            int point = rs.getInt("point");

            // left join なので test データがない行も来る
            if (!rs.wasNull()) {
                current.putPoint(testNo, point);
            }
        }

        return list;
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(baseSql)
        ) {
            st.setString(1, subject.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setString(4, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }
        }

        return list;
    }
}