package nlp.sina.action;

import java.util.List;

import nlp.sina.configure.Configure;
import nlp.sina.dao.BasicInfoDAO;
import nlp.sina.model.basicinfo.Country;
import nlp.sina.model.basicinfo.GenderCount;
import nlp.sina.model.basicinfo.Province;
import nlp.sina.model.basicinfo.VerifyType;
import nlp.sina.model.basicinfo.WebAge;

import com.opensymphony.xwork2.ActionSupport;

public class BasicInfoAction extends ActionSupport{
	public GenderCount genderCount;
	public List<WebAge> webAgeList;
	public List<VerifyType> typeList;
	public List<Province> provinceList;
	public List<Country> countryList;
	
	public String schoolProvince;
	public String schoolCity;
	public String schoolName;
	public String gender;
	
	public String getSchoolProvince() {
		return schoolProvince;
	}
	public void setSchoolProvince(String schoolProvince) {
		this.schoolProvince = schoolProvince;
	}
	public String getSchoolCity() {
		return schoolCity;
	}
	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}
	/**
	 * 男女比例
	 * @return
	 */
	public String UserGenderRate(){
		BasicInfoDAO dao = new BasicInfoDAO();
		genderCount = dao.getGenderCount(schoolProvince,schoolCity,schoolName);
		
		return SUCCESS;
	}
	/**
	 * 网龄分布
	 */
	public String UserWebAgeMap(){
		BasicInfoDAO dao = new BasicInfoDAO();
		webAgeList = dao.getWebAgeData(schoolProvince,schoolCity,schoolName);
		return SUCCESS;
	}
	/**
	 * 用户认证类型
	 * @return
	 */
	public String UserVerifyTypeMap(){
		BasicInfoDAO dao = new BasicInfoDAO();
		typeList = dao.getUserVerifyTypeData(schoolProvince,schoolCity,schoolName);
		return SUCCESS;
	}
	
	/**
	 * 用户国内省份分布
	 * @return
	 */
	public String UserProvinceMap(){
		BasicInfoDAO dao = new BasicInfoDAO();
		provinceList = dao.getUserProvinceData(schoolProvince,schoolCity,schoolName);
		return SUCCESS;
	}
	/**
	 * 用户世界国家分布
	 * @return
	 */
	public String UserCountryMap(){
		BasicInfoDAO dao = new BasicInfoDAO();
		countryList = dao.getUserCountryData(schoolProvince,schoolCity,schoolName);
		return SUCCESS;
	}
	
	
	
	
	public List<WebAge> getWebAgeList() {
		return webAgeList;
	}
	public List<VerifyType> getTypeList() {
		return typeList;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public List<Country> getCountryList() {
		return countryList;
	}
	public GenderCount getGenderCount() {
		return genderCount;
	}
	public void setGenderCount(GenderCount genderCount) {
		this.genderCount = genderCount;
	}

	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setWebAgeList(List<WebAge> webAgeList) {
		this.webAgeList = webAgeList;
	}
	public void setTypeList(List<VerifyType> typeList) {
		this.typeList = typeList;
	}
	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}
	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}
	
	
}
