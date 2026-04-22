package scoremanager;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 科目一覧を取得
		SubjectDAO dao = new SubjectDAO();
		List<Subject> subjects = dao.filter(teacher.getSchool());

		// JSPに渡すデータをセット
		request.setAttribute("subjects", subjects);

		return "subject_list.jsp";
	}
}