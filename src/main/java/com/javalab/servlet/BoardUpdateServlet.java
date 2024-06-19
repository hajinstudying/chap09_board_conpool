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
 * 게시물 수정 서블릿
 * - doGet : 게시물 수정폼 제공
 * - doPost : 게시물 수정 처리
 */
@WebServlet("/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardUpdateServlet() {
        super();
    }
    
    /*
     * 게시물 수정폼 제공 - 수정화면으로 이동
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 디버깅 문자열
        System.out.println("boardUpdate 서블릿의 doGet()이 실행되었습니다.");
        // 전달된 bno 파라미터 추출
        int bno = Integer.parseInt(request.getParameter("bno"));
        
        // DAO에서 게시물 한 개 조회
        // 조회 메소드(getBoard())를 사용하기 위해 BoardDAO 객체 생성
        // 조회 메소드에서 가져온 객체를 BoardVO 객체에 담음 
        BoardDAO boardDAO = new BoardDAO();
        BoardVO boardVO = boardDAO.getBoard(bno);

        //request 영역에 수정할 게시물 객체를 저장
        request.setAttribute("boardVO", boardVO);
        
        // 게시물이동 dispatcher
        RequestDispatcher rd = request.getRequestDispatcher("/boardUpdateForm.jsp");
        rd.forward(request, response);

	}

	/*
	 * 게시물 수정 처리 - 데이터베이스에 수정된 게시물 저장
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 디버깅 문자열
        System.out.println("boardUpdate 서블릿의 doPost()가 실행되었습니다.");
        
        // 메세지 바디 부분으로 전달되는 파라미터 인코딩
        request.setCharacterEncoding("utf-8");
        // 파라미터 추출 후 객체에 세팅
        int bno = Integer.parseInt(request.getParameter("bno"));
        BoardVO boardVO = new BoardVO();
        boardVO.setBno(bno);
        boardVO.setTitle(request.getParameter("title"));
        boardVO.setContent(request.getParameter("content"));
        
        // boardVO 객체를 DAO에 전달해서 수정
        BoardDAO boardDAO = new BoardDAO();
        int row = boardDAO.updateBoard(boardVO); // 게시물 수정 메소드 호출
        
        // 컨텍스트패스
        String contextPath = request.getContextPath();
        
        // row를 통해서 분기
        if (row > 0) { // 정상 수정
			System.out.println("게시물 수정 성공"); // 디버깅을 위한 문자열
			// 페이지 이동
			response.sendRedirect(contextPath + "/boardDetail?bno=" + bno); 
		} else { // 수정 실패
			// 오류 메세지 세팅
			request.setAttribute("error", "게시물 수정에 실패했습니다.");
			// 오류 메세지를 가지고 갈 수 있도록 dispatcher로 이동
			// boardUpdateForm.jsp로 이동하면 빈 페이지가 나오기 때문에 bno를 포함시켜 서블릿으로 호출해야한다.
			RequestDispatcher rd = request.getRequestDispatcher("/boardUpdate?bno=" + bno);
			rd.forward(request, response);
		}
	}

}
