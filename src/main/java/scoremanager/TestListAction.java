package scoremanager;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        
        // クラスデータ
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNum = cNumDao.filter(teacher.getSchool());
        
        // 科目データ
        SubjectDAO sDao = new SubjectDAO();
        List<Subject> subjects = sDao.filter(teacher.getSchool());
        

        

     	// JSPに渡すデータをセット
        request.setAttribute("yearList", createYearList());
     	request.setAttribute("subjects", subjects);
     	request.setAttribute("classNum", classNum);

        return "test_list.jsp";
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