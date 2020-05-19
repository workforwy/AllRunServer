package dao.operate;

import dao.conn.*;
import util.*;

import java.sql.*;
import java.util.*;
//import com.raymobile.wap.common.Tools;

/**
 * 读数据库,接受select
 */
public class Select 
{
	Log logger = Log.getInstance();
	//private String sql;
	public Select()
	{	
	}
//	public Select(String sqlcom)
//	{
//		this.sql=sqlcom;		
//	}
//	public List selectRS(String sqlStr)
//	{
//		this.sql = sqlStr;
//		//System.out.println(sql);		
//		return selectRS(); 
//	}
//	
	public List selectRS(String sql)
	{
		List rsall = new ArrayList();
		DBConnManager conn = null;
		Connection  con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map rsTree;
		try{
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");
			stmt = con.createStatement();			
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while(rs.next())
			{
			rsTree = new HashMap(numberOfColumns);
				for(int r=1;r<numberOfColumns+1;r++)
				{
					rsTree.put(rsmd.getColumnName(r),rs.getObject(r));	
					
				}
				rsall.add(rsTree);
			}
			
		}catch(Exception ex){
			Tools.writeException(ex);
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.releaseConnection("mssql",con);
			}catch(Exception e){
				Tools.writeException(e);
			}
		}
		return rsall;
	}

//	public List selectRS(String sqlStr,SetParameter setParameter)
//	{
//		this.sql = sqlStr;
//		//System.out.println(sql);
//		return selectRS(setParameter);
//	}
	public List selectRS(String sql,SetParameter setParameter)
	{
		List rsall = new ArrayList();
		DBConnManager conn = null;
		Connection  con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map rsTree;
		try{
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");
			stmt = con.prepareStatement(sql);
			setParameter.set(stmt);
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while(rs.next())
			{
			rsTree = new HashMap(numberOfColumns);
				for(int r=1;r<numberOfColumns+1;r++)
				{
					rsTree.put(rsmd.getColumnName(r),rs.getObject(r));

				}
				rsall.add(rsTree);
			}

		}catch(Exception ex){
			Tools.writeException(ex);
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.releaseConnection("mssql",con);
			}catch(Exception e){
				Tools.writeException(e);
			}	
		}
		return rsall;
	}
	public String intercept(String str,int num,String last)
	{
		if(str.length()<=num) 
			return str;
		else
			return str.substring(0,num)+last;
	}
	public boolean checklogin(String sql)
	{
		if(!selectRS(sql).isEmpty())
			return true;
		else
			return false;
	}
	public String subURL(String url,String str)
	{
		String link="";
		try{
			String point = "、";
			String[] name = new String[(str.split(point)).length];
			name = str.split(point);
			for(int i = 0;i<name.length;i++)
			{
				if(i==name.length-1)
				{
					point="";
				}
				link+="<a href="+url+name[i]+" target=_blank>"+name[i]+"</a>"+point;
			
			}
		}catch(Exception e){
			Tools.writeException(e);
		}
		return link; 
	}
}
