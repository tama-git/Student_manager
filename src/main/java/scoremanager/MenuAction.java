package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action; // 自作のActionクラスをインポート

public class MenuAction extends Action { // HttpServletではなくActionを継承

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. セッションの確認
		HttpSession session = request.getSession(); // getSession(false)ではなく通常通りでOK


		// 2. menu.jspを表示する（ファイル名だけを返すのがこのシステムのルール）
		return "menu.jsp";
	}
}