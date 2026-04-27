package scoremanager;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap; // 追加
import java.util.List;
import java.util.Map;    // 追加

import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject; // 作成した新しいBean
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
        if (entYearStr != null && !entYearStr.equals("0") && 
            classNum != null && !classNum.equals("0") && 
            subjectCd != null && !subjectCd.equals("0")) {

            try {
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = sDao.get(subjectCd);
                
                if (subject != null) {
                    // ① DBから生の成績リスト（回数ごとに別レコード）を取得
                    List<Test> rawTests = tDao.filter(entYear, classNum, subject, teacher.getSchool());
                    
                    // ② 学生番号をキーにして、データをまとめるためのMapを準備
                    Map<String, TestListSubject> map = new HashMap<>();

                    for (Test t : rawTests) {
                        String sNo = t.getStudent().getNo();
                        
                        // Mapにまだその学生がいなければ、新しくTestListSubjectを作って入れる
                        if (!map.containsKey(sNo)) {
                            TestListSubject tls = new TestListSubject();
                            tls.setEntYear(t.getStudent().getEntYear());
                            tls.setStudentNo(sNo);
                            tls.setStudentName(t.getStudent().getName());
                            tls.setClassNum(t.getClassNum());
                            map.put(sNo, tls);
                        }
                        
                        // その学生のオブジェクトに対して、今回の回数(t.getNo())と点数(t.getPoint())を保存
                        map.get(sNo).putPoint(t.getNo(), t.getPoint());
                    }

                    // ③ Mapの値をリストに変換してリクエストにセット（これでJSP側は1人1行になる）
                    req.setAttribute("tests", new ArrayList<>(map.values())); 
                }
            } catch (NumberFormatException e) {
                // エラー処理
            }
        }

        // 4. 表示状態の維持とプルダウンデータの再セット
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        req.setAttribute("yearList", createYearList());
        req.setAttribute("subjects", sDao.filter(teacher.getSchool()));
        req.setAttribute("classNum", cNumDao.filter(teacher.getSchool()));

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