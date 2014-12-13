package nlp.sina.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nlp.sina.configure.Configure;
import nlp.sina.dao.EventDAO;
import nlp.sina.model.Event;
import nlp.sina.model.KeyWord;
import nlp.sina.model.Noumenon;
import nlp.sina.util.TimeUtil;

import com.opensymphony.xwork2.ActionSupport;

public class NoumenonAction extends ActionSupport{
	
	//public int quarterId=Configure.quarterId;
	
	public Noumenon positiveEnergy;		//正能量本体
	public Noumenon content;			//内容本体
	public Noumenon psychology;			//心理本体
	
	public int eventId;
	public List<KeyWord> keywordList;
	public String schoolProvince;
	public String schoolCity;
	public String schoolName;
	
	public String date_start;
	public String date_end;

	public String gender;
	
	/**
	 * 获取内容本体数据包括（事件列表、每个事件的每天讨论数量），准备绘制事件走势图，基本统计图
	 * @return
	 */
	public String FatchContentNoumenon(){
		
		long startTime = System.currentTimeMillis();
		EventDAO dao = new EventDAO();
		List<String> timeArray;
		if(date_start.isEmpty() || date_end.isEmpty()){
			timeArray = Configure.timeArray;
			date_start = Configure.StartTime;
			date_end = Configure.EndTime;
		}else{
			timeArray = TimeUtil.generateTimeArray(date_start, date_end);
		}
		
		List<Event> eventList = dao.getEventList_Content(schoolProvince, schoolCity, schoolName, gender, date_start, date_end);
		content = new Noumenon(eventList);
		content.timeArray = timeArray;
		for(Event event:content.eventList){
			Map<String,Integer> map = dao.getContentDaysCount(schoolProvince, schoolCity, schoolName, gender, event.id, date_start, date_end);
			
			List<Integer> list = new ArrayList<Integer>();
			for(int i=0 ; i < content.timeArray.size();i++){
				String date = content.timeArray.get(i);
				//System.out.println(date);
				if(map.containsKey(date)){
					list.add(map.get(date));
				}
				else{
					list.add(0);
				}
			}
			content.dataReList.add(list);
		}	
		long endTime = System.currentTimeMillis();
    	System.out.println("程序运行时间： "+ (endTime - startTime) + "ms");	
		return SUCCESS;
	}
	/**
	 * 获取心理本体数据包括（事件列表、每个事件的每天讨论数量），准备绘制事件走势图，基本统计图
	 * @return
	 */
	public String FatchPsychologyNoumenon(){
		
		EventDAO dao = new EventDAO();
		List<Event> eventList = dao.getEventList_Psychology(schoolProvince, schoolCity, schoolName, gender);
		psychology = new Noumenon(eventList);
		if(date_start.isEmpty() || date_end.isEmpty()){
			psychology.timeArray = Configure.timeArray;
			date_start = Configure.StartTime;
			date_end = Configure.EndTime;
		}else{
			psychology.timeArray = TimeUtil.generateTimeArray(date_start, date_end);
		}
		for(Event event:psychology.eventList){
			Map<String,Integer> map = dao.getPsychologyDaysCount(schoolProvince,schoolCity,schoolName, gender, event.id, date_start, date_end);
			
			List<Integer> list = new ArrayList<Integer>();
			for(int i=0 ; i < psychology.timeArray.size();i++){
				String date = psychology.timeArray.get(i);
				//System.out.println(date);
				if(map.containsKey(date)){
					list.add(map.get(date));
				}
				else{
					list.add(0);
				}
			}
			psychology.dataReList.add(list);
		}	
		return SUCCESS;
	}
	/**
	 * 获取正能量本体数据包括（事件列表、每个事件的每天讨论数量），准备绘制事件走势图，基本统计图
	 * @return
	 */
	public String FatchPositiveEnergyNoumenon(){
		
		EventDAO dao = new EventDAO();
		List<Event> eventList = dao.getEventList_PE(schoolProvince, schoolCity, schoolName, gender);
		
		positiveEnergy = new Noumenon(eventList);
		if(date_start.isEmpty() || date_end.isEmpty()){
			positiveEnergy.timeArray = Configure.timeArray;
			date_start = Configure.StartTime;
			date_end = Configure.EndTime;
		}else{
			positiveEnergy.timeArray = TimeUtil.generateTimeArray(date_start, date_end);
		}
		
		for(Event event:positiveEnergy.eventList){
			Map<String,Integer> map = dao.getPenergyDaysCount(schoolProvince,schoolCity,schoolName, gender, event.id,date_start, date_end);
			
			List<Integer> list = new ArrayList<Integer>();
			for(int i=0 ; i<positiveEnergy.timeArray.size();i++){
				String date = positiveEnergy.timeArray.get(i);
				//System.out.println(date);
				if(map.containsKey(date)){
					list.add(map.get(date));
				}
				else{
					list.add(0);
				}
			}
			positiveEnergy.dataReList.add(list);
		}
			
		return SUCCESS;
	}
	
	/**
	 * 获取正能量事件的关键词标签数据，准备绘制关键词云图
	 */
	public String FatchPenergyEventTagData(){
		EventDAO dao = new EventDAO();
		keywordList = dao.getPenergyKeywordListById(eventId);
		return SUCCESS;
	}
	
	public String FatchPsychologyEventTagData(){
		EventDAO dao = new EventDAO();
		keywordList = dao.getPsychologyKeywordListById(eventId);
		return SUCCESS;
	}
	
	public String FatchPrefocusEventTagData(){
		EventDAO dao = new EventDAO();
		keywordList = dao.getPrefocusKeywordListById(eventId);
		return SUCCESS;
	}
	
	
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public List<KeyWord> getKeywordList() {
		return keywordList;
	}

	public Noumenon getPositiveEnergy() {
		return positiveEnergy;
	}

	public Noumenon getContent() {
		return content;
	}

	public Noumenon getPsychology() {
		return psychology;
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
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	
}
