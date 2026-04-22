package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
 
public class StudentDAO extends DAO {
 
	private String baseSql = "select * from student where school_cd=?";
 
	/**
	 * 全学生を取得する（追加分）
	 */
	public List<Student> selectAll() throws Exception {
		List<Student> list = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("select * from student")) {
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Student s = new Student();
					s.setNo(rs.getString("no"));
					s.setName(rs.getString("name"));
					s.setEntYear(rs.getInt("ent_year"));
					s.setClassNum(rs.getString("class_num"));
					s.setAttend(rs.getBoolean("is_attend"));
 
					// 各学生の所属学校をセット
					School school = new School();
					school.setCd(rs.getString("school_cd"));
					s.setSchool(school);
 
					list.add(s);
				}
			}
		}
		return list;
	}
 
	public Student get(String no) throws Exception {
		Student student = null;
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("select * from student where no=?")) {
			st.setString(1, no);
			try (ResultSet rs = st.executeQuery()) {
				List<Student> list = postFilter(rs, null);
				if (!list.isEmpty()) {
					student = list.get(0);
				}
			}
		}
		return student;
	}
 
	private List<Student> postFilter(ResultSet rs, School school) throws Exception {
		List<Student> list = new ArrayList<>();
		while (rs.next()) {
			Student s = new Student();
			s.setNo(rs.getString("no"));
			s.setName(rs.getString("name"));
			s.setEntYear(rs.getInt("ent_year"));
			s.setClassNum(rs.getString("class_num"));
			s.setAttend(rs.getBoolean("is_attend"));
 
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
 
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String sql = baseSql + " and ent_year=? and class_num=? and is_attend=?";
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setString(3, classNum);
			st.setBoolean(4, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				list = postFilter(rs, school);
			}
		}
		return list;
	}
 
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String sql = baseSql + " and ent_year=? and is_attend=?";
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setBoolean(3, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				list = postFilter(rs, school);
			}
		}
		return list;
	}
 
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String sql = baseSql + " and is_attend=?";
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, school.getCd());
			st.setBoolean(2, isAttend);
			try (ResultSet rs = st.executeQuery()) {
				list = postFilter(rs, school);
			}
		}
		return list;
	}
 
	public boolean save(Student student) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;
		int count = 0;
		try {
			Student exists = get(student.getNo());
			if (exists == null) {
				st = con.prepareStatement(
						"insert into student (no, name, ent_year, class_num, is_attend, school_cd) values (?, ?, ?, ?, ?, ?)");
				st.setString(1, student.getNo());
				st.setString(2, student.getName());
				st.setInt(3, student.getEntYear());
				st.setString(4, student.getClassNum());
				st.setBoolean(5, student.isAttend());
				st.setString(6, student.getSchool().getCd());
			} else {
				st = con.prepareStatement(
						"update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				st.setString(1, student.getName());
				st.setInt(2, student.getEntYear());
				st.setString(3, student.getClassNum());
				st.setBoolean(4, student.isAttend());
				st.setString(5, student.getNo());
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