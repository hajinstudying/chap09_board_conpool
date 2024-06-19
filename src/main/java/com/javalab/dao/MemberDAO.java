package com.javalab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalab.vo.MemberVO;

/*
 * DAO (Data Access Object) 
 * - 회원가입 관련 업무에서 데이터베이스 관련된 메소드만 모듈화한 클래스로서
 *   데이터베이스와 관련된 모든 메소드를 갖고 있다:D
 * - 서블릿에 중복으로 존재하던 보일러 플레이트 코드(중복코드)를 한 곳으로 집약시켰다.
 * - 서블릿에선 DAO에 데이터베이스 관련 업무를 요청하고 해당 결과를 반환 받아서 다음 처리를 진행한다.
 */

public class MemberDAO {
	
	private DataSource dataSource;
	private Connection conn = null; // 데이터베이스 접속을 위한 커넥션 객체
	private PreparedStatement pstmt = null; // 쿼리문을 만들고 실행해주는 객체
	private ResultSet rs = null; // 쿼리 결과를 받아오는 객체

	/* 생성자 */
	public MemberDAO() {
		try {
			Context ctx = new InitialContext(); // 커넥션 풀을 참조하기 위한 환경변수(네이밍 컨텍스트)를 가지고 있는 객체
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/*
	 * insertMember(...) : 회원가입 처리 메소드
	 * 데이터 하나하나 전달받지 말고(^^;) memberVO 객체를 파라미터로 전달받는다.
	 */

	public int insertMember(MemberVO memberVO) {
		// 디버깅 문자열
		System.out.println("memberDAO의 insertMember(...) 메소드가 호출되었습니다.");
		
		// 반환할 값 일단 선언
		int row = 0;

		try {
			conn = dataSource.getConnection();
			
			// 멤버 추가 쿼리
			String sql = "INSERT INTO member(member_id, password, name, email) VALUES(?, ?, ?, ?)";

			// PrepareStatement 객체 얻기(쿼리문 전달)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getMemberId());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getEmail());

			row = pstmt.executeUpdate(); // 실행한 행의 갯수가 담김

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 등록 중 오류가 발생했습니다.");
		} finally {
			closeResource(); //자원 해제 메소드 호출
		}
		// 메소드 결과 값 반환
		return row;
	}
	
	/*
	 * closeResource() :
	 * 데이터베이스 관련 자원 반납(해제) 메소드
	 */
	private void closeResource() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
