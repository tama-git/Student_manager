package scoremanager;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @SuppressWarnings("unused")
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // セッションからログインユーザー（教員）の情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータの取得
        String cd = request.getParameter("cd");     // 科目コード
        String name = request.getParameter("name"); // 科目名

        // 入力値をリクエスト属性にセット（エラー時に値を保持するため）
        request.setAttribute("cd", cd);
        request.setAttribute("name", name);

        List<String> errors = new ArrayList<>();
        SubjectDAO dao = new SubjectDAO();

        // --- バリデーションチェック ---
        
        // 科目コードのチェック
        if (cd == null || cd.isBlank()) {
            errors.add("科目コードを入力してください。");
        } else if (cd.length() != 3) {
            errors.add("科目コードは3文字で入力してください。");
        }

        // 科目名のチェック
        if (name == null || name.isBlank()) {
            errors.add("科目名を入力してください。");
        } else if (name.length() > 10) {
            errors.add("科目名は10文字以内で入力してください。");
        }

        // 重複チェック（コードと名称が未入力でない場合のみ実行）
        if (errors.isEmpty()) {
            School school = teacher.getSchool();
            // 指定した学校に同じ科目コードが既に存在するか確認
            if (dao.get(cd) != null) {
                errors.add("科目コードが重複しています。");
            }
        }

        // エラーがある場合は登録画面へ戻る
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return "subject_create.jsp";
        }

        // --- 登録処理 ---
        
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool()); // 先生の所属学校をセット

        boolean result = dao.save(subject);
        request.setAttribute("result", result);

        // 完了画面へ
        return "subject_create_done.jsp";
    }
}