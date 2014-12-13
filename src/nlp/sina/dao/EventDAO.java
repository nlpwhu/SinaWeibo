package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.model.Event;
import nlp.sina.model.KeyWord;
import nlp.sina.util.DBUtil;
import nlp.sina.util.TimeUtil;

public class EventDAO {
	
	
	/**
	 * 函数作用：获取内容本体事件列表
	 */
	public List<Event> getEventList_Content(String schoolProvince, String schoolCity, String schoolName, String gender){
		List<Event> eventList = new ArrayList<Event>();
		String sql = "select * from t_prefocus_map where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by prefocusId ";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);
		System.out.println("查询t prefocus map:\n" + sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				rst.beforeFirst();
				System.out.println("在预设话题map表里面查询到这条记录\n");
				while(rst.next()){
					
					int eventId = rst.getInt("prefocusId");
					String eventName = rst.getString("name");
					int statusCount = rst.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName, eventId, statusCount);
					eventList.add(event);
				}
		    }
		    else{
		    	String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		    	if(queryCondition.trim().isEmpty()){
		    		queryCondition = "where t_prefocus.id = t_prefocus_status.prefocusId";
		    	}
		    	else{
		    		queryCondition = queryCondition + " and t_prefocus.id = t_prefocus_status.prefocusId";
		    	}
		    	String sqlPrefocus = "select prefocusId, name, count(*) as number from t_prefocus_status, t_prefocus " + queryCondition + " group by prefocusId order by prefocusId";
		    	System.out.println("在预设话题总表里面统计各个话题的数量\n" + sqlPrefocus);
		    	Statement stmtp = DBUtil.createStmt(conn);
		    	ResultSet rstp = DBUtil.getRs(stmt, sqlPrefocus);
		    	while(rstp.next()){
		    		int eventId = rstp.getInt("prefocusId");
					String eventName = rstp.getString("name");
					int statusCount = rstp.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName,eventId,statusCount);
					eventList.add(event);
					sql = "insert into t_prefocus_map values('%s', '%s', '%s', '%s', %d, '%s', %d)";
					sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, rstp.getInt("prefocusId"), rstp.getString("name"), rstp.getInt("number"));
					System.out.println("插入一条记录\n" + sql);
					DBUtil.executeSQL(stmtp, sql);
		    	}
		    	DBUtil.closeRs(rstp);
		    	DBUtil.closeStmt(stmtp);
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
		return eventList;
	}
	/**
	 * 函数作用：获取心理本体事件列表
	 */
	public List<Event> getEventList_Psychology(String schoolProvince,String schoolCity,String schoolName,String gender){
		List<Event> eventList = new ArrayList<Event>();
		String sql = "select * from t_psychology_map where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by psychologyId";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);
		System.out.println("从心理map里面查询相关记录:\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				System.out.println("在心理map表里面查询到这条记录\n");
				rst.beforeFirst();
				while(rst.next()){
					int eventId = rst.getInt("psychologyId");
					String eventName = rst.getString("name");
					int statusCount = rst.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName,eventId,statusCount);
					eventList.add(event);
			    }
			}
			else{
				String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
				if(queryCondition.trim().isEmpty()){
					queryCondition = " where t_psychology.id = t_psychology_status.psychologyId";
				}
				else{
					queryCondition = queryCondition + " and t_psychology.id = t_psychology_status.psychologyId";
				}
				String psychologySql = "select psychologyId, name, count(*) as number from t_psychology, t_psychology_status" + queryCondition + " group by psychologyId order by psychologyId";
				System.out.println("在心理总表里面查询\n" + psychologySql);
				Statement stmtp = DBUtil.createStmt(conn);
				ResultSet rstp = DBUtil.getRs(stmt, psychologySql);
				while(rstp.next()){
					int eventId = rstp.getInt("psychologyId");
					String eventName = rstp.getString("name");
					int statusCount = rstp.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName,eventId,statusCount);
					eventList.add(event);
					sql = "insert into t_psychology_map values('%s', '%s', '%s', '%s', %d, '%s', %d)";
					sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, rstp.getInt("psychologyId"), rstp.getString("name"), rstp.getInt("number"));
					System.out.println("插入一条记录\n" + sql);
					DBUtil.executeSQL(stmtp, sql);
				}
				DBUtil.closeStmt(stmtp);
				DBUtil.closeRs(rstp);
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
		return eventList;
	}


	/**
	 * 函数作用：获取正能量本体事件列表
	 */
	public List<Event> getEventList_PE(String schoolProvince,String schoolCity,String schoolName,String gender){
		List<Event> eventList = new ArrayList<Event>();
		String sql = "select * from t_penergy_map where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by penergyId"; 
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);
		System.out.println("查询正能量map表:\n" + sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				rst.beforeFirst();
				System.out.println("在正能量map表里面查询到这条记录\n");
				while(rst.next()){
					int eventId = rst.getInt("penergyId");
					String eventName = rst.getString("name");
					int statusCount = rst.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName, eventId, statusCount);
					eventList.add(event);
				}
			}
			else{
				String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
				if(queryCondition.trim().isEmpty()){
					queryCondition = "where t_penergy_status.penergyId = t_penergy.id";
				}
				else{
					queryCondition = queryCondition + " and t_penergy_status.penergyId = t_penergy.id";
				}
				String penergySql = "select penergyId, name, count(*) as number from t_penergy, t_penergy_status " + queryCondition + " group by penergyId order by penergyId";
				System.out.println("在正能量总表里面查询\n" + penergySql);
				Statement stmtp =  DBUtil.createStmt(conn);
				ResultSet rstp = DBUtil.getRs(stmt, penergySql);
				while(rstp.next()){
 					int eventId = rstp.getInt("penergyId");
					String eventName = rstp.getString("name");
					int statusCount = rstp.getInt("number");
					//int type = rst.getInt("type");
					Event event = new Event(eventName, eventId, statusCount);
					eventList.add(event);
					sql = "insert into t_penergy_map values('%s', '%s', '%s', '%s', %d, '%s', %d)";
					sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, rstp.getInt("penergyId"), rstp.getString("name"), rstp.getInt("number"));
					DBUtil.executeSQL(stmtp, sql);
					System.out.println("插入一条记录\n" + sql);

				}
				DBUtil.closeStmt(stmtp);
				DBUtil.closeRs(rstp);
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
		return eventList;
	}
	/**
	 * 根据本体事件ID，获取本体事件具体信息
	 * @param eventId
	 * @return
	 */
	public Event getEventById(int quarterId,int eventId){
		
		Event event=null;
		String sql = "select * from t_event where quarterId=%d and id=%d";
		sql = String.format(sql, quarterId,eventId);
		System.out.println("查询event by id:\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				String eventName = rst.getString("name");
				int statusCount = rst.getInt("statusCount");
				//int type = rst.getInt("type");
				event = new Event(eventName, eventId, statusCount);
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
		return event;
	}

	/**
	 * 获取本体事件的每天的微博量
	 */
	public Map<String,Integer> getContentDaysCount(String schoolProvince, String schoolCity, String schoolName, String gender, int id, String startDate, String endDate){
		Map<String,Integer> map = new HashMap<String,Integer>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		String sql;
		if(queryCondition.trim().isEmpty()){
			sql = "select createdDate, count(*) as number from t_prefocus_status where prefocusId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}else{
			sql = "select createdDate, count(*) as number from t_prefocus_status " + queryCondition + " and prefocusId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}
		sql = String.format(sql, id, startDate, endDate);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		
		try {
			while(rst.next()){
				Integer count = rst.getInt("number");
				String date = TimeUtil.reformatToSimpleFormatStr(rst.getString("createdDate"));
				map.put(date,count);
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
	
	/**
	 * 获取正能量本体事件的每天的微博量
	 */
	public Map<String,Integer> getPenergyDaysCount(String schoolProvince, String schoolCity, String schoolName, String gender, int id, String startDate, String endDate){
		Map<String,Integer> map = new HashMap<String,Integer>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		String sql;
		if(queryCondition.trim().isEmpty()){
			sql = "select createdDate, count(*) as number from t_penergy_status where penergyId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}else{
			sql = "select createdDate, count(*) as number from t_penergy_status " + queryCondition + " and penergyId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}
		sql = String.format(sql, id, startDate, endDate);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		
		try {
			while(rst.next()){
				Integer count = rst.getInt("number");
				String date = TimeUtil.reformatToSimpleFormatStr(rst.getString("createdDate"));
				map.put(date,count);
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
	
	/**
	 * 获取心理本体事件的每天的微博量
	 */
	public Map<String,Integer> getPsychologyDaysCount(String schoolProvince, String schoolCity, String schoolName, String gender, int id, String startDate, String endDate){
		Map<String,Integer> map = new HashMap<String,Integer>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		String sql;
		if(queryCondition.trim().isEmpty()){
			sql = "select createdDate, count(*) as number from t_psychology_status where psychologyId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}else{
			sql = "select createdDate, count(*) as number from t_psychology_status " + queryCondition + " and psychologyId = %d and createdDate between '%s' and '%s' group by createdDate order by createdDate";
		}
		sql = String.format(sql, id, startDate, endDate);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		
		try {
			while(rst.next()){
				Integer count = rst.getInt("number");
				String date = TimeUtil.reformatToSimpleFormatStr(rst.getString("createdDate"));
				map.put(date,count);
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
	

	/**
	 * 获取某个本体事件在某段时间内的微博数量
	 * @param eventId
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	public int getEventCountByDate(int quarterId,int eventId,String date_start,String date_end){
		String sql = "select count(*) as statusCount from t_event_status where quarterId=%d and eventId=%d and createdAt between '%s' and '%s' ";
		sql = String.format(sql,quarterId, eventId,date_start,date_end);
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
	
	/**
	 * 获取本体事件的关键词列表
	 * @param eventId
	 * @return
	 */
	public List<KeyWord> getPenergyKeywordListById(int eventId){
		List<KeyWord> klist = new ArrayList<KeyWord>();
		String sql = "select *  from t_penergy_keyword where id = %d order by freq desc";
		sql = String.format(sql, eventId);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				KeyWord keyword = new KeyWord(rst.getString("keywordName"),rst.getInt("freq"));
				klist.add(keyword);
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
		return klist;
	}
	
	public List<KeyWord> getPsychologyKeywordListById(int eventId){
		List<KeyWord> klist = new ArrayList<KeyWord>();
		String sql = "select *  from t_psychology_keyword where id = %d order by freq desc";
		sql = String.format(sql, eventId);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				KeyWord keyword = new KeyWord(rst.getString("keywordName"),rst.getInt("freq"));
				klist.add(keyword);
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
		return klist;
	}
	
	public List<KeyWord> getPrefocusKeywordListById(int eventId){
		List<KeyWord> klist = new ArrayList<KeyWord>();
		String sql = "select *  from t_prefocus_keyword where id = %d order by freq desc";
		sql = String.format(sql, eventId);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				KeyWord keyword = new KeyWord(rst.getString("keywordName"),rst.getInt("freq"));
				klist.add(keyword);
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
		return klist;
	}

	public static String processWhereQuery(String schoolProvince,String schoolCity,String schoolName,String gender){
		String query = new String();
		if (schoolProvince.isEmpty() && schoolCity.isEmpty() && schoolName.isEmpty() && gender.isEmpty())
			return query;
		else {
			if (!schoolProvince.isEmpty())
				query = String.format("schoolProvince='%s'", schoolProvince);
			if (!schoolCity.isEmpty()) {
				if (!query.isEmpty()) query += " AND ";
				query += String.format("schoolCity='%s'", schoolCity);
			}
			if (!schoolName.isEmpty()) {
				if (!query.isEmpty()) query += " AND ";
				query += String.format("schoolName='%s'", schoolName);
			}
			if(!gender.isEmpty()) {
				if (!query.isEmpty()) query += " AND ";
				query += String.format("gender='%s'", gender);
			}
			return " where " + query;
		}
	}

	
	public static void main(String[] args){
		
	}
}
