package com.javalab.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.MemberDAO;
import com.javalab.vo.MemberVO;

/**
 * 회원가입폼 제공 및 등록 처리
 * - doGet : 회원가입 jsp 화면 제공
 * - doPost : 회원 등록(저장) 처리
 */
@WebServlet("/member")
public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MemberServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 디버깅 문자열
        System.out.println("member 서블릿의 doGet()이 실행되었습니다.");

        // 회원가입 폼으로 이동하는 dispatcher
        RequestDispatcher rd = request.getRequestDispatcher("/memberForm.jsp");
        rd.forward(request, response);
    }

    /**
     * 회원가입 처리
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 디버깅 문자열
        System.out.println("member 서블릿의 doPost()가 실행되었습니다.");

        // message body로 전달되어오는 파라미터 인코딩
        request.setCharacterEncoding("utf-8");

        // 전달받은 파라미터 담기
        String memberId = request.getParameter("id");
        String pwd = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // 전달받은 데이터로 MemberVO 객체 생성
        MemberVO memberVO = new MemberVO (memberId, pwd, name, email);
        
        // DAO 영역의 메소드 호출
     	MemberDAO memberDAO = new MemberDAO();
        int row = memberDAO.insertMember(memberVO);
     	
        // 회원가입 결과 확인
        if (row > 0) { // 회원등록 성공
            System.out.println("회원가입 성공, 로그인 페이지로 이동");
            //컨텍스트패스(경로) 얻기
            String contextPath = request.getContextPath();
            // 로그인 페이지로 이동(로그인폼을 띄워주는 서블릿 호출)
            response.sendRedirect(contextPath + "/login");
        } else { // 회원가입 중 오류 발생
            System.out.println("회원가입 실패, 회원가입 페이지로 이동");
            RequestDispatcher rd = request.getRequestDispatcher("/memberForm.jsp");
            rd.forward(request, response);
        }
    }
}