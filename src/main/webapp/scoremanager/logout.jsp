<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ログアウト" />
    <c:param name="content">
        <div class="text-center mt-5">
            <h3>ログアウトしました</h3>
            <p class="text-muted">ご利用ありがとうございました。</p>
            <a href="Login.action" class="btn btn-primary mt-3">ログイン画面へ戻る</a>
        </div>
    </c:param>
</c:import>