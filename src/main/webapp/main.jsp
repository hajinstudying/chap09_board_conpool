<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 컨텍스트패스(진입점 폴더) 변수 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<%-- now : 현재 시간의 시분초를 now 변수에 세팅 --%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main - servlet board</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
</head>

<body>
	<div class="container">
		<%-- 헤더부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야함 --%>
		<jsp:include page="/common/header.jsp" />
		
		<main>
			<h3>여기는 servlet board의 main.jsp입니다.</h3>

			<%-- 세션에 저장되어있는 memberId를 EL을 이용해서 바로 꺼내씀 --%>
			<c:if test="${not empty sessionScope.member}">
				<a href="<c:url value='/board' />">게시물 등록</a>
			</c:if>
			<br>
			<a href="<c:url value='/boardList'/>">게시물 목록</a>
		</main>
	</div>
</body>
</html>