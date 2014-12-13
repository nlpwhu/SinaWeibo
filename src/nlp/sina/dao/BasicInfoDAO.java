package nlp.sina.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.model.basicinfo.Country;
import nlp.sina.model.basicinfo.GenderCount;
import nlp.sina.model.basicinfo.Province;
import nlp.sina.model.basicinfo.VerifyType;
import nlp.sina.model.basicinfo.WebAge;
import nlp.sina.util.DBUtil;

public class BasicInfoDAO {
	 
	/**
	 * 获取男女比例数据
	 * @param quarterId
	 * @return
	 */
	public GenderCount getGenderCount(String schoolProvince,String schoolCity,String schoolName){
		GenderCount genderCount=null;
		String sql="select * from t_gender_map where schoolProvince='%s' AND schoolCity='%s' AND schoolName='%s'";
		sql = String.format(sql, schoolProvince,schoolCity,schoolName);
		System.out.println("查询t_gender_map中的相关记录：\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			if(rst.next()){
				genderCount = new GenderCount(rst.getInt("maleCount"),rst.getInt("femaleCount"));
			}
			else{
				genderCount = new GenderCount();
				/*		从t_user表中重新查询性别比例		*/
				sql = "SELECT COUNT(gender) as genderCount FROM t_user " +
				      processWhereQuery(schoolProvince, schoolCity, schoolName) +
				      " GROUP BY gender";
				System.out.println("t_gender_map中没有相关记录，从t_user中重新查询：\n"+sql);
				ResultSet rst1 = DBUtil.getRs(stmt, sql);
				for(int i=0 ; i<2 ; i++){
					rst1.next();
					if(i==0)
						genderCount.setFemaleCount(rst1.getInt("genderCount"));
					else if(i==1){
						genderCount.setMaleCount(rst1.getInt("genderCount"));
					}
				}
				
				sql="insert into t_gender_map(schoolProvince,schoolCity,schoolName,maleCount,femaleCount) values('%s','%s','%s',%d,%d)";
				sql = String.format(sql, schoolProvince,schoolCity,schoolName,genderCount.maleCount,genderCount.femaleCount);
				System.out.println("将从t_user中查询到的结果存储到t_gender_map中\n"+sql);
				DBUtil.executeSQL(stmt, sql);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return genderCount;
	}
	
	public static String processWhereQuery(String schoolProvince,String schoolCity,String schoolName){
		String query = new String();
		if (schoolProvince.isEmpty() && schoolCity.isEmpty() && schoolName.isEmpty())
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
			return " where " + query;
		}
	}
	/**
	 * 获取网龄分布数据
	 * @param quarterId
	 * @return
	 */
	public List<WebAge> getWebAgeData(String schoolProvince,String schoolCity,String schoolName){
		List<WebAge> webAgeList = new ArrayList<WebAge>();
		String sql="select weiboAge, number from t_weibo_age_map where schoolProvince='%s' AND schoolCity='%s' AND schoolName='%s'";
		sql = String.format(sql, schoolProvince,schoolCity,schoolName);
		System.out.println("查询t_weibo_age_map中的相关记录：\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				WebAge webAge = new WebAge(rst.getInt("weiboAge"),rst.getInt("number"));
				webAgeList.add(webAge);
			}
			if (webAgeList.isEmpty()) {
				sql = "SELECT weiboAge, COUNT(weiboAge) AS number FROM t_user " +
					   processWhereQuery(schoolProvince, schoolCity, schoolName) +
					  " GROUP BY weiboAge";
				System.out.println("t_weibo_age_map中没有相关记录，从t_user中重新查询：\n"+sql);
				ResultSet rst1 = DBUtil.getRs(stmt, sql);
				while(rst1.next()){
					WebAge webAge = new WebAge(rst1.getInt("weiboAge"),rst1.getInt("number"));
					webAgeList.add(webAge);
				}
				System.out.println("将从t_user中查询到的结果存储到t_gender_map中\n");
				for(WebAge wa : webAgeList) {
					sql="insert into t_weibo_age_map(schoolProvince,schoolCity,schoolName,weiboAge,number) values('%s','%s','%s',%d,%d)";
					sql = String.format(sql, schoolProvince,schoolCity,schoolName,wa.weiboAge,wa.number);
					DBUtil.executeSQL(stmt, sql);
					System.out.println("存储\n"+sql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return webAgeList;
	}
	
	/**
	 * 获取用户类型数据
	 * @param quarterId
	 * @return
	 */
	public List<VerifyType> getUserVerifyTypeData(String schoolProvince,String schoolCity,String schoolName){
		List<VerifyType> typeList = new ArrayList<VerifyType>();
		
		String sql="select verifiedType, number from t_verified_type_map where schoolProvince='%s' AND schoolCity='%s' AND schoolName='%s'";
		sql = String.format(sql, schoolProvince,schoolCity,schoolName);
		System.out.println("查询t_verified_type_map中的相关记录：\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				String typeName = verifiedTypeMap.get(rst.getInt("verifiedType"));
				VerifyType verifiedType = new VerifyType(typeName,rst.getInt("number"));
				typeList.add(verifiedType);
			}
			if (typeList.isEmpty()) {
				sql = "SELECT verifiedType, COUNT(verifiedType) AS number FROM t_user " +
					   processWhereQuery(schoolProvince, schoolCity, schoolName) +
					  " GROUP BY verifiedType";
				System.out.println("t_verified_type_map中没有相关记录，从t_user中重新查询：\n"+sql);
				ResultSet rst1 = DBUtil.getRs(stmt, sql);
				while(rst1.next()){
					String typeName = verifiedTypeMap.get(rst1.getInt("verifiedType"));
					VerifyType verifiedType = new VerifyType(typeName,rst1.getInt("number"));
					typeList.add(verifiedType);
				}
				System.out.println("将从t_user中查询到的结果存储到t_verified_type_map中\n");
				for(VerifyType vt : typeList) {
					sql="insert into t_verified_type_map(schoolProvince,schoolCity,schoolName,verifiedType,number) values('%s','%s','%s',%d,%d)";
					sql = String.format(sql, schoolProvince,schoolCity,schoolName,vt.typeName,vt.number);
					DBUtil.executeSQL(stmt, sql);
					System.out.println("存储\n"+sql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return typeList;
	}
	
	/**
	 * 获取用户国内省份分布数据
	 * @param quarterId
	 * @return
	 */
	public List<Province> getUserProvinceData(String schoolProvince,String schoolCity,String schoolName){
		List<Province> provinceList = new ArrayList<Province>();
		String sql="select provinceName, number from t_province_map where schoolProvince='%s' AND schoolCity='%s' AND schoolName='%s'";
		sql = String.format(sql, schoolProvince,schoolCity,schoolName);
		System.out.println("查询t_province_map中的相关记录：\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				Province province = new Province(rst.getString("provinceName"),rst.getInt("number"));
				provinceList.add(province);
			}
			if (provinceList.isEmpty()) {
				sql = "SELECT provinceName, COUNT(provinceName) AS number FROM t_user " +
					   processWhereQuery(schoolProvince, schoolCity, schoolName) +
					  " GROUP BY provinceName";
				System.out.println("t_province_map中没有相关记录，从t_user中重新查询：\n"+sql);
				ResultSet rst1 = DBUtil.getRs(stmt, sql);
				while(rst1.next()){
					Province province = new Province(rst1.getString("provinceName"),rst1.getInt("number"));
					if (province.provinceName.equals("null"))
						continue;
					provinceList.add(province);
				}
				System.out.println("将从t_user中查询到的结果存储到t_province_map中\n");
				for(Province p : provinceList) {
					sql="insert into t_province_map(schoolProvince,schoolCity,schoolName,provinceName,number) values('%s','%s','%s','%s',%d)";
					sql = String.format(sql, schoolProvince,schoolCity,schoolName,p.provinceName,p.number);
					DBUtil.executeSQL(stmt, sql);
					System.out.println("存储\n"+sql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return provinceList;
	}
	
	/**
	 * 获取用户世界国家分布数据
	 * @param quarterId
	 * @return
	 */
	public List<Country> getUserCountryData(String schoolProvince,String schoolCity,String schoolName){
		List<Country> countryList = new ArrayList<Country>();
		String sql="select countryName, number from t_country_map where schoolProvince='%s' AND schoolCity='%s' AND schoolName='%s'";
		sql = String.format(sql, schoolProvince,schoolCity,schoolName);
		System.out.println("查询t_country_map中的相关记录：\n"+sql);
		Connection conn = DBUtil.getConn();
		Statement stmt = DBUtil.createStmt(conn);
		ResultSet rst = DBUtil.getRs(stmt, sql);
		try {
			while(rst.next()){
				Country country = new Country(rst.getString("countryName"),rst.getInt("number"));
				countryList.add(country);
			}
			if (countryList.isEmpty()) {
				sql = "SELECT countryName, COUNT(countryName) AS number FROM t_user " +
					   processWhereQuery(schoolProvince, schoolCity, schoolName) +
					  " GROUP BY countryName";
				System.out.println("t_country_map中没有相关记录，从t_user中重新查询：\n"+sql);
				ResultSet rst1 = DBUtil.getRs(stmt, sql);
				while(rst1.next()){
					Country country = new Country(rst1.getString("countryName"),rst1.getInt("number"));
					if (country.country.equals("null"))
						continue;
					countryList.add(country);
				}
				System.out.println("将从t_user中查询到的结果存储到t_country_map中\n");
				for(Country c : countryList) {
					sql="insert into t_country_map(schoolProvince,schoolCity,schoolName,countryName,number) values('%s','%s','%s','%s',%d)";
					sql = String.format(sql, schoolProvince,schoolCity,schoolName,c.country,c.number);
					DBUtil.executeSQL(stmt, sql);
					System.out.println("存储\n"+sql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rst);
			DBUtil.closeStmt(stmt);
			DBUtil.closeConn(conn);
		}
		return countryList;
	}
	
	public static Map<Integer,String> verifiedTypeMap = new HashMap<Integer,String>();
	static{
		verifiedTypeMap.put(-1, "普通用户");
		verifiedTypeMap.put(0,   "黄v用户");
		verifiedTypeMap.put(1,   "政府");
		verifiedTypeMap.put(2,   "企业");
		verifiedTypeMap.put(3,   "媒体");
		verifiedTypeMap.put(4,   "校园");
		verifiedTypeMap.put(5,   "网站");
		verifiedTypeMap.put(6,   "应用");
		verifiedTypeMap.put(7,   "团体（机构）");
		verifiedTypeMap.put(8,   "待审企业");
		verifiedTypeMap.put(10,  "微女郎");
		verifiedTypeMap.put(200, "初级达人");
		verifiedTypeMap.put(220, "中高级达人");
	}
	public static void main(String[] args){
		BasicInfoDAO dao = new BasicInfoDAO();
		List<Province> webAgeList = dao.getUserProvinceData("湖北", "武汉", "");
		for(Province wa : webAgeList) {
			System.out.println(wa);
		}
	}
}


