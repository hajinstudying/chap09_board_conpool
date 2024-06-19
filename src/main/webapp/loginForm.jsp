<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginForm - servlet board</title>
<link rel="stylesheet" type="text/css" href="css/board.css" />
</head>
<body>
	<div class="container">
		<header class="header">
			<nav>
				<a href="${contextPath}/boardList">회원제게시판</a>
			</nav>
			<div class="user-info">
				<c:if test="${not empty sessionScope.member}">
					<p>${sessionScope.member.name}님</p>
					<a href="${contextPath}/logout">Logout</a>
				</c:if>
				<c:if test="${empty sessionScope.member}">
					<a href="${contextPath}/login">Login</a>
				</c:if>
			</div>
		</header>
		<main>
			<h2>로그인</h2>
			<form action="${contextPath}/login" method="post">
				<div>
					<label for="memberId">아이디:</label>
					<input type="text" id="memberId" name="memberId" required>
				</div>
				<div>
					<label for="password">비밀번호:</label>
					<input type="password" id="password" name="password" required>
				</div>
				<div>
					<input type="submit" value="로그인">
					<input type="reset" value="다시쓰기">
				</div>
				<div>
					<a href="${contextPath}/member">회원가입</a>
				</div>
			</form>
			<%-- 오류 메세지 출력 --%>
			<c:if test="${not empty error}">
				<span style="color: red;">${error}</span>
			</c:if>
		</main>
	</div>
</body>
</html>