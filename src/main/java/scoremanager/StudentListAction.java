package scoremanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String isAttendStr = request.getParameter("f3");
        
        int entYear = 0;
        boolean isAttend = (isAttendStr != null);
        List<Student> students = null;

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        StudentDAO sDao = new StudentDAO();
        ClassNumDAO cNumDao = new ClassNumDAO();
        Map<String, String> errors = new HashMap<>();

        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        List<String> list = cNumDao.filter(teacher.getSchool());

        if (entYear != 0 && classNum != null && !classNum.equals("0")) {
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear != 0 && (classNum == null || classNum.equals("0"))) {
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
            students = sDao.filter(teacher.getSchool(), isAttend);
        } else {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            students = sDao.filter(teacher.getSchool(), isAttend);
        }

        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);

        if (isAttendStr != null) {
            request.setAttribute("f3", isAttendStr);
        }

        request.setAttribute("students", students);
        request.setAttribute("class_num_set", list);
        request.setAttribute("ent_year_set", entYearSet);

        return "student_list.jsp";
    }
}