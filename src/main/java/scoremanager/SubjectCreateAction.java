package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // メソッド内でフォワードするのではなく、JSPのファイル名を戻り値として返す
        return "subject_create.jsp";
    }
}