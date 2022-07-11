package dao;

import java.sql.*;
import javax.naming.NamingException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.ConnectionPool;

public class FeedDAO {
	public boolean insert(String jsonstr) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			synchronized (this) {
				// phase 1. add "no" property -----------------------------
				String sql = "SELECT no FROM feed ORDER BY no DESC LIMIT 1";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();

				int max = (!rs.next()) ? 0 : rs.getInt("no");
				stmt.close(); rs.close();
				
				JSONParser parser= new JSONParser();
				JSONObject jsonobj= (JSONObject) parser.parse(jsonstr);
				jsonobj.put("no", max + 1);
				
				// phase 2. add "user" property ------------------------------
				String uid= jsonobj.get("id").toString();
				
				sql= "SELECT jsonstr FROM user WHERE id = ?";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, uid);
				rs= stmt.executeQuery();
				
				if(rs.next()) {
					String usrstr= rs.getString("jsonstr");
					JSONObject usrobj= (JSONObject) parser.parse(usrstr);
					usrobj.remove("password");
					usrobj.remove("ts");
					jsonobj.put("user", usrobj);
				}
				stmt.close(); rs.close();
				
				
				// phase 3. insert jsonobj to the table ------------------------
				sql = "INSERT INTO feed(no, id, jsonstr) VALUES(?, ?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, max + 1);
				stmt.setString(2, jsonobj.get("id").toString());
				stmt.setString(3, jsonobj.toJSONString());

				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			}

		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public String getList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT jsonstr FROM feed ORDER BY no DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			String str = "[";
			int cnt = 0;
			while (rs.next()) {
				if (cnt++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";

		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
		
	public String getGroup(String frids, String maxNo) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT jsonstr FROM feed WHERE id IN ("+ frids+ ")";
			if (maxNo != null) {
				sql += " AND no < " + maxNo;
			}
			sql += " ORDER BY no DESC LIMIT 3";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			String str = "[";
			int cnt = 0;
			while (rs.next()) {
				if (cnt++ > 0) str += ", ";
				str += rs.getString("jsonstr");
			}
			return str + "]";

		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
}