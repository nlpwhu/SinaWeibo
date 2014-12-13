package nlp.sina.action;

import java.util.List;

import nlp.sina.configure.Configure;
import nlp.sina.dao.BehaviourDAO;
import nlp.sina.model.behaviour.PhoneBrand;
import nlp.sina.model.behaviour.ReleaseForm;
import nlp.sina.model.behaviour.SourceName;
import nlp.sina.model.behaviour.TimePeriod;

import com.opensymphony.xwork2.ActionSupport;

public class BehaviourAction extends ActionSupport{
	public ReleaseForm form;
	public List<PhoneBrand> brandList;
	public List<SourceName> sourceList;
	public List<TimePeriod> timeList;
	
	public String schoolProvince;
	public String schoolCity;
	public String schoolName;
	public String gender;
	//public String createdDate;
	
	
	/**
	 * 微博发布形式
	 * @return
	 */
	public String WeiboReleaseForm(){
		BehaviourDAO dao = new BehaviourDAO();
		form = dao.getReleaseFormData(schoolProvince, schoolCity, schoolName, gender);
		return SUCCESS;
	}
	/**
	 * 发布微博的手机品牌分布
	 * @return
	 */
	public String WeiboPhoneBrandMap(){
		BehaviourDAO dao = new BehaviourDAO();
		brandList = dao.getPhoneBrandData(schoolProvince, schoolCity, schoolName, gender);
		return SUCCESS;
	}
	/**
	 * 发布微博的软件来源分布
	 * @return
	 */
	public String WeiboSourceNameMap(){
		BehaviourDAO dao = new BehaviourDAO();
		sourceList = dao.getSourceNameData(schoolProvince, schoolCity, schoolName, gender);
		return SUCCESS;
	}
	
	/**
	 * 发布微博的时间段数量分布
	 * @return
	 */
	public String WeiboTimePeriodMap(){
		BehaviourDAO dao = new BehaviourDAO();
		timeList = dao.getTimePeriodData(schoolProvince, schoolCity, schoolName, gender);
		return SUCCESS;
	}
	
	
	
	public ReleaseForm getForm() {
		return form;
	}
	public List<PhoneBrand> getBrandList() {
		return brandList;
	}

	public List<SourceName> getSourceList() {
		return sourceList;
	}

	public List<TimePeriod> getTimeList() {
		return timeList;
	}
	public void setSchoolProvince(String schoolProvince) {
		this.schoolProvince = schoolProvince;
	}
	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
