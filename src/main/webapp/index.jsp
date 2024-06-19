<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index - servlet board</title>
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
			<h3>여기는 servlet board의 index page</h3>

			<c:if test="${not empty sessionScope.member}">
				<p>
					<a href="${contextPath}/logout.jsp">로그아웃</a>
				</p>
				<p>
					<a href="${contextPath}/board">게시물 등록</a>
				</p>
			</c:if>
			<c:if test="${empty sessionScope.member }">
				<p>
					<a href="${contextPath}/login">로그인</a>
				</p>
			</c:if>
			<p>
				<a href="${contextPath}/boardList">게시물 목록</a>
			</p>
		</main>
	</div>
</body>
</html>