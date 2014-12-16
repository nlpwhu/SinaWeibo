package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.configure.Configure;
import nlp.sina.model.Event;
import nlp.sina.model.KeyWord;
import nlp.sina.util.DBUtil;
import nlp.sina.util.TimeUtil;

public class EventDAO {
	
	
	/**
	 * 函数作用：获取内容本体事件列表
	 */
	public List<Event> getEventList_Content(String schoolProvince, String schoolCity, String schoolName, String gender, String startDate, String endDate){
		
		List<Event> eventList = new ArrayList<Event>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
        if(queryCondition.trim().isEmpty()){
    		queryCondition = "where t_prefocus.id = t_prefocus_status.prefocusId and createdDate between '%s' and '%s'";
    	}
    	else{
    		queryCondition = queryCondition + " and t_prefocus.id = t_prefocus_status.prefocusId and createdDate between '%s' and '%s'";
    	}
    	String sqlPrefocus = "select prefocusId, name, count(*) as number from t_prefocus_status, t_prefocus " + queryCondition + " group by prefocusId order by prefocusId";
    	sqlPrefocus = String.format(sqlPrefocus, startDate, endDate);
    	
    	Connection conn = DBUtil.getConn();
    	Statement stmt = DBUtil.createStmt(conn);
    	if(schoolProvince.isEmpty() && schoolCity.isEmpty() && schoolName.isEmpty() && gender.isEmpty() && startDate.equals(Configure.StartTime) && endDate.equals(Configure.EndTime)){
    		String sql = "select * from t_prefocus_map order by prefocusId";
    		System.out.println("静态页面查询\n" + sql);
    		ResultSet rstp = DBUtil.getRs(stmt, sql);
    		try {
				if(rstp.next()){
					rstp.beforeFirst();
					while(rstp.next()){
						int eventId = rstp.getInt("prefocusId");
						String eventName = rstp.getString("name");
						int statusCount = rstp.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
					}
					
				}
				else{
					Statement stmtp = DBUtil.createStmt(conn);
					System.out.println("在预设话题总表里面统计各个话题的数量\n" + sqlPrefocus);
					ResultSet rst = DBUtil.getRs(stmt, sqlPrefocus);
					while(rst.next()){
						int eventId = rst.getInt("prefocusId");
						String eventName = rst.getString("name");
						int statusCount = rst.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
						sql = "insert into t_prefocus_map(prefocusId, name, number) values(%d, '%s', %d)";
						sql = String.format(sql, eventId, eventName, statusCount);
						System.out.println("插入一条记录\n" + sql);
						DBUtil.executeSQL(stmtp, sql);
					}
					DBUtil.closeRs(rst);
					DBUtil.closeStmt(stmtp);
						
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				DBUtil.closeRs(rstp);
				DBUtil.closeStmt(stmt);
				DBUtil.closeConn(conn);
			}
    		return eventList;
    	}
    	System.out.println("在预设话题总表里面统计各个话题的数量\n" + sqlPrefocus);
    	ResultSet rstp = DBUtil.getRs(stmt, sqlPrefocus);
    	try {
			while(rstp.next()){
				int eventId = rstp.getInt("prefocusId");
				String eventName = rstp.getString("name");
				int statusCount = rstp.getInt("number");
				Event event = new Event(eventName,eventId,statusCount);
				eventList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rstp);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
    	
    	return eventList;
	}
	/**
	 * 函数作用：获取心理本体事件列表
	 */
	public List<Event> getEventList_Psychology(String schoolProvince, String schoolCity, String schoolName, String gender, String startDate, String endDate){
		
		List<Event> eventList = new ArrayList<Event>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
        if(queryCondition.trim().isEmpty()){
    		queryCondition = "where t_psychology.id = t_psychology_status.psychologyId and createdDate between '%s' and '%s'";
    	}
    	else{
    		queryCondition = queryCondition + " and t_psychology.id = t_psychology_status.psychologyId and createdDate between '%s' and '%s'";
    	}
    	String sqlPsychology = "select psychologyId, name, count(*) as number from t_psychology, t_psychology_status " + queryCondition + " group by psychologyId order by psychologyId";
    	sqlPsychology = String.format(sqlPsychology, startDate, endDate);
    	
    	Connection conn = DBUtil.getConn();
    	Statement stmt = DBUtil.createStmt(conn);
    	if(schoolProvince.isEmpty() && schoolCity.isEmpty() && schoolName.isEmpty() && gender.isEmpty() && startDate.equals(Configure.StartTime) && endDate.equals(Configure.EndTime)){
    		String sql = "select * from t_psychology_map order by psychologyId";
    		System.out.println("静态页面查询\n" + sql);
    		ResultSet rstp = DBUtil.getRs(stmt, sql);
    		try {
				if(rstp.next()){
					rstp.beforeFirst();
					while(rstp.next()){
						int eventId = rstp.getInt("psychologyId");
						String eventName = rstp.getString("name");
						int statusCount = rstp.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
					}
					
				}
				else{
					Statement stmtp = DBUtil.createStmt(conn);
					System.out.println("在心理总表里面统计各个话题的数量\n" + sqlPsychology);
					ResultSet rst = DBUtil.getRs(stmt, sqlPsychology);
					while(rst.next()){
						int eventId = rst.getInt("psychologyId");
						String eventName = rst.getString("name");
						int statusCount = rst.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
						sql = "insert into t_psychology_map(psychologyId, name, number) values(%d, '%s', %d)";
						sql = String.format(sql, eventId, eventName, statusCount);
						System.out.println("插入一条记录\n" + sql);
						DBUtil.executeSQL(stmtp, sql);
					}
					DBUtil.closeRs(rst);
					DBUtil.closeStmt(stmtp);
						
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				DBUtil.closeRs(rstp);
				DBUtil.closeStmt(stmt);
				DBUtil.closeConn(conn);
			}
    		return eventList;
    	}
    	System.out.println("在心理总表里面统计各个话题的数量\n" + sqlPsychology);
    	ResultSet rstp = DBUtil.getRs(stmt, sqlPsychology);
    	try {
			while(rstp.next()){
				int eventId = rstp.getInt("psychologyId");
				String eventName = rstp.getString("name");
				int statusCount = rstp.getInt("number");
				Event event = new Event(eventName, eventId, statusCount);
				eventList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rstp);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
    	
    	return eventList;
	}

	

	/**
	 * 函数作用：获取正能量本体事件列表
	 */
	public List<Event> getEventList_PE(String schoolProvince, String schoolCity, String schoolName, String gender, String startDate, String endDate){
		
		List<Event> eventList = new ArrayList<Event>();
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
        if(queryCondition.trim().isEmpty()){
    		queryCondition = "where t_penergy_status.penergyId = t_penergy.id and createdDate between '%s' and '%s'";
    	}
    	else{
    		queryCondition = queryCondition + " and t_penergy_status.penergyId = t_penergy.id and createdDate between '%s' and '%s'";
    	}
    	String sqlPenergy = "select penergyId, name, count(*) as number from t_penergy, t_penergy_status " + queryCondition + " group by penergyId order by penergyId";
    	sqlPenergy = String.format(sqlPenergy, startDate, endDate);
    	
    	Connection conn = DBUtil.getConn();
    	Statement stmt = DBUtil.createStmt(conn);
    	if(schoolProvince.isEmpty() && schoolCity.isEmpty() && schoolName.isEmpty() && gender.isEmpty() && startDate.equals(Configure.StartTime) && endDate.equals(Configure.EndTime)){
    		String sql = "select * from t_penergy_map order by penergyId";
    		System.out.println("静态页面查询\n" + sql);
    		ResultSet rstp = DBUtil.getRs(stmt, sql);
    		try {
				if(rstp.next()){
					rstp.beforeFirst();
					while(rstp.next()){
						int eventId = rstp.getInt("penergyId");
						String eventName = rstp.getString("name");
						int statusCount = rstp.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
					}
					
				}
				else{
					Statement stmtp = DBUtil.createStmt(conn);
					System.out.println("在正能量总表里面统计各个话题的数量\n" + sqlPenergy);
					ResultSet rst = DBUtil.getRs(stmt, sqlPenergy);
					while(rst.next()){
						int eventId = rst.getInt("penergyId");
						String eventName = rst.getString("name");
						int statusCount = rst.getInt("number");
						Event event = new Event(eventName, eventId, statusCount);
						eventList.add(event);
						sql = "insert into t_penergy_map(penergyId, name, number) values(%d, '%s', %d)";
						sql = String.format(sql, eventId, eventName, statusCount);
						System.out.println("插入一条记录\n" + sql);
						DBUtil.executeSQL(stmtp, sql);
					}
					DBUtil.closeRs(rst);
					DBUtil.closeStmt(stmtp);
						
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				DBUtil.closeRs(rstp);
				DBUtil.closeStmt(stmt);
				DBUtil.closeConn(conn);
			}
    		return eventList;
    	}
    	System.out.println("在正能量总表里面统计各个话题的数量\n" + sqlPenergy);
    	ResultSet rstp = DBUtil.getRs(stmt, sqlPenergy);
    	try {
			while(rstp.next()){
				int eventId = rstp.getInt("penergyId");
				String eventName = rstp.getString("name");
				int statusCount = rstp.getInt("number");
				Event event = new Event(eventName,eventId,statusCount);
				eventList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rstp);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
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
