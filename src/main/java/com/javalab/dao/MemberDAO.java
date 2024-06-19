package com.javalab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javalab.vo.MemberVO;

/*
 * DAO (Data Access Object) 
 * - 회원가입 관련 업무에서 데이터베이스 관련된 메소드만 모듈화한 클래스로서
 *   데이터베이스와 관련된 모든 메소드를 갖고 있다:D
 * - 서블릿에 중복으로 존재하던 보일러 플레이트 코드(중복코드)를 한 곳으로 집약시켰다.
 * - 서블릿에선 DAO에 데이터베이스 관련 업무를 요청하고 해당 결과를 반환 받아서 다음 처리를 진행한다.
 */

public class MemberDAO {

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
	 * connectDB() : JDBC 드라이버 로딩과 커넥션 객체 생성하는 메소드 Connection 담당 객체인 conn을 생성하여
	 * 저장해준다.
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
	 * insertMember(...) : 회원가입 처리 메소드
	 * 데이터 하나하나 전달받지 말고(^^;) memberVO 객체를 파라미터로 전달받는다.
	 */

	public int insertMember(MemberVO memberVO) {
		// 디버깅 문자열
		System.out.println("memberDAO의 insertMember(...) 메소드가 호출되었습니다.");
		
		// 반환할 값 일단 선언
		int row = 0;

		try {

			// connectDB 메소드 호출
			connectDB();

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
