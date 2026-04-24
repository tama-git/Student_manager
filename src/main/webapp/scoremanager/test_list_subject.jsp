<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
            
            <%-- 検索フィルターエリア --%>
            <div class="border mx-3 mb-3 p-3 rounded" id="filter-container">
                <%-- 科目情報による検索 --%>
                <form method="get" action="TestListSubjectExecute.action" class="row border-bottom mb-3 pb-3 align-items-end">
                    <div class="col-1 fw-bold pb-2">科目情報</div>
                    <div class="col-2">
                        <label class="form-label small">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">---------</option>
                            <c:forEach var="year" items="${yearList}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label small">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">---------</option>
                            <c:forEach var="num" items="${classNum}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label small">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">---------</option>
                            <c:forEach var="subj" items="${subjects}">
                                <option value="${subj.cd}" <c:if test="${subj.cd == f3}">selected</c:if>>${subj.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100">検索</button>
                    </div>
                </form>

                <%-- 学生情報による検索 --%>
                <form method="get" action="TestListStudentExecute.action" class="row align-items-end">
                    <div class="col-1 fw-bold pb-2">学生情報</div>
                    <div class="col-9">
                        <label class="form-label small">学生番号</label>
                        <input type="text" class="form-control" name="f4" placeholder="学生番号を入力してください" value="${f4}">
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100">検索</button>
                    </div>
                </form>
            </div>

            <%-- 成績一覧表示エリア --%>
            <c:choose>
                <c:when test="${not empty tests}">
                    <%-- 選択された科目名を表示 --%>
                    <div class="ms-3 mb-2">
                        <p class="mb-0">科目：${tests[0].subject.name}</p>
                    </div>

                    <table class="table table-hover mx-3">
                        <thead>
                            <tr class="table-light">
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>1回</th>
                                <th>2回</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <%-- ③ 入学年度 --%>
                                    <td>${test.student.entYear}</td>
                                    <%-- ④ クラス --%>
                                    <td>${test.classNum}</td>
                                    <%-- ⑤ 学生番号 --%>
                                    <td>${test.student.no}</td>
                                    <%-- ⑥ 氏名 --%>
                                    <td>${test.student.name}</td>
                                    
                                    <%-- ⑦ 1回目の点数 --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${test.no == 1}">${test.point}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <%-- ⑧ 2回目の点数 --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${test.no == 2}">${test.point}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <%-- 検索結果が空の場合 --%>
                <c:otherwise>
                    <div class="alert alert-info mx-3 mt-3">成績情報が存在しません。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>