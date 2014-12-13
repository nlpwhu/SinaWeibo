package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nlp.sina.model.behaviour.PhoneBrand;
import nlp.sina.model.behaviour.ReleaseForm;
import nlp.sina.model.behaviour.SourceName;
import nlp.sina.model.behaviour.TimePeriod;
import nlp.sina.util.DBUtil;

public class BehaviourDAO {
	/**
	 * 获取发布形式数据
	 * @param quarterId
	 * @return
	 */
	public ReleaseForm getReleaseFormData(String schoolProvince,String schoolCity,String schoolName,String gender){
		
		ReleaseForm form=null;
		String sql="select * from t_releaseform where schoolProvince='%s' and schoolCity='%s' and schoolName='%s' and gender='%s'";
		sql = String.format(sql,schoolProvince,schoolCity,schoolName,gender);
		System.out.println("从t_releaseform表中查询相关条件的微博发布形式:\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				System.out.println("在t_releaseform表中找到相关记录");
				form = new ReleaseForm(rst.getInt("picedCount"),rst.getInt("unpicedCount"),rst.getInt("geoedCount"),rst.getInt("ungeoedCount"),rst.getInt("emotionedCount"),rst.getInt("unemotionedCount"));
			}
			else{
				System.out.println("在t_releaseform表中没有找到相关记录，从t_status表中重新计算:");
				String queryCondition = processWhereQuery(schoolProvince,schoolCity,schoolName,gender);
				sql = "select count(*) as totalCount from t_status " + queryCondition;
				System.out.println(sql);
				ResultSet rstt = DBUtil.getRs(stmt, sql);
				if(rstt.next()){
					int totalCount = rstt.getInt("totalCount");
					int picedCount=0;
					int geoedCount=0;
					int emotionedCount=0;
					
					String picedSql;
					String geoedSql;
					String emotionedSql;
					if(queryCondition.trim().isEmpty()){
						picedSql = "select count(*) as count from t_status where pictureed=1 ";
						geoedSql = "select count(*) as count from t_status where geoed=1 ";
						emotionedSql = "select count(*) as count from t_status where emotioned=1";
					}else{
						picedSql =String.format("select count(*) as count from t_status '%s' and pictureed=1 ",queryCondition);
						geoedSql = String.format("select count(*) as count from t_status '%s' and geoed=1 ",queryCondition);
						emotionedSql =String.format("select count(*) as count from t_status '%s' and emotioned=1",queryCondition);
					}
					System.out.println(picedSql);
					ResultSet rstp= DBUtil.getRs(stmt, picedSql);
					if(rstp.next())
						picedCount = rstp.getInt("count");
					System.out.println(geoedSql);
					ResultSet rstg= DBUtil.getRs(stmt, geoedSql);
					if(rstg.next())
						geoedCount = rstg.getInt("count");
					System.out.println(emotionedSql);
					ResultSet rste= DBUtil.getRs(stmt, emotionedSql);
					if(rste.next())
						emotionedCount = rste.getInt("count");
					
					form = new ReleaseForm(picedCount,totalCount-picedCount,geoedCount,totalCount-geoedCount,emotionedCount,totalCount-emotionedCount);
					
					sql = "insert into t_releaseform(schoolProvince,schoolCity,schoolName,gender,picedCount,unpicedCount,geoedCount,ungeoedCount, emotionedCount, unemotionedCount) values('%s','%s','%s','%s',%d,%d,%d,%d,%d,%d)";
					sql = String.format(sql, schoolProvince,schoolCity,schoolName,gender,picedCount,totalCount-picedCount,geoedCount,totalCount-geoedCount,emotionedCount,totalCount-emotionedCount);
					System.out.println("计算完毕，将计算结果存储到t_releaseform表中:\n"+sql);
					DBUtil.executeSQL(stmt, sql);
					
					DBUtil.closeRs(rstt);
					DBUtil.closeRs(rstp);
					DBUtil.closeRs(rstg);
					DBUtil.closeRs(rste);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return form;
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

	/**
	 * 获取发布微博的手机品牌数据
	 * @param quarterId
	 * @return
	 */
	public List<PhoneBrand> getPhoneBrandData(String schoolProvince, String schoolCity, String schoolName, String gender){
		List<PhoneBrand> brandList = new ArrayList<PhoneBrand>();
		
		String sql="select brand, number from t_sphoneBrand where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by number desc limit 0,10";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);  
		System.out.println("从t_sphoneBrand中查询相关信息:\n" + sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				System.out.println("已经查到所需要的信息");
				rst.beforeFirst();
			    while(rst.next()){
			    	    System.out.println("\n" + rst.getString("brand"));
				        PhoneBrand brand = new PhoneBrand(rst.getString("brand"),rst.getInt("number"));
				        brandList.add(brand);
			     }
			}
			else{
				System.out.println("丛微博总表中查询相关信息\n");
				String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
				if(queryCondition.isEmpty()){
					queryCondition = "where phoneBrand != ''";
				}
				else{
					queryCondition = queryCondition + " AND phoneBrand != ''";
				}
				String phoneSql = "select phoneBrand, count(*) as number from t_status " + queryCondition + " group by phoneBrand order by number desc limit 0, 10";
				System.out.println(phoneSql);
				ResultSet rstp = DBUtil.getRs(stmt, phoneSql);
				String phoneBrand;
				int number;
				Statement stmtp = DBUtil.createStmt(conn);
				while(rstp.next()){
					phoneBrand = rstp.getString("phoneBrand");
					number = rstp.getInt("number");
					PhoneBrand brand = new PhoneBrand(phoneBrand, number);
			        brandList.add(brand);
			        sql = "insert into t_sphoneBrand values('%s', '%s', '%s', '%s', '%s', '%d')";
			        sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, phoneBrand, number);
			        System.out.println("插入一条记录:\n" + sql);
			        DBUtil.executeSQL(stmtp, sql);
			        System.out.println("插入完成");
				}
				System.out.println("到这儿了吗");
				DBUtil.closeRs(rstp);
				DBUtil.closeStmt(stmtp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return brandList;
	}
	
	/**
	 * 获取发布微博的软件来源数据
	 * @param quarterId
	 * @return
	 */
	public List<SourceName> getSourceNameData(String schoolProvince, String schoolCity, String schoolName, String gender){
		List<SourceName> sourceList = new ArrayList<SourceName>();
		String sql="select sourceName, number from t_ssourcename where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by number desc limit 0,10";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);  
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				rst.beforeFirst();
			    while(rst.next()){
			    	System.out.println("在发布形式表里面查询到");
				    SourceName source = new SourceName(rst.getString("sourceName"), rst.getInt("number"));
				    sourceList.add(source);
			    }
			}
			else{
				System.out.println("在微博总表里面查询");
				String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
				String sourceSql = "select sourceName, count(*) as number from t_status" + queryCondition + " group by sourceName order by number desc limit 0, 10";
				System.out.println(sourceSql);
				ResultSet rsts = DBUtil.getRs(stmt, sourceSql);
				Statement stmts = DBUtil.createStmt(conn);
				while(rsts.next()){
					SourceName source = new SourceName(rsts.getString("sourceName"), rsts.getInt("number"));
				    sourceList.add(source);
				    sql = "insert into t_ssourcename values('%s', '%s', '%s', '%s', '%s', %d)";
				    sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, rsts.getString("sourceName"), rsts.getInt("number"));
				    System.out.println("插入一条记录:" + sql);
				    DBUtil.executeSQL(stmts, sql);
				}
				DBUtil.closeRs(rsts);
				DBUtil.closeStmt(stmts);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return sourceList;
	}
	
	/**
	 * 获取发布微博的时间段数量 数据
	 * @param quarterId
	 * @return
	 */
	public List<TimePeriod> getTimePeriodData(String schoolProvince, String schoolCity, String schoolName, String gender){
		List<TimePeriod> timeList = new ArrayList<TimePeriod>();
		String sql = "select createdHour, number from t_stime where schoolProvince = '%s' and schoolCity = '%s' and schoolName = '%s' and gender = '%s' order by createdHour";
		sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender);
		System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				System.out.println("在时间表里面查询到");
				rst.beforeFirst();
				while(rst.next()){
					TimePeriod time = new TimePeriod(rst.getInt("createdHour"), rst.getInt("number"));
					timeList.add(time);
				}
			}
			else{
				System.out.println("在微博总表里面查询");
				String queryCondition = processWhereQuery(schoolProvince, schoolCity, schoolName, gender);
				String timeSql = "select createdHour, count(*) as number from t_status " + queryCondition + " group by createdHour order by createdHour";
				System.out.println(timeSql);
				ResultSet rstt = DBUtil.getRs(stmt, timeSql);
				Statement stmtt = DBUtil.createStmt(conn);
				while(rstt.next()){
					TimePeriod time = new TimePeriod(rstt.getInt("createdHour"), rstt.getInt("number"));
					timeList.add(time);
					sql = "insert into t_stime values('%s', '%s', '%s', '%s', %d, %d)";
					sql = String.format(sql, schoolProvince, schoolCity, schoolName, gender, rstt.getInt("createdHour"), rstt.getInt("number"));
					System.out.println("插入一条记录:" + sql);
					DBUtil.executeSQL(stmtt, sql);
				}
				DBUtil.closeStmt(stmtt);
				DBUtil.closeRs(rstt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return timeList;
	}
	
}
