package scoremanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    // 戻り値は String のまま、引数名を req, res に統一します
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. 共通準備
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String f = req.getParameter("f");           // 識別コード(sj/st)
        String entYearStr = req.getParameter("f1"); // 入学年度
        String classNum = req.getParameter("f2");   // クラス
        String subjectCd = req.getParameter("f3");  // 科目コード
        String studentNo = req.getParameter("f4");  // 学生番号

        // DAOのインスタンス化
        ClassNumDAO cNumDAO = new ClassNumDAO();
        SubjectDAO sDAO = new SubjectDAO();
        StudentDAO studentDAO = new StudentDAO();
        TestDAO tDAO = new TestDAO();

        List<Test> tests = null;

        // 2. 検索ロジック
        if (f != null) {
            if (f.equals("sj")) {
                // 科目情報検索
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = sDAO.get(subjectCd);
                // TestDAOのfilter(int, String, Subject, School)を呼び出し
                tests = tDAO.filter(entYear, classNum, subject, teacher.getSchool());
                
            } else if (f.equals("st")) {
                // 学生番号検索
                Student student = studentDAO.get(studentNo);
                if (student != null) {
                    // TestDAOのfilter(Student, School)を呼び出し
                    tests = tDAO.filter(student, teacher.getSchool());
                }
            }
        }

        // 3. 画面表示用データの準備
        List<String> class_list = cNumDAO.filter(teacher.getSchool());
        List<Subject> subject_list = sDAO.filter(teacher.getSchool());
        
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // 4. リクエスト属性へのセット
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);
        
        req.setAttribute("tests", tests);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subject_set", subject_list);
        req.setAttribute("ent_year_set", entYearSet);

        // 5. 遷移先のパスを返す
        return "test_list.jsp";
    }
}