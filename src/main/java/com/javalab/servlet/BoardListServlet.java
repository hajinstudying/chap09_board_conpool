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
		//디버깅 문자열 
		System.out.println("BoardListServlet의 doGet()메소드가 실행되었습니다.");
		
		// 키워드 파라미터 추출
		String keyword = request.getParameter("keyword");
		List<BoardVO> boardList = null;


		// 데이터베이스 전담 객체 (BoardDAO 객체) 가져오기 *****
		// BoardDAO boardDAO = new BoardDAO(); // 싱글톤패턴 적용 이전 방식
		// BoardDAO의 성생자가 private으로 막혔기 때문에 static 메소드를 이용해서 객체를 가져온다.
		BoardDAO boardDAO = BoardDAO.getInstance();		
		
		// 키워드 유무에 따른 분기
		if(keyword != null && !keyword.isEmpty()) {
			// 키워드가 있을 경우 검색된 목록 가져오기
			boardList = boardDAO.searchBoardList(keyword); //searchBoardList() 호출
		} else {
			// 키워드가 없을 경우 전체 목록 가져오기
			boardList = boardDAO.getBoardList(); // getBoardList()호출
		}
		
		//request 영역에 boardList 저장
		request.setAttribute("boardList", boardList);
		
		// 저장한 boardList를 출력할 페이지인 boardList.jsp로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/boardList.jsp");
		rd.forward(request, response);
	}
}