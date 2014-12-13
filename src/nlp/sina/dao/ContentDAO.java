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
		String sql = "select * from t_content_map where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s'";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst= DBUtil.getRs(stmt, sql);
		BasicInfo binfo = new BasicInfo();
		try {
			if(rst.next()){
				binfo.TotalStatusCount = rst.getInt("TotalStatusCount");
				binfo.RepostedStatusCount = rst.getInt("RepostedStatusCount");
				binfo.OriginalStatusCount = rst.getInt("OriginalStatusCount");
				binfo.StatusCommentCount = rst.getInt("StatusCommentCount");
				binfo.StatusRepostCount = rst.getInt("StatusRepostCount");
				binfo.StatusAttitudeCount = rst.getInt("StatusAttitudeCount");
				binfo.StatusReadingCount = rst.getInt("StatusReadingCount");
				
				binfo.AvgStatusRepostCount = binfo.StatusRepostCount/(float)binfo.TotalStatusCount;
				binfo.AvgStatusAttitudeCount = binfo.StatusAttitudeCount/(float)binfo.TotalStatusCount;
				binfo.AvgStatusCommentCount = binfo.StatusCommentCount/(float)binfo.TotalStatusCount;
				binfo.AvgStatusReadingCount = binfo.StatusReadingCount/(float)binfo.TotalStatusCount;
			}
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
		
		return binfo;
	}
}
