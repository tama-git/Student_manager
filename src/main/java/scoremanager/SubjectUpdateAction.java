package scoremanager;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        // 1. パラメータ（科目コード）を取得
        String cd = request.getParameter("cd");

        // 2. SubjectDAOを使って既存の科目データを取得
        SubjectDAO sDao = new SubjectDAO();
        // 現在のDAOの仕様（引数1つ）に合わせて取得
        Subject subject = sDao.get(cd);

        // 3. JSPにデータを渡す
        // 学校コードと科目コードは変更不可として表示するだけなので、
        // subjectオブジェクトをそのまま渡せばOKです
        request.setAttribute("subject", subject);

        // 4. 科目変更画面を表示
        return "subject_update.jsp";
    }
}