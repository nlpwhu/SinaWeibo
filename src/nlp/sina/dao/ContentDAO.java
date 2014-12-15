package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nlp.sina.model.content.BasicInfo;
import nlp.sina.util.DBUtil;

import nlp.sina.configure.Configure;
import nlp.sina.configure.Content_Configure;

public class ContentDAO {
	
	/**
	 * 获取微博内容的基本信息，如转发率、原创率等
	 * @return
	 */
	public BasicInfo getBasicInfo(String schoolProvince, String schoolCity, String schoolName, String gender, String startDate, String endDate){
		
		long startTime=System.currentTimeMillis(); 
		String sqlTotal = "select count(*) as TotalStatusCount from t_status ";
		String sqlReposted = "select count(*) as RepostedStatusCount from t_status ";
		 
		String sqlCount = "select sum(commentsCount) as StatusCommentCount, sum(repostsCount) as StatusRepostCount, sum(attitudesCount) as StatusAttitudeCount from t_status ";
		 
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		if(queryCondition.trim().isEmpty()){
			sqlTotal = sqlTotal + " where createdDate between '%s' and '%s'";
			sqlReposted = sqlReposted + " where retweetedStatusId != '' and createdDate between '%s' and '%s'";
			 
			sqlCount = sqlCount + " where createdDate between '%s' and '%s'";
			 
		}
		else{
			sqlTotal = sqlTotal + queryCondition + " and createdDate between '%s' and '%s'";
			sqlReposted = sqlReposted + queryCondition + " and retweetedStatusId != '' and createdDate between '%s' and '%s'";
			 
			sqlCount = sqlCount + queryCondition + " and createdDate between '%s' and '%s'";
			 
		}
		sqlTotal = String.format(sqlTotal, startDate, endDate);
		sqlReposted = String.format(sqlReposted, startDate, endDate);
		 
		sqlCount = String.format(sqlCount, startDate, endDate);
		 
		
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		if(schoolProvince == "" && schoolCity == "" && schoolName == "" && gender == "" && startDate == Configure.StartTime && endDate == Configure.EndTime){
			String sql = "select * from t_content_map";
			System.out.println("在静态页面内容总表里面查询\n" + sql);
			ResultSet rst= DBUtil.getRs(stmt, sql);
		    BasicInfo binfo = new BasicInfo();
		    if(rst.next){
		    	binfo.TotalStatusCount = rst.getInt("TotalStatusCount");
		    	binfo.RepostedStatusCount = rst.getInt("RepostedStatusCount");
			    binfo.StatusCommentCount = rst.getInt("StatusCommentCount");
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
				binfo.OriginalStatusCount = binfo.TotalStatusCount - binfo.RepostedStatusCount;
				binfo.AvgStatusRepostCount = binfo.StatusRepostCount/(float)binfo.TotalStatusCount;
				return binfo;
		    }
		    else{
		    	System.out.println("\n" + sqlTotal);
		    	rst = DBUtil.getRs(stmt, sqlTotal);
		    	if(rst.next()){
		    		binfo.TotalStatusCount = rst.getInt("TotalStatusCount");
		    	}
		    	System.out.println("\n" + sqlReposted);
			rst = DBUtil.getRs(stmt, sqlReposted);
			if(rst.next()){
				binfo.RepostedStatusCount = rst.getInt("RepostedStatusCount");
			}
			 
			System.out.println("\n" + sqlCount);
			rst = DBUtil.getRs(stmt, sqlCount);
			if(rst.next()){
				binfo.StatusCommentCount = rst.getInt("StatusCommentCount");
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
			}
			 
			binfo.OriginalStatusCount = binfo.TotalStatusCount - binfo.RepostedStatusCount;
			binfo.AvgStatusRepostCount = binfo.StatusRepostCount/(float)binfo.TotalStatusCount;
		    }
		    sql = "insert into t_content_map(TotalStatusCount, RepostedStatusCount, StatusCommentCount, StatusRepostCount, StatusAttitudeCount) values(%d, %d, %d, %d, %d)";
		    DBUtil.executeSQL(stmt, sql);
		    return binfo;
		}
		System.out.println("\n" + sqlTotal);
		ResultSet rst = DBUtil.getRs(stmt, sqlTotal);
		BasicInfo binfo = new BasicInfo();
		try {
			if(rst.next()){
				binfo.TotalStatusCount = rst.getInt("TotalStatusCount");
			    
			}
			System.out.println("\n" + sqlReposted);
			rst = DBUtil.getRs(stmt, sqlReposted);
			if(rst.next()){
				binfo.RepostedStatusCount = rst.getInt("RepostedStatusCount");
			}
			 
			System.out.println("\n" + sqlCount);
			rst = DBUtil.getRs(stmt, sqlCount);
			if(rst.next()){
				binfo.StatusCommentCount = rst.getInt("StatusCommentCount");
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
			}
			 
			binfo.OriginalStatusCount = binfo.TotalStatusCount - binfo.RepostedStatusCount;
			binfo.AvgStatusRepostCount = binfo.StatusRepostCount/(float)binfo.TotalStatusCount;
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {  
                rst.close();  
                stmt.close();  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
		}
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： "+ (endTime-startTime) +"ms"); 
		return binfo;
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

}
