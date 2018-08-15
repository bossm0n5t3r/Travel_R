package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Blogs;

public class ConnBlogs {
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
	
	public void insert(Blogs b) {
		con();
		String sql = "insert into blogs values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b.getBlog_num());
			pstmt.setString(2, b.getR_date());
			pstmt.setString(3, b.getSubject());
			pstmt.setString(4, b.getUrl());
			pstmt.setString(5, b.getWriter());
			pstmt.setString(6, b.getContent1());
			pstmt.setInt(7, b.getTraff());
			pstmt.setInt(8, b.getFood());
			pstmt.setInt(9, b.getTour());
			pstmt.setInt(10, b.getStay());
			pstmt.setInt(11, b.getEtc());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
	}
	
	public ArrayList <ArrayList <String>> blogsOrderByType(String type) {
		con();
		ArrayList <String> subject = new ArrayList <String> ();
		ArrayList <String> url = new ArrayList <String> ();
		ArrayList <ArrayList <String>> result = new ArrayList <ArrayList <String>> (2);
		String sql = "select subject, url from blogs where " + type + " > 0 order by " + type + " desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				subject.add(rs.getString(1));
				url.add(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
		result.add(subject);
		result.add(url);
		return result;
	}
	
	public ArrayList <ArrayList <String>> blogsOrderBySpecialType(String [] types) {
		con();
		ArrayList <String> subject = new ArrayList <String> ();
		ArrayList <String> url = new ArrayList <String> ();
		ArrayList <ArrayList <String>> result = new ArrayList <ArrayList <String>> (2);
		String sql = "select subject, url from blogs where ";
		for (int i = 0; i < types.length; i++) {
			if (i == types.length - 1) {
				sql += types[i] + " > 0";
				break;
			}
			sql += types[i] + " > 0 and ";
		}
		sql += " order by ";
		for (int i = 0; i < types.length; i++) {
			if (i == types.length - 1) {
				sql += types[i] + " desc";
				break;
			}
			sql += types[i] + " desc, ";
		}
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				subject.add(rs.getString(1));
				url.add(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
		result.add(subject);
		result.add(url);
		return result;
	}
}
