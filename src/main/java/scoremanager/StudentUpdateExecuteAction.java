package scoremanager;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータの取得
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		int entYear = Integer.parseInt(request.getParameter("entYear"));
		String classNum = request.getParameter("classNum");
		// チェックボックス（在学フラグ）の判定
		boolean isAttend = request.getParameter("isAttend") != null;
		
		// Studentインスタンスの組み立て
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());

		// DAOで更新実行
		StudentDAO dao = new StudentDAO();
		dao.save(student);

		// 【重要】ここの戻り値のJSPファイル名が正しいか確認！
		return "student_update_done.jsp";
	}
}