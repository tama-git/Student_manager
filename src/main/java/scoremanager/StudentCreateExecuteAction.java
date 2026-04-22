package scoremanager;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");

        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classList = cNumDao.filter(teacher.getSchool());

        request.setAttribute("yearList", createYearList());
        request.setAttribute("classList", classList);
        request.setAttribute("no", no);
        request.setAttribute("name", name);
        request.setAttribute("classNum", classNum);

        List<String> errors = new ArrayList<>();

        if (entYearStr == null || entYearStr.isBlank()) {
            errors.add("入学年度を選択してください。");
        }
        if (no == null || no.isBlank()) {
            errors.add("学生番号を入力してください。");
        }
        if (name == null || name.isBlank()) {
            errors.add("氏名を入力してください。");
        }
        if (classNum == null || classNum.isBlank()) {
            errors.add("クラスを選択してください。");
        }

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isBlank()) {
            entYear = Integer.parseInt(entYearStr);
            request.setAttribute("entYear", entYear);
        }

        StudentDAO dao = new StudentDAO();
        if (errors.isEmpty() && dao.get(no) != null) {
            errors.add("学生番号が重複しています。");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return "student_create.jsp";
        }

        School school = teacher.getSchool();

        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchool(school);


        if (no == null || no.isBlank()) { errors.add("学生番号を入力してください。"); }

        // 重複チェックをここに入れる
        if (errors.isEmpty()) {
        	if (dao.get(no) != null) {
        		errors.add("学生番号が重複しています。");
        	}
        }

        // もしエラーが1つでもあれば、ここで処理を中断して JSP へ戻る
        if (!errors.isEmpty()) {
        	request.setAttribute("errors", errors);
        	request.setAttribute("no", no);
        	request.setAttribute("name", name);

        	return "student_create.jsp"; // 登録画面のファイル名
        }
  
        boolean result = dao.save(student);
        request.setAttribute("result", result);

        return "student_create_done.jsp";
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