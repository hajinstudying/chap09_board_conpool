package com.javalab.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.BoardDAO;
import com.javalab.vo.BoardVO;



@WebServlet("/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListServlet() {
        super();
    }

    /*
     * 게시물 목록 조회
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터베이스 전담 객체 (BoardDAO 객체) 생성
		BoardDAO boardDAO = new BoardDAO();
		List<BoardVO> boardList = boardDAO.getBoardList(); // getBoardList()호출

		//request 영역에 boardList 저장
		request.setAttribute("boardList", boardList);
		
		// 저장한 boardLIst를 출력할 페이지인 boardList.jsp로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/boardList.jsp");
		rd.forward(request, response);
	}
}