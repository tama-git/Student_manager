package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

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
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setSchool(school);
            list.add(subject);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }
    
    public Subject get(String cd) throws Exception {
    	Subject subject = null;
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("select * from subject where cd=?")) {
			st.setString(1, cd);
			try (ResultSet rs = st.executeQuery()) {
				List<Subject> list = postFilter(rs, null);
				if (!list.isEmpty()) {
					subject = list.get(0);
				}
			}
		}
		return subject;
	}
 
	private List<Subject> postFilter(ResultSet rs, School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		while (rs.next()) {
			Subject s = new Subject();
			s.setCd(rs.getString("cd"));
			s.setName(rs.getString("name"));
 
			// schoolがnullの場合はResultSetから取得を試みる
			if (school == null) {
				School sc = new School();
				sc.setCd(rs.getString("school_cd"));
				s.setSchool(sc);
			} else {
				s.setSchool(school);
			}
			list.add(s);
		}
		return list;
	}
    
    public boolean save(Subject subject) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;
		int count = 0;
		try {
			Subject exists = get(subject.getCd());
			if (exists == null) {
				st = con.prepareStatement(
						"insert into subject (cd, name, school_cd) values (?, ?, ?)");
				st.setString(1, subject.getCd());
				st.setString(2, subject.getName());
				st.setString(3, subject.getSchool().getCd());
			} else {
				st = con.prepareStatement(
						"update student set name=? where cd=?");
				st.setString(1, subject.getName());
				st.setString(2, subject.getCd());
			}
			count = st.executeUpdate();
		} finally {
			if (st != null)
				st.close();
			con.close();
		}
		return count > 0;
	}
}