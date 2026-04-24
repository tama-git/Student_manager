<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-3">
                    <ul class="mb-0">
                        <c:forEach var="error" items="${errors}">
                            <li>${error.value}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="TestRegist.action" method="get">
                <div class="border mx-3 mb-3 py-3 px-4 rounded bg-white">
                    <div class="row align-items-end">
                        <div class="col-md-3 mb-3">
                            <label class="form-label" for="f1">入学年度</label>
                            <select class="form-select" name="f1" id="f1">
                                <option value="">--------</option>
                                <c:forEach var="year" items="${entYearSet}">
                                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-3 mb-3">
                            <label class="form-label" for="f2">クラス</label>
                            <select class="form-select" name="f2" id="f2">
                                <option value="">--------</option>
                                <c:forEach var="classNum" items="${classNumSet}">
                                    <option value="${classNum}" <c:if test="${classNum == f2}">selected</c:if>>${classNum}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-4 mb-3">
                            <label class="form-label" for="f3">科目</label>
                            <select class="form-select" name="f3" id="f3">
                                <option value="">--------</option>
                                <c:forEach var="subject" items="${subjects}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-1 mb-3">
                            <label class="form-label" for="f4">回数</label>
                            <select class="form-select" name="f4" id="f4">
                                <option value="">--</option>
                                <c:forEach var="num" items="${noSet}">
                                    <option value="${num}" <c:if test="${num == f4}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-1 mb-3">
                            <button class="btn btn-secondary" type="submit">検索</button>
                        </div>
                    </div>
                </div>
            </form>

            <c:if test="${searched}">
                <div class="mx-3 mb-2">
                    科目：${subject.name}（${no}回）
                </div>

                <c:choose>
                    <c:when test="${empty tests}">
                        <div class="mx-3 text-danger">学生情報が存在しませんでした</div>
                    </c:when>

                    <c:otherwise>
                        <form action="TestRegistExecute.action" method="post">
                            <input type="hidden" name="f1" value="${f1}">
                            <input type="hidden" name="f2" value="${f2}">
                            <input type="hidden" name="f3" value="${f3}">
                            <input type="hidden" name="f4" value="${f4}">

                            <div class="mx-3 table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>入学年度</th>
                                            <th>クラス</th>
                                            <th>学生番号</th>
                                            <th>氏名</th>
                                            <th>点数</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="test" items="${tests}">
                                            <tr>
                                                <td>${test.entYear}</td>
                                                <td>${test.classNum}</td>
                                                <td>${test.studentNo}</td>
                                                <td>${test.studentName}</td>
                                                <td>
                                                    <input type="text"
                                                           class="form-control w-50"
                                                           name="point_${test.studentNo}"
                                                           value="${pointMap[test.studentNo]}">
                                                    <c:if test="${not empty pointErrors[test.studentNo]}">
                                                        <div class="text-warning small mt-1">
                                                            ${pointErrors[test.studentNo]}
                                                        </div>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="mx-3 mt-3">
                                <button class="btn btn-secondary" type="submit">登録して終了</button>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </section>
    </c:param>
</c:import>
