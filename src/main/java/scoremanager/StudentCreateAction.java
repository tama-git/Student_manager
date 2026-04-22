package scoremanager;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classList = cNumDao.filter(teacher.getSchool());

        request.setAttribute("yearList", createYearList());
        request.setAttribute("classList", classList);

        return "student_create.jsp";
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