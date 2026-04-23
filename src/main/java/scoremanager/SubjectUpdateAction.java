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
        
     // 1. パラメータから科目コードを取得
        String cd = request.getParameter("cd");

        // 2. SubjectDAOを使って既存の科目データを取得（引数は1つに変更）
        SubjectDAO sDao = new SubjectDAO();
        Subject subject = sDao.get(cd);

        // 3. 取得したsubjectから学校コードを取得する
        // subjectがnullでないことを確認してから取得するとより安全です
        if (subject != null && subject.getSchool() != null) {
            String schoolCd = subject.getSchool().getCd();
            // 必要であればここで schoolCd をログ出力したり、別の処理に使ったりできます
        }

        // 4. JSPにデータを渡す
        request.setAttribute("subject", subject);

        // 5. 科目変更画面を表示
        return "subject_update.jsp";
    }
}