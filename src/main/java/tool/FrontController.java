package tool;
 
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 
@WebServlet(urlPatterns={"*.action"})
public class FrontController extends HttpServlet {
 
	public void doPost(
			HttpServletRequest request, HttpServletResponse response
		) throws ServletException, IOException {
			PrintWriter out=response.getWriter();
			try {
				// ログインチェック
				HttpSession session = request.getSession();
				String path = request.getServletPath();
				// ログイン画面（Login.actionやLoginExecute.action）以外へのアクセス時にチェック
				if (session.getAttribute("user") == null && !path.contains("Login")) {
					// 未ログインならログイン画面へ強制遷移
					request.getRequestDispatcher("/scoremanager/login.jsp").forward(request, response);
					return; // 処理を中断
				}
 
				String servletPath = path.substring(1); // 変数名が重複しないよう調整
				String name = servletPath.replace(".a", "A").replace('/', '.');
				Action action = (Action)Class.forName(name).
					getDeclaredConstructor().newInstance();
				String url = action.execute(request, response);
				request.getRequestDispatcher(url).
					forward(request, response);
			} catch (Exception e) {
				e.printStackTrace(out);
			}
		}
 
	public void doGet(
		HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		doPost(request, response);
	}
}