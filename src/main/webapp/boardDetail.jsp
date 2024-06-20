<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- jstl 컨텍스트패스(진입점 폴더) 변수 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<%-- now : 현재 시간의 시분초를 now 변수에 세팅 --%>
<c:set var="now" value="<%= new java.util.Date()%>"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardDetail - servlet board</title>
<%-- css 요청 문자열에 시시각각 변하는 시간을 파라미터로 전달하기 때문에 서버는 매번 새로운 요청으로 착각.
	 캐싱된 css파일이 아니라 늘 새로운 css를 읽음 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
</head>
<body>
	<div class="container">
		<%-- 헤더부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야함 --%>
		<jsp:include page="/common/header.jsp" />
		<main>
			<table border="1">
				<tr>
					<th>게시물 번호</th>
					<td><c:out value="${boardVO.bno}"/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><c:out value="${boardVO.title}"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<%-- c:out은 ckeditor의 폰트 효과를 주는 태그들을 무력화시키기 때문에 내용부에선 사용x --%>
					<td>${boardVO.content}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><c:out value="${boardVO.memberId}"/></td>
				</tr>
				<tr>
					<th>작성일자</th>
					<td><fmt:formatDate value="${boardVO.regDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><c:out value="${boardVO.hitNo}"/></td>
				</tr>
			</table>
			<br> <a href="<c:url value='/boardList'/>">목록으로 돌아가기</a> <a
				href="<c:url value='/boardUpdate'/>?bno=${boardVO.bno}">글 수정하기</a>
			<%-- delete는 doPost로 받기 때문에 예외적으로 Post로 전달 --%>
			<form action="<c:url value='/boardDelete'/>" method="post">
				<input type="hidden" name="bno" value="${boardVO.bno}">
				<input type="submit" value="삭제" onclick="return confirm('정말 삭제하시겠습니까?');">
			</form>
		</main>
	</div>
</body>
</html>