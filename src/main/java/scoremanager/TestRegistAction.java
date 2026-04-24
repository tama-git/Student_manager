package scoremanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestListSubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Teacher teacher = (Teacher) request.getSession().getAttribute("user");
    	School school = teacher.getSchool();
    	
        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        TestListSubjectDAO testListSubjectDao = new TestListSubjectDAO();

        List<Integer> entYearSet = createEntYearList();
        List<String> classNumSet = classNumDao.filter(school);
        List<Subject> subjects = subjectDao.filter(school);

        request.setAttribute("entYearSet", entYearSet);
        request.setAttribute("classNumSet", classNumSet);
        request.setAttribute("subjects", subjects);
        request.setAttribute("noSet", createNoList());

        String f1 = request.getParameter("f1"); // 入学年度
        String f2 = request.getParameter("f2"); // クラス
        String f3 = request.getParameter("f3"); // 科目コード
        String f4 = request.getParameter("f4"); // 回数

        request.setAttribute("f1", f1);
        request.setAttribute("f2", f2);
        request.setAttribute("f3", f3);
        request.setAttribute("f4", f4);

        // 初期表示の場合は検索せず、セレクトボックスだけ表示する
        if (isEmpty(f1) && isEmpty(f2) && isEmpty(f3) && isEmpty(f4)) {
            return "test_regist.jsp";
        }

        Map<String, String> errors = new HashMap<>();

        if (isEmpty(f1) || isEmpty(f2) || isEmpty(f3) || isEmpty(f4)) {
            errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
            request.setAttribute("errors", errors);
            return "test_regist.jsp";
        }

        int entYear = Integer.parseInt(f1);
        int no = Integer.parseInt(f4);
        Subject subject = findSubject(subjects, f3);

        if (subject == null) {
            errors.put("filter", "指定された科目が見つかりません");
            request.setAttribute("errors", errors);
            return "test_regist.jsp";
        }

        // 入学年度・クラス・科目で学生一覧を取得する。
        // 点数表示は選択された回数だけを取り出して pointMap に入れる。
        List<TestListSubject> tests = testListSubjectDao.filter(entYear, f2, subject, school);
        Map<String, String> pointMap = createPointMap(tests, no);

        request.setAttribute("tests", tests);
        request.setAttribute("pointMap", pointMap);
        request.setAttribute("subject", subject);
        request.setAttribute("no", no);
        request.setAttribute("searched", true);

        return "test_regist.jsp";
    }

    private List<Integer> createEntYearList() {
        List<Integer> list = new ArrayList<>();
        int year = LocalDate.now().getYear();

        for (int i = year - 10; i <= year; i++) {
            list.add(i);
        }
        return list;
    }

    private List<Integer> createNoList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    private Subject findSubject(List<Subject> subjects, String cd) {
        if (subjects == null || cd == null) {
            return null;
        }

        for (Subject subject : subjects) {
            if (cd.equals(subject.getCd())) {
                return subject;
            }
        }
        return null;
    }

    private Map<String, String> createPointMap(List<TestListSubject> tests, int no) {
        Map<String, String> map = new HashMap<>();

        if (tests == null) {
            return map;
        }

        for (TestListSubject test : tests) {
            Integer point = test.getPoints().get(no);
            map.put(test.getStudentNo(), point == null ? "" : String.valueOf(point));
        }

        return map;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
