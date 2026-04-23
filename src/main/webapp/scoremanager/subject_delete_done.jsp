<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目削除完了</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除完了</h2>

            <div class="card mx-4">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${result}">
                            <div class="alert alert-success">科目情報を削除しました。</div>
                            <p class="mb-1">科目コード：${subject.cd}</p>
                            <p class="mb-4">科目名：${subject.name}</p>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger">科目情報の削除に失敗しました。</div>
                        </c:otherwise>
                    </c:choose>

                    <a href="SubjectList.action" class="btn btn-primary">科目一覧へ戻る</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>
