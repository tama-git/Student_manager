package scoremanager; // パッケージ名は環境に合わせて調整してください

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータの取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // 2. DAOの準備
        TestDAO tDao = new TestDAO();
        SubjectDAO sDao = new SubjectDAO();
        ClassNumDAO cNumDao = new ClassNumDAO();

        // 3. 検索処理
        // 未選択(0)やnullを避けて実行
        if (entYearStr != null && !entYearStr.equals("0") && 
            classNum != null && !classNum.equals("0") && 
            subjectCd != null && !subjectCd.equals("0")) {

            try {
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = sDao.get(subjectCd);
                
                if (subject != null) {
                    // 成績リストを取得してリクエストにセット
                    List<Test> tests = tDao.filter(entYear, classNum, subject, teacher.getSchool());
                    req.setAttribute("tests", tests); 
                }
            } catch (NumberFormatException e) {
                // 数値変換失敗時の処理（必要に応じて）
            }
        }
        

        // 4. 表示状態の維持とプルダウンデータの再セット
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        req.setAttribute("yearList", createYearList());
        req.setAttribute("subjects", sDao.filter(teacher.getSchool()));
        req.setAttribute("classNum", cNumDao.filter(teacher.getSchool()));

        // 5. 検索結果を表示するJSPへ
        return "test_list_subject.jsp";
    }

    private List<Integer> createYearList() {
        List<Integer> yearList = new ArrayList<>();
        int currentYear = Year.now().getValue();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            yearList.add(i);
        }
        return yearList;
    }
}