package com.javalab.dao;

import com.javalab.vo.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * DAO (Data Access Object) 
 * - 로그인 관련 업무에서 데이터베이스 관련된 메소드만 모듈화한 클래스로서
 *   데이터베이스와 관련된 모든 메소드를 갖고 있다:D
 * - 서블릿에 중복으로 존재하던 보일러 플레이트 코드(중복코드)를 한 곳으로 집약시켰다.
 * - 서블릿에선 DAO에 데이터베이스 관련 업무를 요청하고 해당 결과를 반환 받아서 다음 처리를 진행한다.
 */


public class LoginDAO {
	// 멤버 변수로 데이터베이스 관련 객체를 전역 변수로 정의
	// jdbc 드라이버 로딩 문자열
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	// 데이터베이스 접속 문자열
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	// 접속 계정(아이디/비밀번호)
	private static final String DB_USER = "mboard";
	private static final String DB_PASSWORD = "1234";

	Connection conn = null; // 데이터베이스 접속을 위한 커넥션 객체
	PreparedStatement pstmt = null; // 쿼리문을 만들고 실행해주는 객체
	ResultSet rs = null; // 쿼리 결과를 받아오는 객체

	/*
	 * connectDB() : 
	 * JDBC 드라이버 로딩과 커넥션 객체 생성하는 메소드
	 * Connection 담당 객체인 conn을 생성하여 저장해준다.
	 */
	public void connectDB() throws SQLException, ClassNotFoundException {

		try {
			Class.forName(JDBC_DRIVER); // jdbc 드라이버 로드
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스 커넥션 객체 얻기
			System.out.println("커넥션 객체를 획득했습니다.");
		} catch (Exception e) {
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
			//connectDB 메소드 호출
			connectDB();
			
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
