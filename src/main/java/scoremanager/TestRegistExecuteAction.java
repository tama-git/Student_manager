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

        // セッションからログインユーザー情報を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        School school = teacher.getSchool();

        // 各種DAOの準備
        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();
        TestListSubjectDAO testListSubjectDao = new TestListSubjectDAO();
        TestDAO testDao = new TestDAO();

        // ① 画面表示用のリスト（プルダウン等）を準備
        List<Integer> entYearSet = createEntYearList();
        List<String> classNumSet = classNumDao.filter(school);
        List<Subject> subjects = subjectDao.filter(school);

        request.setAttribute("entYearSet", entYearSet);
        request.setAttribute("classNumSet", classNumSet);
        request.setAttribute("subjects", subjects);
        request.setAttribute("noSet", createNoList());

        // ② リクエストパラメータの取得
        String f1 = request.getParameter("f1"); // 入学年度
        String f2 = request.getParameter("f2"); // クラス
        String f3 = request.getParameter("f3"); // 科目コード
        String f4 = request.getParameter("f4"); // 回数

        // JSPに値を戻すためセット
        request.setAttribute("f1", f1);
        request.setAttribute("f2", f2);
        request.setAttribute("f3", f3);
        request.setAttribute("f4", f4);

        Map<String, String> errors = new HashMap<>();
        Map<String, String> pointErrors = new HashMap<>();
        Map<String, String> pointMap = new HashMap<>();

        // 必須チェック
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

        // ③ 成績データの処理
        // 画面に表示されている学生リストを取得（このリストをもとに点数を紐づける）
        List<TestListSubject> tests = testListSubjectDao.filter(entYear, f2, subject, school);
        List<Test> saveList = new ArrayList<>();

        for (TestListSubject row : tests) {
            String studentNo = row.getStudentNo();
            // JSP側の input name="point_${row.studentNo}" から値を取得
            String pointText = request.getParameter("point_" + studentNo);

            if (pointText != null) {
                pointText = pointText.trim();
            }

            // 再表示用に現在の入力値を保持
            pointMap.put(studentNo, pointText == null ? "" : pointText);

            // 点数が未入力（空欄）の場合は、その学生の成績は更新・登録対象から外す
            if (isEmpty(pointText)) {
                continue;
            }

            // 数値チェック
            int point;
            try {
                point = Integer.parseInt(pointText);
            } catch (NumberFormatException e) {
                pointErrors.put(studentNo, "0～100の範囲で入力してください");
                continue;
            }

            // 範囲チェック
            if (point < 0 || point > 100) {
                pointErrors.put(studentNo, "0～100の範囲で入力してください");
                continue;
            }

            // 保存用 Test オブジェクトの組み立て
            Student student = new Student();
            student.setNo(studentNo);
            student.setSchool(school); // 学生に学校をセット

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(no);
            test.setPoint(point);
            test.setClassNum(row.getClassNum()); // クラス番号も保持

            saveList.add(test);
        }

        // 入力エラーがある場合は、保存せずに元の画面に戻る
        if (!pointErrors.isEmpty()) {
            request.setAttribute("tests", tests);
            request.setAttribute("pointMap", pointMap);
            request.setAttribute("pointErrors", pointErrors);
            request.setAttribute("subject", subject); // 選択中の科目を保持
            request.setAttribute("no", no);           // 選択中の回数を保持
            request.setAttribute("searched", true);   // 検索後のフラグ
            return "test_regist.jsp";
        }

        // ④ 保存実行
        if (!saveList.isEmpty()) {
            boolean result = testDao.save(saveList);
            if (!result) {
                // DB保存失敗時の処理
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

        // ⑤ 完了画面へ
        return "test_regist_done.jsp";
    }

    // 入学年度リスト作成（現在から10年前まで）
    private List<Integer> createEntYearList() {
        List<Integer> list = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) {
            list.add(i);
        }
        return list;
    }

    // 回数リスト作成（1回、2回）
    private List<Integer> createNoList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    // コードから科目オブジェクトを探す
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

    // 文字列が空かチェック
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}