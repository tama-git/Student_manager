package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bean.Subject;
import bean.School;

public class SubjectDAO extends DAO {

    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();
        // ログインしている先生と同じ学校の科目だけを取得
        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC"
        );
        st.setString(1, school.getCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("CD"));
            subject.setName(rs.getString("NAME"));
            subject.setSchool(school);
            list.add(subject);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }
}