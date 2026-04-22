package scoremanager;

import bean.Teacher;
import dao.TeacherDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        // JSPのname属性（login）に合わせる
        String id = request.getParameter("login");
        String password = request.getParameter("password");

        TeacherDAO dao = new TeacherDAO();
        Teacher teacher = dao.login(id, password);

        if (teacher != null) {
            // 認証成功
            session.setAttribute("user", teacher);
            return "Menu.action";
        }
        
        // 認証失敗
        request.setAttribute("error", "IDまたはパスワードが間違っています");
        return "login.jsp";
    }
}