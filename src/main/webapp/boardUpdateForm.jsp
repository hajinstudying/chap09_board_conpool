<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardUpadateForm</title>
<link rel="stylesheet" type="text/css" href="css/board.css" />
<script src='<c:url value="/ckeditor/ckeditor.js"/>'>
</script>
<script src='<c:url value="/ckeditor/config.js"/>'>
</script>
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
			<!-- 세션영역에 member 객체가 있다 = 로그인이 되어있다  -->
			<c:if test="${!empty sessionScope.member}">
				<h3>게시물 수정</h3>
				<form action="${contextPath}/boardUpdate" method="post">
					<!-- 수정 대상인 글의 bno를 전달하기 위해 hidden으로 요소 전달 -->
					<input type="hidden" name="bno" value="${boardVO.bno}">
					<div>
						<label for="title">제목</label>
						<!--  수정 전에 이미 이전 값을 볼 수 있도록 value 값 지정 -->
						<input type="text" name="title" id="title"
							value="${boardVO.title}" required>
					</div>
					<div>
						<label for="content">내용</label>
						<!-- textarea는 태그 내용부를 붙여써줘야 공백이 안생김 -->
						<textarea name="content" cols="150" rows="10" required>
							${boardVO.content}
						</textarea>
						<script>CKEDITOR.replace('content');</script>
					</div>
					<div>
						<input type="submit" value="수정"> <input type="reset"
							value="다시쓰기">
					</div>
				</form>
			</c:if>
			<!-- 세션 영역에 로그인된 멤버 객체가 없다면 -->
			<c:if test="${empty sessionScope.member }">
				<script>
					alert('회원만 게시물을 수정할 수 있습니다.');
					window.location.href = '${contextPath}/login';
				</script>
			</c:if>
		</main>
	</div>
</body>
</html>