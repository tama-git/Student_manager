package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDAO extends DAO {

	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = null;
		// データベースへの接続
		Connection con = getConnection();

		// クラス番号と学校コードで検索するSQL
		PreparedStatement st = con.prepareStatement(
			"select * from class_num where class_num=? and school_cd=?");
		st.setString(1, class_num);
		st.setString(2, school.getCd());
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			classNum = new ClassNum();
			classNum.setClassNum(rs.getString("class_num"));
			classNum.setSchool(school);
		}

		st.close();
		con.close();
		return classNum;
	}

	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection con = getConnection();

		// 指定した学校のクラス番号一覧を取得するSQL
		PreparedStatement st = con.prepareStatement(
			"select class_num from class_num where school_cd=? order by class_num asc");
		st.setString(1, school.getCd());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			list.add(rs.getString("class_num"));
		}

		st.close();
		con.close();
		return list;
	}

	public boolean save(ClassNum classNum) throws Exception {
		Connection con = getConnection();
		int count = 0;

		// 新規登録
		PreparedStatement st = con.prepareStatement(
			"insert into class_num (class_num, school_cd) values (?, ?)");
		st.setString(1, classNum.getClassNum());
		st.setString(2, classNum.getSchool().getCd());

		count = st.executeUpdate();

		st.close();
		con.close();
		return count > 0;
	}

	public boolean save(ClassNum classNum, String newClassNum) throws Exception {
		Connection con = getConnection();
		int count = 0;

		// クラス番号の更新（UPDATE）
		PreparedStatement st = con.prepareStatement(
			"update class_num set class_num=? where class_num=? and school_cd=?");
		st.setString(1, newClassNum);
		st.setString(2, classNum.getClassNum());
		st.setString(3, classNum.getSchool().getCd());

		count = st.executeUpdate();

		st.close();
		con.close();
		return count > 0;
	}
}