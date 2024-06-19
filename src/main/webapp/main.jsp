<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main - servlet board</title>
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
			<h3>여기는 servlet board의 main.jsp입니다.</h3>

			<!-- 세션에 저장되어있는 memberId를 EL을 이용해서 바로 꺼내씀 -->
			<c:if test="${not empty sessionScope.member}">
				<a href="${contextPath}/board">게시물 등록</a>
			</c:if>
			<br>
			<a href="${contextPath}/boardList">게시물 목록</a>
		</main>
	</div>
</body>
</html>