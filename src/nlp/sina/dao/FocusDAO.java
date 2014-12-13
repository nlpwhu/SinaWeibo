package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nlp.sina.model.Focus;
import nlp.sina.model.KeyWord;
import nlp.sina.model.Topic;
import nlp.sina.util.DBUtil;


public class FocusDAO {
	/**
	 * 获取最终抽取的话题的关键词列表
	 * @param focusId
	 * @return
	 */
	public List<KeyWord> getKeywordListById(int quarterId,int focusId){
		List<KeyWord> klist = new ArrayList<KeyWord>();
		String sql = "select *  from t_focus_keyword_finally where quarterId=%d and focusId=%d order by freq desc";
		sql = String.format(sql,quarterId, focusId);
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
	
	/**
	 * 获取最终抽取的话题列表，取排名limitIndex开始的limitCount个话题
	 * @param limitIndex
	 * @param limitCount
	 * @return
	 */
	public List<Focus> getFinallyFocusList(int quarterId,int limitIndex,int limitCount){
		List<Focus> focusList = new ArrayList<Focus>();
		
		String sql = "select *  from t_focus_finally where quarterId=%d order by totalFreq desc limit %d,%d";
		sql = String.format(sql, quarterId,limitIndex,limitCount);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				int id = rst.getInt("id");
				String name = rst.getString("name");
				int totalFreq = rst.getInt("totalFreq");
				String date_start = rst.getString("date_start");
				String date_end = rst.getString("date_end");
				Focus focus = new Focus(id,name,totalFreq,date_start,date_end);
				focusList.add(focus);
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
		return focusList;
		
	}
	/**
	 * 获取选定ID的最终话题列表
	 * @param idlist
	 * @return
	 */
	public List<Focus> getSelectedTopicList(int quarterId,List<Integer> idlist){
		List<Focus> focusList = new ArrayList<Focus>();
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		try {
			for(Integer topicId:idlist){
				String sql = "select *  from t_focus_finally where quarterId=%d and id=%d";
				sql = String.format(sql,quarterId, topicId);
				System.out.println(sql);
				ResultSet rst = DBUtil.getRs(stmt, sql);
				if(rst.next()){
					int id = rst.getInt("id");
					String name = rst.getString("name");
					int index=name.indexOf("(");
					if(index!=-1)
						name = name.substring(0,index);
					int totalFreq = rst.getInt("totalFreq");
					String date_start = rst.getString("date_start");
					String date_end = rst.getString("date_end");
					Focus focus = new Focus(id,name,totalFreq,date_start,date_end);
					focusList.add(focus);
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
		return focusList;
		
	}
	/**
	 * 获取某个最终话题的每天的微博量
	 * @param topicId
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	public List<Integer> getFocusDaysCount(int quarterId,int focusId){
		String sql = "select * from t_focus_daycount_finally where quarterId=%d and focusId=%d order by day";
		sql = String.format(sql,quarterId, focusId);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		List<Integer> list = new ArrayList<Integer>();
		try {
			while(rst.next()){
				Integer count = rst.getInt("statusCount");
				list.add(count);
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
		return list;
	}
	/**
	 * 获取某个最终话题在一个时间段内的微博数量
	 * @param focusId
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	public int getFocusStatusCountByDate(int quarterId,int focusId,String date_start,String date_end){
		String sql = "select count(*) as statusCount from t_focus_status_finally where quarterId=%d and focusId=%d and createdAt between '%s' and '%s' ";
		sql = String.format(sql, quarterId,focusId,date_start,date_end);
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
	
}
