package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action; // 自作のActionクラスをインポート

public class MenuAction extends Action { // HttpServletではなくActionを継承

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//  menu.jspを表示する（ファイル名だけを返すのがこのシステムのルール）
		return "menu.jsp";
	}
}