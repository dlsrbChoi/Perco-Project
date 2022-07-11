package dao;

import java.sql.* ;

import javax.naming.NamingException;

import org.json.simple.parser.ParseException;

import util.ConnectionPool;

public class GYMDAO {
	public String insert(String uid, String gymid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id FROM gym WHERE id = ? AND gymid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, gymid);
			rs = stmt.executeQuery();
			if (rs.next()) return "EX";
			stmt.close();
			
			sql = "INSERT INTO gym VALUES(?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, gymid);
			int count = stmt.executeUpdate();
			return (count == 1) ? "OK" : "ER";
			
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public String remove(String uid, String gymid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM gym WHERE id = ? AND gymid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, gymid);
			
			int count = stmt.executeUpdate();
			return (count == 1) ? "OK" : "ER";
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public String getList(String uid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT gymid FROM gym WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			
			String str = ""; int cnt = 0;
			while(rs.next()) {
				if (cnt++ > 0) str += ",";
				str += "\"" + rs.getString("gymid") + "\"";
			}
			if (cnt == 0) return "[]";
			rs.close(); stmt.close();
			
			sql = "SELECT jsonstr FROM director WHERE id IN (" + str + ")";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			str = "["; cnt = 0;
			while(rs.next()) {
				if (cnt++ > 0) str += ",";
				str += rs.getString("jsonstr");
			}
			str += "]";
			return str;
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
}
