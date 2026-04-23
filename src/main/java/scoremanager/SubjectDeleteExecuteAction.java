package scoremanager;

import bean.Subject;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        String cd = request.getParameter("cd");

        if (cd == null || cd.isBlank()) {
            request.setAttribute("error", "科目コードが取得できませんでした。");
            return "subject_delete.jsp";
        }

        SubjectDAO dao = new SubjectDAO();
        Subject subject = dao.get(cd);

        if (subject == null) {
            request.setAttribute("error", "削除対象の科目が存在しません。既に削除されている可能性があります。");
            return "subject_delete.jsp";
        }

        boolean result = dao.delete(cd);

        request.setAttribute("result", result);
        request.setAttribute("subject", subject);

        return "subject_delete_done.jsp";
    }
}
