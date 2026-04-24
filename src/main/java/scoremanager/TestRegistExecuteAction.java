package scoremanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import dao.TestListSubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Teacher teacher = (Teacher) request.getSession().getAttribute("user");
    	School school = teacher.getSchool();

        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        TestListSubjectDAO testListSubjectDao = new TestListSubjectDAO();
        TestDAO testDao = new TestDAO();

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

        Map<String, String> errors = new HashMap<>();
        Map<String, String> pointErrors = new HashMap<>();
        Map<String, String> pointMap = new HashMap<>();

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

        // 再取得して、画面に表示されていた学生分だけ保存対象にする
        List<TestListSubject> tests = testListSubjectDao.filter(entYear, f2, subject, school);
        List<Test> saveList = new ArrayList<>();

        for (TestListSubject row : tests) {
            String studentNo = row.getStudentNo();
            String pointText = request.getParameter("point_" + studentNo);

            if (pointText != null) {
                pointText = pointText.trim();
            }

            pointMap.put(studentNo, pointText == null ? "" : pointText);

            // 空欄の場合、その学生の成績レコードは保存しない
            if (isEmpty(pointText)) {
                continue;
            }

            int point;
            try {
                point = Integer.parseInt(pointText);
            } catch (NumberFormatException e) {
                pointErrors.put(studentNo, "0～100の範囲で入力してください");
                continue;
            }

            if (point < 0 || point > 100) {
                pointErrors.put(studentNo, "0～100の範囲で入力してください");
                continue;
            }

            Student student = new Student();
            student.setNo(studentNo);
            student.setName(row.getStudentName());
            student.setEntYear(row.getEntYear());
            student.setClassNum(row.getClassNum());
            student.setSchool(school);

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(no);
            test.setPoint(point);
            test.setClassNum(row.getClassNum());

            saveList.add(test);
        }

        if (!pointErrors.isEmpty()) {
            request.setAttribute("tests", tests);
            request.setAttribute("pointMap", pointMap);
            request.setAttribute("pointErrors", pointErrors);
            request.setAttribute("subject", subject);
            request.setAttribute("no", no);
            request.setAttribute("searched", true);
            return "test_regist.jsp";
        }

        if (!saveList.isEmpty()) {
            boolean result = testDao.save(saveList);
            if (!result) {
                errors.put("save", "成績の登録に失敗しました");
                request.setAttribute("tests", tests);
                request.setAttribute("pointMap", pointMap);
                request.setAttribute("errors", errors);
                request.setAttribute("subject", subject);
                request.setAttribute("no", no);
                request.setAttribute("searched", true);
                return "test_regist.jsp";
            }
        }

        
        return "test_regist_done.jsp";
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

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
