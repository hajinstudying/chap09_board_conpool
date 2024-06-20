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
<title>registerForm - servlet board</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
</head>

<body>
	<div class="container">
		<%-- 헤더부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야함 --%>
		<jsp:include page="/common/header.jsp" />
		
		<main>
			<h3>회원가입</h3>
			<form id="regForm" action="<c:url value='/member'/>" method="post">
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