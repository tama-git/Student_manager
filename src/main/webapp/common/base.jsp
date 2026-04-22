<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>${param.title} - 得点管理システム</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
/* 必要に応じて追加のスタイル */
.mx2 { margin-left: 0.5rem; margin-right: 0.5rem; }
</style>
${param.scripts}
</head>
<body class="bg-light">
 
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">得点管理システム</a>
            
            <%-- ログインしている場合のみメニューボタン、ユーザー名、ログアウトボタンを表示 --%>
            <c:if test="${not empty user}">
                <div class="d-flex align-items-center text-white">
                    <a href="menu.jsp" class="btn btn-secondary btn-sm me-3">メニュー</a>
                    
                    <span class="me-3">${user.name}</span>
                    <a href="Logout.action" class="btn btn-outline-light btn-sm">ログアウト</a>
                </div>
            </c:if>
        </div>
    </nav>
 
	<div class="container-fluid">
        <div class="row">
            <main class="col">
                ${param.content}
            </main>
        </div>
    </div>
 
	<footer class="footer mt-auto py-3 bg-white text-center shadow-sm">
        <div class="container">
            <span class="text-muted">© 2026 得点管理システム</span>
        </div>
    </footer>
 
</body>
</html>