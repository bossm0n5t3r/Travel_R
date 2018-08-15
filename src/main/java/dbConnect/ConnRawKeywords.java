package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ConnRawKeywords {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void con() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@183.101.196.136:1521:xe", "kitri_ml_01", "kitri");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void discon() {
		try {
			if (rs != null)
				rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insert(int key, String[] keywords) {
		con();
		String sql = "insert into raw_keywords values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			for (int i = 0; i < 10; i++) {
				pstmt.setString(i + 2, keywords[i]);
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
	}

	public ArrayList<String> extractAll() {
		con();
		ArrayList<String> result = new ArrayList<String>();
		String sql = "select * from raw_keywords";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				for (int i = 0; i < 10; i++) {
					result.add(rs.getString(i + 2));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
		return result;
	}
	
	public ArrayList <String> selectRawKeywords(int blog_num) {
		con();
		ArrayList <String> result = new ArrayList<String>();
		String sql = "select * from raw_keywords where blog_num = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				for (int i = 2; i < 12; i++)
					result.add(rs.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
		return result;
	}
}
