package scoremanager;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータの取得
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");

		// Studentインスタンスの組み立て
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		// DAOで更新実行
		SubjectDAO dao = new SubjectDAO();
		subject.setSchool(teacher.getSchool());
		dao.save(subject);

		return "subject_update_done.jsp";
	}

}