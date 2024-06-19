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
 * - 로그인 관련 업무에서 데이터베이스 관련된 메소드만 모듈화한 클래스로서
 *   데이터베이스와 관련된 모든 메소드를 갖고 있다:D
 * - 서블릿에 중복으로 존재하던 보일러 플레이트 코드(중복코드)를 한 곳으로 집약시켰다.
 * - 서블릿에선 DAO에 데이터베이스 관련 업무를 요청하고 해당 결과를 반환 받아서 다음 처리를 진행한다.
 */


public class LoginDAO {
	
	private DataSource dataSource; // 커넥션 풀 객체 type
	private Connection conn = null; // 데이터베이스 접속을 위한 커넥션 객체
	private PreparedStatement pstmt = null; // 쿼리문을 만들고 실행해주는 객체
	private ResultSet rs = null; // 쿼리 결과를 받아오는 객체

	/*
	 * 생성자
	 * 1. JNDI(java naming and directory interface)
	 *  - 네이밍 인터페이스 : 이름(jdbc/oracle)으로 특정 자원에 대한 정보를 참조하는 방법
	 *  - InitialContext : 기본 네이밍 컨텍스트를 제공하는 클래스. (key-value로 가지고 있다.)
	 *  				   이 객체를 통해서 초기 네이밍 컨텍스트 객체를 생성한다.
	 * 2. java:comp/env : 네이밍 컨텍스트에서 애플리케이션의 환경변수를 가져오기 위한 루트 경로를 말한다.
	 * 					  환경 변수 중에서 'jdbc/oracle'인 것(커넥션 풀)을 찾는 것
	 */
	public LoginDAO() {
		try {
			Context ctx = new InitialContext(); // 커넥션 풀을 참조하기 위한 환경변수(네이밍 컨텍스트)를 가지고 있는 객체
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * login(String memberId, String pwd) : 
	 * 로그인 관련 데이터베이스 처리
	 * - 파라미터로 전달받은 아이디/비밀번호로 데이터베이스에서 해당 사용자를 조회하여
	 * 	 객체로 만들고 호출한 곳으로 반환시킨다. 
	 */
	public MemberVO login(String memberId, String pwd) {
		System.out.println("LoginDAO의 login() 메소드가 호출되었습니다.");
		MemberVO member = null;

		try {
			conn = dataSource.getConnection();
			
			// 아이디와 비번이 db에 존재하는지를 쿼리문 where절로 검사
			String sql = "SELECT member_id, name, email FROM member WHERE member_id = ? AND password = ?";
			pstmt = conn.prepareStatement(sql); // PrepareStatement 객체 얻기(쿼리문 전달)
			pstmt.setString(1, memberId); // 첫번째 ?에 파라미터 세팅
			pstmt.setString(2, pwd); // 두번째 ?에 파라미터 세팅

			rs = pstmt.executeQuery(); // 쿼리문 실행해서 결과 전달받기

			// 하나 가져오기 때문에 while문이 아니라 if문을 사용
			if (rs.next()) { // 결과가 있으면 즉, 로그인 성공

				// setter메소드로 객체에 값 설정
				member = new MemberVO(); // 자바 빈즈 객체 생성 -> 생성도니 객체를 세션에 보관할 용도
				member.setMemberId(rs.getString("member_id")); // 자바빈즈 데이터베이스 조회 값 세팅
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("로그인 중 오류가 발생했습니다.");
		} finally {
			closeResource(); // 자원 해제 메소드 호출
		}
		
		// 조회한 객체 반환
		return member; 
		
	} // end of login()
	
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
