<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目登録</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-3">
                    <ul class="mb-0">
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="SubjectCreateExecute.action" method="post">
                <div class="border mx-3 mb-3 py-4 px-4 rounded bg-white">

                    <div class="mb-3">
                        <label class="form-label" for="cd">科目コード(3文字以内の数字)</label>
                        	<input type="text" 
           						class="form-control" 
          						name="cd" 
           						id="cd" 
           						pattern="\d{3}" 
           						maxlength="3"
           						placeholder="3文字で入力してください"
           						required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label" for="name">科目名</label>
                        	<input type="text" 
           						class="form-control" 
          						name="name" 
           						id="name" 
           						pattern="^[^0-20]+$"
           						maxlength="20"
           						placeholder="20文字以内で入力してください"
           						required>
                    </div>
                    

                    

                    <div class="d-flex gap-2">
                        <button class="btn btn-primary" type="submit" name="end" value="true">
                            登録
                        </button>
                        <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>