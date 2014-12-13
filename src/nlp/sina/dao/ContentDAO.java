package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nlp.sina.model.content.BasicInfo;
import nlp.sina.util.DBUtil;

public class ContentDAO {
	
	/**
	 * 获取微博内容的基本信息，如转发率、原创率等
	 * @return
	 */
	public BasicInfo getBasicInfo(String schoolProvince, String schoolCity, String schoolName, String gender, String startDate, String endDate){
		long startTime=System.currentTimeMillis(); 
		String sqlTotal = "select count(*) as TotalStatusCount from t_status ";
		String sqlReposted = "select count(*) as RepostedStatusCount from t_status ";
		//String sqlOriginal = "select count(*) as OriginalStatusCount from t_status ";
		String sqlCount = "select sum(commentsCount) as StatusCommentCount, sum(repostsCount) as StatusRepostCount, sum(attitudesCount) as StatusAttitudeCount from t_status ";
		//String sqlRepostCount = "select sum(repostsCount) as StatusRepostCount from t_status ";
		//String sqlAttitudeCount = "select sum(attitudesCount) as StatusAttitudeCount from t_status ";
		String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
		if(queryCondition.trim().isEmpty()){
			sqlTotal = sqlTotal + " where createdDate between '%s' and '%s'";
			sqlReposted = sqlReposted + " where retweetedStatusId != '' and createdDate between '%s' and '%s'";
			//sqlOriginal = sqlOriginal + " where retweetedStatusId = '' and createdDate between '%s' and '%s'";
			sqlCount = sqlCount + " where createdDate between '%s' and '%s'";
			//sqlRepostCount = sqlRepostCount + " where createdDate between '%s' and '%s'";
			//sqlAttitudeCount = sqlAttitudeCount + " where createdDate between '%s' and '%s'";
		}
		else{
			sqlTotal = sqlTotal + queryCondition + " and createdDate between '%s' and '%s'";
			sqlReposted = sqlReposted + queryCondition + " and retweetedStatusId != '' and createdDate between '%s' and '%s'";
			//sqlOriginal = sqlOriginal + queryCondition + " and retweetedStatusId = '' and createdDate between '%s' and '%s'";
			sqlCount = sqlCount + queryCondition + " and createdDate between '%s' and '%s'";
			//sqlRepostCount = sqlRepostCount + queryCondition + " and createdDate between '%s' and '%s'";
			//sqlAttitudeCount = sqlAttitudeCount + queryCondition + " and createdDate between '%s' and '%s'";
		}
		sqlTotal = String.format(sqlTotal, startDate, endDate);
		sqlReposted = String.format(sqlReposted, startDate, endDate);
		//sqlOriginal = String.format(sqlOriginal, startDate, endDate);
		sqlCount = String.format(sqlCount, startDate, endDate);
		//sqlRepostCount = String.format(sqlRepostCount, startDate, endDate);
		//sqlAttitudeCount = String.format(sqlAttitudeCount, startDate, endDate);
		
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		System.out.println("\n" + sqlTotal);
		ResultSet rst= DBUtil.getRs(stmt, sqlTotal);
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
			/*System.out.println("\n" + sqlOriginal);
			rst = DBUtil.getRs(stmt, sqlOriginal);
			if(rst.next()){
				binfo.OriginalStatusCount = rst.getInt("OriginalStatusCount");
			}*/
			System.out.println("\n" + sqlCount);
			rst = DBUtil.getRs(stmt, sqlCount);
			if(rst.next()){
				binfo.StatusCommentCount = rst.getInt("StatusCommentCount");
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
			}
			/*System.out.println("\n" + sqlRepostCount);
			rst = DBUtil.getRs(stmt, sqlRepostCount);
			if(rst.next()){
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
			}
			System.out.println("\n" + sqlAttitudeCount);
			rst = DBUtil.getRs(stmt, sqlAttitudeCount);
			if(rst.next()){
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
			}*/
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
