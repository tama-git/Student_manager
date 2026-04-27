<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- タイトル：成績参照 --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
            
            <%-- エラー表示エリア --%>
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-3">
                    <c:forEach var="error" items="${errors}">
                        <div>${error}</div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="border mx-3 mb-3 p-3 rounded" id="filter-container">
                <%-- 上段：科目情報による検索 --%>
                <form method="get" action="TestListSubjectExecute.action" class="row border-bottom mb-3 pb-3 align-items-end">
                    <div class="col-1 fw-bold pb-2">科目情報</div>
                    <div class="col-2">
                        <label class="form-label small" for="ent-year-select">入学年度</label>
                        <select class="form-select" id="ent-year-select" name="f1">
                            <option value="0">---------</option>
                            <c:forEach var="year" items="${yearList}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label small" for="class-select">クラス</label>
                        <select class="form-select" id="class-select" name="f2">
                            <option value="0">---------</option>
                            <c:forEach var="num" items="${classNum}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label small" for="subject-select">科目</label>
                        <select class="form-select" id="subject-select" name="f3">
                            <option value="0">---------</option>
                            <c:forEach var="subj" items="${subjects}">
                                <option value="${subj.cd}" <c:if test="${subj.cd == f3}">selected</c:if>>${subj.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" type="submit">検索</button>
                    </div>
                </form>
                
                <%-- 下段：学生情報による検索 --%>
                <form method="get" action="TestListStudentExecute.action" class="row align-items-end">
                    <div class="col-1 fw-bold pb-2">学生情報</div>
                    <div class="col-9">
                        <label class="form-label small" for="student-no-input">学生番号</label>
                        <input type="text" class="form-control" id="student-no-input" name="f10" 
                               placeholder="学生番号を入力してください" value="${f10}">
                    </div>
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary w-100" type="submit">検索</button>
                    </div>
                </form>
            </div>

            <%-- ガイドメッセージ（検索結果がない時のみ表示） --%>
            <c:if test="${empty tests && empty errors}">
                <div class="mx-4 mt-2">
                    <p class="text-info small">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
                </div>
            </c:if>

            <%-- 学生検索結果の表示エリア（画像のレイアウトを再現） --%>
            <c:if test="${not empty tests}">
                <div class="mx-3 mt-4">
                    <%-- 氏名表示部分 --%>
                    <div class="mb-3 fw-bold">
                        氏名：${tests[0].student.name} (${tests[0].student.no})
                    </div>
                    
                    <%-- 成績一覧テーブル --%>
                    <table class="table table-hover mt-2">
                        <thead>
                            <tr class="border-bottom border-dark">
                                <th>科目名</th>
                                <th>科目コード</th>
                                <th>回数</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <td>${test.subject.name}</td>
                                    <td>${test.subject.cd}</td>
                                    <td>${test.no}</td>
                                    <td>${test.point}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

        </section>
    </c:param>
</c:import>