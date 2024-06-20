<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 컨텍스트패스(진입점 폴더) 변수 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<%-- now : 현재 시간의 시분초를 now 변수에 세팅 --%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardUpadateForm</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
<script src='<c:url value="/ckeditor/ckeditor.js"/>'>
</script>
<script src='<c:url value="/ckeditor/config.js"/>'>
</script>
</head>

<body>
	<div class="container">
		<%-- 헤더부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야함 --%>
		<jsp:include page="/common/header.jsp" />
		
		<main>
			<%-- 세션영역에 member 객체가 있다 = 로그인이 되어있다  --%>
			<c:if test="${!empty sessionScope.member}">
				<h3>게시물 수정</h3>
				<form action="<c:url value='/boardUpdate'/>" method="post">
					<%-- 수정 대상인 글의 bno를 전달하기 위해 hidden으로 요소 전달 --%>
					<input type="hidden" name="bno" value="<c:out value='${boardVO.bno}'/>">
					<div>
						<label for="title">제목</label>
						<%--  수정 전에 이미 이전 값을 볼 수 있도록 value 값 지정 --%>
						<input type="text" name="title" id="title" value="<c:out value='${boardVO.title}'/>" required>
					</div>
					<div>
						<label for="content">내용</label>
						<%-- textarea는 태그 내용부를 붙여써줘야 공백이 안생김 --%>
						<%-- c:out은 ckeditor의 폰트 효과를 주는 태그들을 무력화시키기 때문에 내용부에선 사용x --%>
						<textarea name="content" cols="150" rows="10">
							${boardVO.content}
						</textarea>
						<script>CKEDITOR.replace('content');</script>
					</div>
					<div>
						<input type="submit" value="수정">
						<input type="reset" value="다시쓰기">
					</div>
				</form>
			</c:if>
			<%-- 세션 영역에 로그인된 멤버 객체가 없다면 --%>
			<c:if test="${empty sessionScope.member}">
				<script>
					alert('회원만 게시물을 수정할 수 있습니다.');
					window.location.href = '<c:url value='/loginForm.jsp'/>';
				</script>
			</c:if>
		</main>
	</div>
</body>
</html>