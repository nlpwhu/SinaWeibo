package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nlp.sina.configure.Configure;
import nlp.sina.model.KeyWord;
import nlp.sina.model.Status;
import nlp.sina.util.DBUtil;
import nlp.sina.util.TimeUtil;

public class GroupPortraitDAO {
	
	public List<Status> selectRelativeStatus(String keywordStr){
		List<Status> statusList = new ArrayList<Status>();
		String[] keywordArray = keywordStr.split("-");
		String sql = "select * from t_status where createdAt between '%s' and '%s' ";
		sql = String.format(sql, Configure.StartTime,Configure.EndTime);
		for(String keyword:keywordArray){
			sql +=" and (text like'%"+keyword+"%' or ";
			sql +="retweetedText like'%"+keyword+"%') ";
		}
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt =  DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				Status status = new Status();
				fillResultToStatus(rst,status);
				statusList.add(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return statusList;
	}
	
	public void InsertGroupPortrait(String keywordStr){
		String sql="insert into t_group_portrait(name) values('%s')";
		sql = String.format(sql, keywordStr);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
	}
	
	public List<KeyWord> searchGroupPortraitKeyword(String keywordStr){
		List<KeyWord> list = new ArrayList<KeyWord>();
		String sql = "select * from t_group_portrait_keyword where gpName='%s'";
		sql = String.format(sql, keywordStr);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				KeyWord k = new KeyWord(rst.getString("keywordName"),rst.getFloat("weight"));
				list.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}
	
	public void InsertGroupPortraitKeyword(String keywordStr,List<KeyWord> keywordList){
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		try {
			for(KeyWord keyword:keywordList){
				String sql="insert into t_group_portrait_keyword(gpName,keywordName,weight) values('%s','%s','%f')";
				sql = String.format(sql, keywordStr,keyword.name,keyword.score);
				System.out.println(sql);
				stmt.execute(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
	}
	
	
	public List<String> getGroupPortraitNameList(){
		List<String> list = new ArrayList<String>();
		String sql="select name from t_group_portrait";
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				list.add(rst.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return list;
				
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
}
