package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;
 
public class TeacherDAO extends DAO{
 
	public Teacher get(String id) throws Exception {
		Teacher teacher = null;
		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
			"SELECT * FROM TEACHER WHERE id = ?"
		);
		st.setString(1, id);
		ResultSet rs = st.executeQuery();
 
		if (rs.next()) {
			teacher = new Teacher();
			teacher.setId(rs.getString("id"));
			teacher.setName(rs.getString("name"));
			School school = new School();
			school.setCd(rs.getString("school_cd"));
			teacher.setSchool(school);
		}
 
		rs.close();
		st.close();
		con.close();
		return teacher;
	}
 
	public Teacher login(String id, String password) throws Exception {
		Teacher teacher = null;
		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
			"SELECT * FROM TEACHER WHERE id = ? AND password = ?"
		);
		st.setString(1, id);
		st.setString(2, password);
		ResultSet rs = st.executeQuery();
 
		if (rs.next()) {
			teacher = new Teacher();
			teacher.setId(rs.getString("id"));
			teacher.setName(rs.getString("name"));
 
			School school = new School();
			school.setCd(rs.getString("school_cd"));
			teacher.setSchool(school);
		}
 
		rs.close();
		st.close();
		con.close();
		return teacher;
	}
}