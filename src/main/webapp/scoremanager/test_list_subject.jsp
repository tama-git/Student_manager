<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧(科目)</h2>
            
            <div class="border mx-3 mb-3 p-3 rounded" id="filter-container">
                <%-- 上段：科目情報による検索 --%>
                <%-- 識別パラメータ f=sj を付与して送信 --%>
                <form method="get" action="TestList.action" class="row border-bottom mb-3 pb-3 align-items-end">
                    <input type="hidden" name="f" value="sj">
                    <div class="col-1 fw-bold pb-2">科目情報</div>
                    
                    <div class="col-2">
                        <label class="form-label small" for="ent-year-select">入学年度</label>
                        <select class="form-select" id="ent-year-select" name="f1">
                            <option value="0">---------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="col-2">
                        <label class="form-label small" for="class-select">クラス</label>
                        <select class="form-select" id="class-select" name="f2">
                            <option value="0">---------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="col-4">
                        <label class="form-label small" for="subject-select">科目</label>
                        <select class="form-select" id="subject-select" name="f3">
                            <option value="0">---------</option>
                            <%-- 全件リスト（subjects）を使ってループ --%>
                            <c:forEach var="subj" items="${subjects}">
                                <option value="${subj.cd}" <c:if test="${subj.cd == f3}">selected</c:if>>${subj.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" id="filter-button-subject">検索</button>
                    </div>
                </form>
                
                <%-- 下段：学生情報による検索 --%>
                <%-- 識別パラメータ f=st を付与して送信 --%>
                <form method="get" action="TestList.action" class="row align-items-end">
                    <input type="hidden" name="f" value="st">
                    <div class="col-1 fw-bold pb-2">学生情報</div>
                    
                    <div class="col-9">
                        <label class="form-label small" for="student-no-input">学生番号</label>
                        <input type="text" class="form-control" id="student-no-input" name="f4" 
                               placeholder="学生番号を入力してください" value="${f4}">
                    </div>
                    
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" id="filter-button-student">検索</button>
                    </div>
                </form>
            </div>

            <%-- 成績結果の表示エリア --%>
            <c:choose>
                <c:when test="${not empty tests}">
                    <table class="table table-hover mt-3">
                        <thead>
                            <tr class="table-light">
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>1回の点数</th>
                                <th>2回の点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <%-- 要件: 入学年度を表示 --%>
                                    <td>${test.student.entYear}</td>
                                    <%-- 要件: クラス番号を表示 --%>
                                    <td>${test.classNum}</td>
                                    <%-- 要件: 学生番号を表示 --%>
                                    <td>${test.student.no}</td>
                                    <%-- 要件: 学生氏名を表示 --%>
                                    <td>${test.student.name}</td>
                                    <%-- 要件: 回数1の得点を表示。空なら - --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${test.no1}">${test.point}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <%-- 要件: 回数2の得点を表示。空なら - --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${test.no2}">${test.point}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${not empty f}">
                    <%-- 検索を実行した結果、0件だった場合 --%>
                    <div class="alert alert-info mt-3">成績情報が存在しません。</div>
                </c:when>
            </c:choose>
        </section>
    </c:param>
</c:import>