<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 컨텍스트패스(진입점 폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardInsertForm - servlet board</title>
<!-- css는 상대경로로, 현재 파일의 위치에서 찾게된다. :D -->
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
				<h3>게시물 작성</h3>
				<form action="${contextPath}/board" method="post">
					<div>
						<label for="title">제목</label> <input type="text" name="title"
							id="title" required>
					</div>
					<div>
						<label for="content">내용</label>
						 <textarea id="content" name="content" cols="80" rows="10" required>
						 </textarea>
  						<script>CKEDITOR.replace('content');</script>

					</div>
					<div>
						<input type="submit" value="저장">
						<input type="reset" value="다시쓰기">
					</div>
				</form>
			</c:if>
			<!-- 세션 영역에 로그인된 멤버 객체가 없다면 -->
			<c:if test="${empty sessionScope.member }">
				<script>
					alert('회원만 게시물을 작성할 수 있습니다.');
					window.location.href = '${contextPath}/loginForm.jsp';
				</script>
			</c:if>
		</main>
	</div>
</body>
</html>