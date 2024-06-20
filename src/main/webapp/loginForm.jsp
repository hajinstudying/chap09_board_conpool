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
<title>loginForm - servlet board</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
</head>

<body>
	<div class="container">
		<%-- 헤더부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야함 --%>
		<jsp:include page="/common/header.jsp" />
		
		<main>
			<h2>로그인</h2>
			<form action="<c:url value='/login'/>" method="post">
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
					<a href="<c:url value='/member'/>">회원가입</a>
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