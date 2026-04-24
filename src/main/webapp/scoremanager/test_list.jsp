<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
            
            <div class="border mx-3 mb-3 p-3 rounded" id="filter-container">
                
                <%-- 上段：科目情報による検索 --%>
                <form method="get" action="TestListSubjectExecute.action" class="row border-bottom mb-3 pb-3 align-items-end">
                    <div class="col-1 fw-bold pb-2">
                        科目情報
                    </div>
                    <div class="col-2">
                        <label class="form-label small" for="ent-year-select">入学年度</label>
                        <select class="form-select" id="ent-year-select" name="f1">
                            <option value="0">---------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label small" for="class-select">クラス</label>
                        <select class="form-select" id="class-select" name="f2">
                            <option value="0">---------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label small" for="subject-select">科目</label>
                        <select class="form-select" id="subject-select" name="f3">
                            <option value="0">---------</option>
                            <%-- itemsをsubjectからsubjects(複数形)に変更を想定 --%>
                            <c:forEach var="subj" items="${subjects}">
                                <option value="${subj.cd}" <c:if test="${subj.cd==f3}">selected</c:if>>${subj.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-1">
                        <label class="form-label small" for="no-select">回数</label>
                        <select class="form-select" id="no-select" name="f4">
                            <option value="0">----</option>
                            <c:forEach var="i" begin="1" end="2">
                                <option value="${i}" <c:if test="${i==f4}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" id="filter-button-subject">検索</button>
                    </div>
                </form>
                
                <%-- 下段：学生情報による検索 --%>
                <form method="get" action="TestListStudentExecute.action" class="row align-items-end">
                    <div class="col-1 fw-bold pb-2">
                        学生情報
                    </div>
                    <div class="col-9">
                        <label class="form-label small" for="student-no-input">学生番号</label>
                        <input type="text" class="form-control" id="student-no-input" name="f10" 
                               placeholder="学生番号を入力してください" value="${f10}">
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" id="filter-button-student">検索</button>
                    </div>
                </form>
            </div>

            <%-- ガイドメッセージ --%>
            <div class="mx-4 mt-2">
                <p class="text-info small">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
            </div>


        </section>
    </c:param>
</c:import>