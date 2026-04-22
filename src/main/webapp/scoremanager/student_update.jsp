<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

            <%-- 【重要】action名が FrontController の設定と一致しているか確認 --%>
            <form action="StudentUpdateExecute.action" method="post">
                <div class="border mx-3 mb-3 py-4 px-4 rounded bg-white">
                    <div class="mb-3">
                        <label class="form-label">入学年度</label>
                        <div class="form-control-plaintext px-2">${student.entYear}</div>
                        <input type="hidden" name="entYear" value="${student.entYear}">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">学生番号</label>
                        <input class="form-control bg-light" type="text" name="no" value="${student.no}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">氏名</label>
                        <input class="form-control" type="text" name="name" value="${student.name}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="classNum">
                            <c:forEach var="item" items="${classList}">
                                <option value="${item}" <c:if test="${item == student.classNum}">selected</c:if>>${item}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-4 form-check">
                        <input class="form-check-input" type="checkbox" name="isAttend" id="isAttend"
                            <c:if test="${student.isAttend()}">checked</c:if>>
                        <label class="form-check-label" for="isAttend">在学中</label>
                    </div>

                    <div class="d-flex gap-2">
                        <button class="btn btn-primary" type="submit">変更を保存する</button>
                        <a href="StudentList.action" class="btn btn-secondary">戻る</a>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>