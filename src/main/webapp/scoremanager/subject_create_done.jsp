<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目登録完了</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録完了</h2>

            <div class="border mx-3 mb-3 py-4 px-4 rounded bg-white">
                <p>科目情報の登録が完了しました。</p>

                <div class="d-flex gap-2">
                    <a href="SubjectCreate.action" class="btn btn-primary">戻る</a>
                    <a href="SubjectList.action" class="btn btn-secondary">学生一覧</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>