<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ログイン" />
    
    <c:param name="content">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white text-center">
                        <h4 class="mb-0">ログイン</h4>
                    </div>
                    <div class="card-body">
                        
                        <%-- LoginExecuteActionから戻ってきた際のエラー表示 --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <%-- 【修正ポイント】 action を LoginExecute.action に変更 --%>
                        <form action="LoginExecute.action" method="post">
                            <div class="mb-3">
                                <label for="login" class="form-label">教員ID</label>
                                <input type="text" class="form-control" id="login" name="login" 
                                       placeholder="教員IDを入力してください" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">パスワード</label>
                                <input type="password" class="form-control" id="password" name="password" 
                                       placeholder="パスワードを入力してください" required>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">ログイン</button>
                            </div>
                        </form>
                        
                    </div>
                </div>
            </div>
        </div>
    </c:param>
</c:import>