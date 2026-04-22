<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生登録</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生登録</h2>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-3">
                    <ul class="mb-0">
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="StudentCreateExecute.action" method="post">
                <div class="border mx-3 mb-3 py-4 px-4 rounded bg-white">

                    <div class="mb-3">
                        <label class="form-label" for="ent_year">入学年度</label>
                        <select class="form-select" id="ent_year" name="entYear" required>
                            <option value="">--------</option>
                            <c:forEach var="year" items="${yearList}">
                                <option value="${year}" <c:if test="${year == entYear}">selected</c:if>>
                                    ${year}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="no">学生番号</label>
                        <input class="form-control" type="text" id="no" name="no"
                               value="${no}" maxlength="10" required
                               placeholder="学生番号を入力してください">
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="name">氏名</label>
                        <input class="form-control" type="text" id="name" name="name"
                               value="${name}" maxlength="30" required
                               placeholder="氏名を入力してください">
                    </div>

                    <div class="mb-4">
                        <label class="form-label" for="class_num">クラス</label>
                        <select class="form-select" id="class_num" name="classNum" required>
                            <option value="">--------</option>
                            <c:forEach var="classItem" items="${classList}">
    							<option value="${classItem}"
        							<c:if test="${classItem == classNum}">selected</c:if>>
        								${classItem}
    							</option>
							</c:forEach>
                        </select>
                    </div>

                    <div class="d-flex gap-2">
                        <button class="btn btn-primary" type="submit" name="end" value="true">
                            登録して終了
                        </button>
                        <a href="StudentList.action" class="btn btn-secondary">戻る</a>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>