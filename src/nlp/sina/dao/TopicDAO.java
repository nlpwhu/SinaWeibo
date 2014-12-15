package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.model.Topic;
import nlp.sina.util.DBUtil;
import nlp.sina.util.TimeUtil;

public class TopicDAO {
	
	/**
	 * 获取排名从limitIndex开始的limitCount个微话题
	 * @param limitIndex
	 * @param limitCount
	 * @return
	 */
	public List<Topic> getTopicList(int limit){
		List<Topic> topicList = new ArrayList<Topic>();
		
		String sql = "SELECT *  FROM t_topic  ORDER BY statusCount DESC LIMIT %d";
		sql = String.format(sql, limit);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				int id = rst.getInt("id");
				String name = rst.getString("name");
				int statusCount = rst.getInt("statusCount");
				String date_start = rst.getString("date_start");
				String date_end = rst.getString("date_end");
				Topic topic = new Topic(id,name,statusCount,date_start,date_end);
				topicList.add(topic);
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
		return topicList;
		
	}
	
	/**
	 * 获取选定ID的微话题列表
	 * @param idlist
	 * @return
	 */
	public List<Topic> getSelectedTopicList(List<Integer> idlist){
		List<Topic> topicList = new ArrayList<Topic>();
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		try {
			for(Integer topicId:idlist){
				String sql = "select *  from t_topic where id=%d";
				sql = String.format(sql,topicId);
				System.out.println(sql);
				ResultSet rst = DBUtil.getRs(stmt, sql);
				if(rst.next()){
					int id = rst.getInt("id");
					String name = rst.getString("name");
					int totalFreq = rst.getInt("statusCount");
					String date_start = rst.getString("date_start");
					String date_end = rst.getString("date_end");
					Topic topic = new Topic(id,name,totalFreq,date_start,date_end);
					topicList.add(topic);
				}
				rst.close();
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {  
                stmt.close();  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
		}
		return topicList;
		
	}
	
	/**
	 * 获取某个微话题的每天的微博量
	 * @param topicId
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	public Map<String, Integer> getTopicDaysCount(String schoolProvince,String schoolCity,String schoolName, int topicId, String startDate, String endDate, String gender){
		String sql = getTopicInfoQuery(schoolProvince, schoolCity, schoolName, topicId, startDate, endDate, gender);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		Map<String, Integer> map = new HashMap<String, Integer>();
		System.out.println("haha");
		try {
			while(rst.next()){
				Integer count = rst.getInt("statusCount");
				String date = TimeUtil.reformatToSimpleFormatStr(rst.getString("createdDate"));
				System.out.println(count + " " + date);
				map.put(date, count);
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
		return map;
	}
	

	public static String getTopicInfoQuery(String schoolProvince,String schoolCity,String schoolName, int topicId, String startDate, String endDate, String gender){
		String baseQuery = "SELECT COUNT(*) AS statusCount, createdDate FROM t_topic_status WHERE ";
		baseQuery += String.format("topicId='%d' ", topicId);
		// if start and end dates are specified, add to query
		if (!startDate.isEmpty() && !endDate.isEmpty())
			baseQuery += String.format(" AND createdDate BETWEEN '%s' AND '%s '", startDate, endDate);

		if (!gender.isEmpty()){
			baseQuery += String.format(" AND gender='%s' ", gender);
		}
		if (!schoolProvince.isEmpty()) {
			baseQuery += String.format(" AND schoolProvince='%s'", schoolProvince);
		}
		if (!schoolCity.isEmpty()) {
			baseQuery += String.format(" AND schoolCity='%s'", schoolCity);
		}
		if (!schoolName.isEmpty()) {
			baseQuery += String.format(" AND schoolName='%s' ", schoolName);
		}
		
		return baseQuery + "GROUP BY createdDate";
		
	}
	
	/**
	 * 获取某个微话题的某个时间段内的微博数量
	 * @param topicId
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	public int getTopicStatusCountByDate(int quarterId,int topicId,String date_start,String date_end){
		String sql = "select count(*) as statusCount from t_topic_status where quarterId=%d and topicId=%d and createdAt between '%s' and '%s' ";
		sql = String.format(sql,quarterId, topicId,date_start,date_end);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		int count=0;
		try {
			if(rst.next()){
				count = rst.getInt("statusCount");
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
		return count;
	}
	
	public static void main(String[] args){
		TopicDAO topicDAO = new TopicDAO();
		topicDAO.getTopicDaysCount("", "", "", 20515, "2013-01-01", "2014-09-06", "");
	}
}
