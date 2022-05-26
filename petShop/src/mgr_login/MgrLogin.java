package mgr_login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracleDB.OracleDB;
import util.MyUtil;

public class MgrLogin {
	
	public boolean mgrLogin() {
		
		System.out.println("===== 관리자 로그인 =====");
		// 아이디, 비번 받기
		System.out.print("아이디 : ");
		String id = MyUtil.sc.nextLine();
		System.out.print("비밀번호 : ");
		String pwd = MyUtil.sc.nextLine();
		
		// 디비와 연결, 관리자 아이디/비번 일치여부
		Connection conn = OracleDB.getOracleConnection();
		String sql = "SELECT * FROM ADMIN WHERE ADM_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			rs.next();
			String dbPwd = rs.getString("ADM_PWD");
			if(pwd.equals(dbPwd)) {
				System.out.println("관리자 ID " + id + " 로그인 되었습니다.");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			OracleDB.close(conn);
			OracleDB.close(rs);
			OracleDB.close(pstmt);
		}
		System.out.println("아이디와 비밀번호를 다시 확인하십시오.");
		return false;
		
	}//mgrLogin
	
	
	public void memberMng() {
		boolean isCorrect = true; 
		//회원 관리
		System.out.println("===== 회원 관리 페이지 =====");
		System.out.println("1. 회원 조회 및 관리");
		System.out.println("2. 오늘의 회원 변동");
		
		while(isCorrect) {
			int n = MyUtil.scInt();
			
			switch(n) {
			case 1: memberList(); break;
			case 2: todayMem(); break;
			default: System.out.println("상위 메뉴로 돌아갑니다."); isCorrect = false;
			}
		}
		return;
	}//memberMng
	
	
	private void memberList() {
		//회원 조회
		System.out.println("===== 회원 목록 조회 =====");
		Connection conn = OracleDB.getOracleConnection();
		String sql = "SELECT * FROM MEMBER WHERE QUIT_YN = 'N' ORDERED BY ENROLL_DATE DESC";
		
	}//memberList
	
	
	private void memWithdraw(int mem_no) {
		//회원 강퇴
		System.out.println("탈퇴시키겠습니까? (y/n)");
		String answer = MyUtil.sc.nextLine();
		if("y".equals(answer)) {
			Connection conn = OracleDB.getOracleConnection();
			String sql = "UPDATE MEMBER SET QUIT_YN = 'Y' WHERE MEM_NO = ?";
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_no);
				int result = pstmt.executeUpdate();
				if(result == 1) {
					System.out.println("탈퇴가 성공적으로 이루어졌습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				OracleDB.close(conn);
				OracleDB.close(pstmt);
			}
			System.out.println("탈퇴 처리에 실패하였습니다.");
		}else {
			System.out.println("상위 메뉴로 돌아갑니다.");
			return;
		}
		
	}//memWithdraw
	
	
	
	private void todayMem() {
		//오늘의 회원 변동 조회
		
	}

}//class
