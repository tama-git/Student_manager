package scoremanager;

import bean.Subject;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String cd = request.getParameter("cd");

        if (cd == null || cd.isBlank()) {
            request.setAttribute("error", "科目コードが取得できませんでした。");
            return "subject_delete.jsp";
        }

        SubjectDAO dao = new SubjectDAO();
        Subject subject = dao.get(cd);

        if (subject == null) {
            request.setAttribute("error", "指定された科目が見つかりませんでした。");
        }

        request.setAttribute("subject", subject);

        return "subject_delete.jsp";
    }
}
