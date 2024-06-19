<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>registerForm - servlet board</title>
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
			<h3>회원가입</h3>
			<form id="regForm" action="${contextPath}/member" method="post">
				<div>
					아이디: <input type="text" name="id" id="id">
				</div>
				<div>
					비밀번호: <input type="password" name="password" id="password">
				</div>
				<div>
					비밀번호 확인: <input type="password" name="pwdConfirm" id="pwdConfirm">
				</div>
				<div>
					이름: <input type="text" name="name" id="name">
				</div>
				<div>
					이메일 : <input type="text" name="email" id="email">
				</div>
				<div>
					<input type="submit" id="btnSubmit" value="전송">
				</div>
			</form>
		</main>
	</div>
</body>
</html>