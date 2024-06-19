package com.javalab.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.BoardDAO;

/*
 * 
 */
@WebServlet("/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public BoardDeleteServlet() {
        super();
    }
    
    /*
     * 삭제할 때도 게시물을 조회 후 삭제해야하기 때문에 doGet()이 아니라 doPost()로 만든다.
     * 데이터베이스에 조작을 가하는 경우(CRUD)는 doPost()로 한다.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//디버깅 문자열
		System.out.println("boardDelete의 doPost() 메소드가 실행되었습니다.");
		
		// 파라미터 인코딩
		request.setCharacterEncoding("utf-8");
		
		//파라미터 받기
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		//boardDAO 객체 생성
		BoardDAO boardDAO = new BoardDAO();
		int row = boardDAO.deleteBoard(bno);
		
		// contextPath 얻기
		String contextPath = request.getContextPath();
		
		// 데이터베이스 작업결과 (row)로 분기
		if(row > 0) { // 삭제 성공
			// boardList로 이동
			// boardList.jsp를 부르면 목록 조회메소드를 호출하는 서블릿을 거치지 않기 때문에 아무 화면도 뜨지 않음
			response.sendRedirect(contextPath + "/boardList");
		} else { // 삭제 실패
			// 에러 메세지
			request.setAttribute("error", "게시물 삭제에 실패했습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("/boardDetail?bno=" + bno);
			rd.forward(request, response);
		}
	}

}
