package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.model.Status;
import nlp.sina.util.DBUtil;
import nlp.sina.util.TimeUtil;

public class StatusDAO {
	
	/**
	 * 函数作用:根据微博的id，获取微博对象Status
	 * @param id
	 * @return
	 */
	public Status getStatusById(String id){
		
		Status status = new Status();
		String sql = "select * from t_status where id="+id;
		Connection conn = DBUtil.getConn();
		Statement stmt =  DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				fillResultToStatus(rst,status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {  
                rst.close();  
                stmt.close();  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
		}
		
		return status;
	}
	
	/*
	 * 函数作用：将数据库检索的结果集转换成Status类对象
	 */
	public static void fillResultToStatus(ResultSet rst,Status status){
		try {
			status.setAnnotations(rst.getString("annotations").trim());
			status.setAttitudesCount(rst.getInt("attitudesCount"));
			status.setCommentsCount(rst.getInt("commentsCount"));
			status.setCreatedAt(TimeUtil.parseDate(rst.getString("createdAt"), TimeUtil.detailFormatStr));
			status.setFavorited(rst.getBoolean("favorited"));
			status.setGeoed(rst.getBoolean("geoed"));
			status.setId(rst.getString("id"));
			status.setLatitude(rst.getDouble("latitude"));
			status.setLongitude(rst.getDouble("longitude"));
			status.setMid(rst.getString("mid"));
			status.setPictureed(rst.getBoolean("pictureed"));
			status.setReadersCount(rst.getInt("readersCount"));
			status.setRepostsCount(rst.getInt("repostsCount"));
			status.setRetweetedStatusId(rst.getString("retweetedStatusId"));
			status.setRetweetedText(rst.getString("retweetedText"));
			status.setSourceName(rst.getString("sourceName").trim());
			status.setText(rst.getString("text").trim());
			status.setUid(rst.getString("uid"));
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
	}
	
}
