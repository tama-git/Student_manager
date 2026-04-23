<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目削除</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mx-4">${error}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty subject}">
                    <div class="card mx-4">
                        <div class="card-body">
                            <p class="mb-4">以下の科目を削除します。よろしいですか？</p>

                            <table class="table table-bordered align-middle">
                                <tr>
                                    <th style="width: 180px;" class="table-light">科目コード</th>
                                    <td>${subject.cd}</td>
                                </tr>
                                <tr>
                                    <th class="table-light">科目名</th>
                                    <td>${subject.name}</td>
                                </tr>
                            </table>

                            <form action="SubjectDeleteExecute.action" method="post" class="mt-4">
                                <input type="hidden" name="cd" value="${subject.cd}">
                                <button type="submit" class="btn btn-danger">削除</button>
                                <a href="SubjectList.action" class="btn btn-secondary ms-2">戻る</a>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning mx-4">削除する科目情報が見つかりませんでした。</div>
                    <div class="mx-4">
                        <a href="SubjectList.action" class="btn btn-secondary">科目一覧へ</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
