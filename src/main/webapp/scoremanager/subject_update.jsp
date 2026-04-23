<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

            <form action="SubjectUpdateExecute.action" method="post">
                <div class="border mx-3 mb-3 py-4 px-4 rounded bg-white">
                    
                    <%-- 学校コード：表示のみ --%>
                    <div class="mb-3">
                        <label class="form-label">学校コード</label>
                        <div class="form-control-plaintext px-2">${subject.school.cd}</div>
                    </div>

                    <%-- 科目コード：表示用 --%>
					<div class="mb-3">
    					<label class="form-label">科目コード</label>
    					<div class="form-control-plaintext px-2">${subject.cd}</div>
    
    					<%-- 【重要】この一行を追加！サーバーに値を送るための隠しフィールド --%>
    					<input type="hidden" name="cd" value="${subject.cd}">
					</div>

                    <%-- 科目名：編集可能 --%>
                    <div class="mb-3">
                        <label class="form-label" for="name">科目名</label>
                        <input class="form-control" type="text" name="name" id="name" 
                               value="${subject.name}" maxlength="20" pattern="^[^0-20]+$" 
                               placeholder="20文字以内で入力してください" required>
                        <div class="form-text">数字は入力できません。</div>
                    </div>

                    <div class="d-flex gap-2 mt-4">
                        <button class="btn btn-primary" type="submit">変更を保存する</button>
                        <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>