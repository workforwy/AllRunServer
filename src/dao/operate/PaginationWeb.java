package dao.operate;

import dao.conn.*;
import util.*;

import java.sql.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import com.raymobile.wap.common.Tools;

/**
 * web 上的分页
 */
public class PaginationWeb {
	private String sql;
	private int intPageSize; // 每页行数
	private int intRowCount;
	private int intPage; // 页号
	private String Counter;

	public PaginationWeb() {

	}

	
/**
 * 
 * @param sql
 * @param pageIndex 第几页,第1页从1开始
 * @param rownum   每页有几行
 * @return
 */
	public List selectRS(String sql, int pageIndex, int rownum) {
		this.sql = sql;
		this.intPage = pageIndex;
		this.intPageSize = rownum;
		return selectRS();
	}

	public List selectRS() {
		List rsall = new ArrayList();
		Map rsTree;
		DBConnManager conn = null;
		Connection con = null;

		Statement st2 = null;
		ResultSet rs = null;
	
		try {
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");			

			st2 = con.createStatement();
			sql += " limit " + (intPage - 1) * intPageSize + "," + intPageSize;
			rs = st2.executeQuery(sql);
			// System.out.println(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while (rs.next()) {
				rsTree = new HashMap(numberOfColumns);
				for (int r = 1; r < numberOfColumns + 1; r++) {
					rsTree.put(rsmd.getColumnName(r), rs.getObject(r));
				}
				rsall.add(rsTree);
			}
		} catch (Exception ex) {
			Tools.writeException(ex);
		} finally {
			try {
				
				if (rs != null)
					rs.close();
				if (st2 != null)
					st2.close();
				if (conn != null) {
					conn.releaseConnection("mssql", con);
				}
			} catch (Exception e) {
				Tools.writeException(e);
			}
		}
		return rsall;
	}

	

	
	public int getTotal() {
		return intRowCount;
	}

	
	
	
	

	

}
