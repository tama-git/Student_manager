<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">変更完了</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
            <div class="mx-3">
                <p>学生情報の変更が完了しました。</p>
                <a href="StudentList.action" class="btn btn-primary">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>