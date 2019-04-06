package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;
public class DBUtil {
	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:easy_grader.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}
	//execute a sql statement
	public static boolean dosql(String sql) {
		boolean isSucceed = false;
		Connection conn = getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			isSucceed = ps.execute();
			System.out.println("dosql: " + sql);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSucceed;
	}

	//retrieve data with no user input
	public static CachedRowSet select(String sql) {
		CachedRowSet crs=null;
		try {
			crs = new CachedRowSetImpl();
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			crs.populate(rs);
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return crs;
	}
	
	//retrieve data with user input
	public static CachedRowSet select(String sql,String[] params) {
		CachedRowSet crs=null;
		try {
			crs = new CachedRowSetImpl();
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				ps.setString(i+1, params[i]);
			}
			ResultSet rs = ps.executeQuery(sql);
			crs.populate(rs);
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return crs;
	}
	
}
