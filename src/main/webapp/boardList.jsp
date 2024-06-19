<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.javalab.dao.* , com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- jstl 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board List - servlet board</title>
<link rel="stylesheet" type="text/css" href="css/board.css"/>
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
			<h3>게시물 목록</h3>
			<!-- 검색폼 -->
			<form action="${contextPath}/boardList"  method="post" id="searchForm">
				<label for="search">제목 검색 : </label>
				<input type="text" name="search" id="search">
				<input type="submit" value="검색">
			</form>
			
			<c:if test="${empty boardList}">
				<p>게시물이 존재하지 않습니다.</p>
			</c:if>		
			<c:if test="${not empty boardList}">
				<table border="1">
					<tr>
						<th>게시물</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<c:forEach var="boardVO" items="${boardList}">
						<tr>
							<td>${boardVO.bno}</td>
							<td><a href="${contextPath}/boardDetail?bno=${boardVO.bno}">${boardVO.title}</a></td>
							<td>${boardVO.memberId}</td>
							<td><fmt:formatDate value="${boardVO.regDate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${boardVO.hitNo}</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<a href="${contextPath}/board">게시물 작성</a>
			</c:if>
		</main>
	</div>
</body>
</html>