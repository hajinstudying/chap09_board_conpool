<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javalab.dao.* , com.javalab.vo.*, java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- JSTL 컨텍스트 패스(진입점 폴더) 변수 설정 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%-- now : 현재 시간의 시분초를 now 변수에 세팅 --%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board List - servlet board</title>
<%-- CSS 요청 문자열에 시시각각 변하는 시간을 파라미터로 전달하기 때문에 서버는 매번 새로운 요청으로 착각.
     캐싱된 CSS 파일이 아니라 늘 새로운 CSS를 읽어온다. 캐싱하지 않음. --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/board.css'/>?v=${now}" />
</head>
<body>
    <div class="container">
        <%-- 헤더 부분 include 액션 태그 사용, c:url 사용 금지, 경로를 직접 지정해야 함 --%>
        <jsp:include page="/common/header.jsp" />
        
        <main>
            <h3>게시물 목록</h3>
            <%-- 검색 폼 --%>
            <form action="<c:url value='/boardList'/>" method="get" class="search-form">
                <label for="keyword">검색 : </label>
                <input type="text" name="keyword" id="keyword" placeholder="검색어 입력" />
                <input type="submit" value="검색">
            </form>
            
            <c:if test="${empty boardList}">
                <p>게시물이 존재하지 않습니다.</p>
            </c:if>        
            <c:if test="${not empty boardList}">

                <table border="1">
                    <caption>게시판 목록</caption>
                    <colgroup>
                        <col width="100"/>
                        <col width="300"/>
                        <col width="80"/>
                        <col width="200"/>
                        <col width="80"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>게시물</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일자</th>
                            <th>조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="boardVO" items="${boardList}">
                            <tr>
                                <td><c:out value='${boardVO.bno}'/></td>
                                <td><a href="<c:url value='/boardDetail'/>?bno=${boardVO.bno}"><c:out value='${boardVO.title}'/></a></td>
                                <td><c:out value='${boardVO.memberId}'/></td>
                                <td><fmt:formatDate value="${boardVO.regDate}"
                                        pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><c:out value='${boardVO.hitNo}'/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td align="center" colspan="5">${page_navigator}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td align="center" colspan="5">Copyright javalab Corp.</td>
                        </tr>
                    </tfoot>
                </table>
                </c:if>
                <br>
                <a href="<c:url value='/board'/>">게시물 작성</a>
        </main>
    </div>
</body>
</html>