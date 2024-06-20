package com.javalab.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.BoardDAO;
import com.javalab.vo.BoardVO;

/*
 * 게시물 내용 보기 서블릿
 */

@WebServlet("/boardDetail")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDetailServlet() {
        super();
    }

    /*
     * 게시물 내용 조회
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파라미터 추출
		int bno = Integer.parseInt(request.getParameter("bno")); 

		// 데이터베이스 전담 객체 생성
		BoardDAO boardDAO = BoardDAO.getInstance();
		// 1. 조회수 증가
		boardDAO.incrementHitNo(bno);
		//2. 게시물 내용 조회 후 boardVO객체에 담음
		BoardVO boardVO = boardDAO.getBoard(bno);
		
		// request 영역에 board 객체 저장
		request.setAttribute("boardVO", boardVO);
		// boardDetail.jsp 페이지로 이동시킴 (내부 이동이니까 contextPath 안써도 됨)
		RequestDispatcher rd = request.getRequestDispatcher("/boardDetail.jsp");
		rd.forward(request, response);
	}
}
