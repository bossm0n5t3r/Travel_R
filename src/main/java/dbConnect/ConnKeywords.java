package dbConnect;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConnKeywords {
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

	public void insertKeywords(String keyword) {
		con();
		try {
			String fileName = "Keywords\\" + keyword + ".txt";
			Path filepath = Paths.get(fileName);
			List<String> key = Files.readAllLines(filepath);
			System.out.println(key.get(0));
			System.out.println(key.get(0).equals("traff"));
			for (int i = 1; i < key.size(); i++) {
				String sql = "insert into keywords(" + keyword + ") values(?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, key.get(i));
				System.out.println(key.get(i));
				pstmt.executeUpdate();
				if (i % 100 == 0) {
					discon();
					con();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null || pstmt != null)
				discon();
		}
	}

	public ArrayList<String> keywordsSet(String type) {
		con();
		ArrayList<String> result = new ArrayList<String>();
		String sql = "select " + type + " from keywords where " + type + " is not null";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
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
