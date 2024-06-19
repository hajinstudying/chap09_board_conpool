package com.javalab.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javalab.dao.BoardDAO;
import com.javalab.vo.BoardVO;
import com.javalab.vo.MemberVO;

/*
 * 게시물 등록폼 제공 및 등록 처리
 * - doGet : 게시물 등록 jsp 화면 제공
 * - doPost : 게시물 등록(저장) 처리
 */
@WebServlet("/board") // '/board'는 URL pattern
public class BoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BoardServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 디버깅 문자열
        System.out.println("board 서블릿의 doGet()이 실행되었습니다.");

        // 게시물 등록 폼으로 이동하는 dispatcher
        RequestDispatcher rd = request.getRequestDispatcher("/boardInsertForm.jsp");
        rd.forward(request, response);
    }

    /*
     * 게시물 등록 처리
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 디버깅 문자열
        System.out.println("board 서블릿의 doPost()가 실행되었습니다.");

        // message body로 전달되어오는 파라미터 인코딩
        request.setCharacterEncoding("utf-8");

        // 전달받은 파라미터 담기
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
    	// 세션에서 로그인한 사용자 객체 가져오기
    	// getAttribute는 Object로 반환하기 때문에 형변환 필요
    	HttpSession ses = request.getSession();
    	MemberVO memberVO = (MemberVO)ses.getAttribute("member"); 
    	String memberId = memberVO.getMemberId(); // 사용자 id
    	
        // 전달받은 데이터로 MemberVO 객체 생성
        BoardVO boardVO = new BoardVO (title, content, memberId);
        
        // DAO 영역의 메소드 호출
     	BoardDAO boardDAO = new BoardDAO();
        int row = boardDAO.insertBoard(boardVO);
     	
        // 게시물 등록 결과 확인
        if (row > 0) { // 등록 성공
            System.out.println("게시물 등록 성공, 게시물 목록 페이지로 이동");
            //컨텍스트패스(경로) 얻기
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/boardList");
        } else { // 게시물 등록 중 오류 발생
            request.setAttribute("error", "게시물 작성에 실패했습니다.");
            RequestDispatcher rd = request.getRequestDispatcher("/bordInsertForm.jsp");
            rd.forward(request, response);
        }
    }
}