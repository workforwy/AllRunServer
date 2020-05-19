package dao.operate;

import dao.conn.DBConnManager;
import util.Log;
import util.Tools;

import java.sql.*;


/**
 * insert delete update
 * 
 * @author smlzhang
 * 
 */
public class Modify {
	Log logger = Log.getInstance();

	/**
	 * 可以用?,防止Sql注入,在mysql中列名不能有?
	 * 
	 * @param sql
	 * @param setParameter
	 * @return
	 */
	public int exec(String sql, SetParameter setParameter) {
		// System.out.println(sqlStr);

		int flag = -2;
		DBConnManager conn = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");
			stmt = con.prepareStatement(sql);
			setParameter.set(stmt);
			int value = stmt.executeUpdate();
			flag = value;
		} catch (Exception ex) {
			 ex.printStackTrace();
			Tools.writeException(ex);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.releaseConnection("mssql", con);
				}

			} catch (Exception e) {
				Tools.writeException(e);
			}
		}
		return flag;
	}

	/**
	 * sql中没有用户输入的内容，不需要用到?
	 *
	 * @param sql
	 * @return
	 */
	public int exec(String sql) {
		// System.out.println(sqlStr);

		int flag = -2;
		DBConnManager conn = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");
			stmt = con.prepareStatement(sql);
			int value = stmt.executeUpdate();
			flag = value;
		} catch (Exception ex) {
			// ex.printStackTrace();
			Tools.writeException(ex);
			
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.releaseConnection("mssql", con);
				}

			} catch (Exception e) {
				// e.printStackTrace();
				Tools.writeException(e);
			}
		}
		return flag;
	}
}
