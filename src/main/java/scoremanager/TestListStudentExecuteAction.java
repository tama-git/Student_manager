package scoremanager;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ① パラメータの取得
        String studentNo = request.getParameter("f10"); // 学生番号

        // 検索結果とエラー用のリスト
        List<Test> tests = null;
        List<String> errors = new ArrayList<>();
        TestDAO tDao = new TestDAO();

        // ② 検索ロジック
        if (studentNo != null && !studentNo.isBlank()) {
            // Studentオブジェクトを作ってDAOに渡す
            Student student = new Student();
            student.setNo(studentNo);

            // DAOで検索実行
            tests = tDao.filter(student, teacher.getSchool());

            if (tests == null || tests.isEmpty()) {
                errors.add("該当する学生の成績データが見つかりませんでした。");
            }
        } else {
            errors.add("学生番号を入力してください。");
        }

        // ③ セレクトボックスの再構築（検索後もプルダウンを表示させるために必須）
        request.setAttribute("yearList", createYearList());
        
        ClassNumDAO cNumDao = new ClassNumDAO();
        request.setAttribute("classNum", cNumDao.filter(teacher.getSchool()));
        
        SubjectDAO sDao = new SubjectDAO();
        request.setAttribute("subjects", sDao.filter(teacher.getSchool()));

        // ④ JSPへのデータ受け渡し
        request.setAttribute("f10", studentNo); // 入力値を保持
        request.setAttribute("tests", tests);   // 成績リスト
        request.setAttribute("errors", errors); // エラーメッセージ

        // 検索結果を表示するJSPを指定
        return "test_list_student.jsp";
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