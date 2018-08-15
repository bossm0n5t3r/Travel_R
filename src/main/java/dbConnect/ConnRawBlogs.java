package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import vo.RawBlogs;

public class ConnRawBlogs {
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

	public void insert(RawBlogs r) {
		con();
		String sql = "insert into raw_blogs values(blog_num.nextval, ?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getR_date());
			pstmt.setString(2, r.getSubject());
			pstmt.setString(3, r.getUrl());
			pstmt.setString(4, r.getWriter());
			pstmt.setString(5, r.getContent1());
			pstmt.setString(6, r.getContent2());
			pstmt.setString(7, r.getContent3());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
	}

	public HashMap<Integer, String> selectContents() {
		con();
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		String sql = "select blog_num, content1, content2, content3 from raw_blogs";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.put(rs.getInt(1), rs.getString(2) + rs.getString(3) + rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
		return result;
	}
	
	public ArrayList <RawBlogs> selectAll() {
		con();
		ArrayList <RawBlogs> result = new ArrayList <RawBlogs> ();
		String sql = "select * from raw_blogs";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RawBlogs r = new RawBlogs(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
				result.add(r);
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
