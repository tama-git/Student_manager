package scoremanager;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 1. パラメータ（学生番号）を取得
		String no = request.getParameter("no");

		// 2. StudentDaoを使って詳細データを取得（シーケンス図：学生の詳細データを取得）
		StudentDAO sDao = new StudentDAO();
		Student student = sDao.get(no);

		
		// 3. ClassNumDaoを使ってクラス一覧を取得（シーケンス図：ClassNumDaoから取得）
		ClassNumDAO cDao = new ClassNumDAO();
		List<String> classList = cDao.filter(teacher.getSchool());

		// 4. JSPにデータを渡す
		request.setAttribute("student", student);
		request.setAttribute("classList", classList);

		// 5. 学生変更画面を表示
		return "student_update.jsp";
	}
}