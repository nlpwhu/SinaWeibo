package nlp.sina.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.sina.configure.Configure;
import nlp.sina.configure.Content_Configure;
import nlp.sina.dao.ContentDAO;
import nlp.sina.dao.FocusDAO;
import nlp.sina.dao.TopicDAO;
import nlp.sina.model.Focus;
import nlp.sina.model.KeyWord;
import nlp.sina.model.Topic;
import nlp.sina.model.content.BasicInfo;
import nlp.sina.model.content.SelectedFocus;
import nlp.sina.model.content.SelectedTopic;
import nlp.sina.util.TimeUtil;

import com.opensymphony.xwork2.ActionSupport;

public class ContentAction extends ActionSupport{
	
	public int quarterId=Configure.quarterId;
	
	/*		基本信息相关的变量		*/
	public BasicInfo binfo;
	
	
	/*		抽取的话题相关变量		*/
	public int focusId;
	public List<Focus> focusList;
	public SelectedFocus sfocus;
	public List<KeyWord> keywordList;
	
	public String idListStr;
	
	/*		微话题相关的变量		*/
	public List<Topic> topicList;
	public SelectedTopic stopic;
	
	public String schoolProvince;
	public String schoolCity;
	public String schoolName;
	public String gender;
	public String date_start;
	public String date_end;
	/*
	 * 从数据库中读取内容分析模块的基本信息，如微博的转发率、原创率等
	 */
	public String BasicInfo(){
		ContentDAO dao = new ContentDAO();
		if(date_start.isEmpty() || date_end.isEmpty()){
			
			date_start = Configure.StartTime;
			date_end = Configure.EndTime;
		}
		binfo = dao.getBasicInfo(schoolProvince, schoolCity, schoolName, gender, date_start, date_end);
		return SUCCESS;
	}
	
	/*
	 * 获取相关话题的关键词列表，绘制关键词云图
	 */
	public String GenerateFocusTagCloud(){
		FocusDAO dao = new FocusDAO();
		keywordList = dao.getKeywordListById(quarterId,focusId);
		return SUCCESS;
	}
	
	/*
	 * 获取所有话题的关键词列表，绘制关键词云图
	 */
	public String GenerateFocusTagCloud_All(){
		FocusDAO dao = new FocusDAO();
		List<Focus> focusList = dao.getFinallyFocusList(quarterId,0, Content_Configure.ExtractedFocusShowLimit);
		Map<String,KeyWord> map = new HashMap<String,KeyWord>();
		for(Focus focus:focusList){
			List<KeyWord> list =  dao.getKeywordListById(quarterId,focus.id);
			for(KeyWord k:list){
				if(map.containsKey(k.name)){
					KeyWord k2 = map.get(k.name);
					k2.freq+=k.freq;
					map.put(k.name, k2);
				}else
					map.put(k.name, k);
			}
		}
		keywordList = new ArrayList<KeyWord>(map.values());
		return SUCCESS;
	}
	
	/*
	 * 获取抽取到的相关话题列表
	 */
	public String FatchExtractedFocusList(){
		FocusDAO dao = new FocusDAO();
		focusList = dao.getFinallyFocusList(quarterId,0, Content_Configure.ExtractedFocusShowLimit);
		return SUCCESS;
	}
	
	/*
	 * 获取选定话题的数据列表，用于绘制话题时间走势图
	 */
	public String SelectedFocusTrend(){
		FocusDAO dao = new FocusDAO();
		
		List<Integer> idList = new ArrayList<Integer>();
		String[] t = idListStr.split(",");
		for(String idStr:t){
			if(!idStr.trim().isEmpty()){
				idList.add(Integer.parseInt(idStr));
			}
		}
		System.out.println(idList);
		
		if(idList==null || idList.isEmpty()){
			sfocus = new SelectedFocus(Content_Configure.SelectedFocusIdList,Content_Configure.SelectedFocusNameList);
		}else{
			List<Focus> focusList = dao.getSelectedTopicList(quarterId,idList);
			sfocus = new SelectedFocus(focusList);
		}
		for(Focus focus:sfocus.focusList){
			List<Integer> list = dao.getFocusDaysCount(quarterId,focus.id);
			sfocus.dataReList.add(list);
		}
		sfocus.timeArray=Configure.timeArray;
		return SUCCESS;
	}
	
	/*
	 * 获取选定微话题的数据列表，用于绘制话题时间走势图
	 */
	public String 

	(){
		TopicDAO dao = new TopicDAO();
		List<Integer> idList = new ArrayList<Integer>();
		String[] t = idListStr.split(",");
		for(String idStr:t){
			if(!idStr.trim().isEmpty()){
				idList.add(Integer.parseInt(idStr));
			}
		}
		System.out.println(idList);
		if(idList==null || idList.isEmpty()){
			List<Topic> topicList = dao.getTopicList(5);
			stopic = new SelectedTopic(topicList);
		}else{
			List<Topic> topicList = dao.getSelectedTopicList(idList);
			stopic = new SelectedTopic(topicList);
		}
		if(date_start == null || date_end == null || date_start.isEmpty() || date_end.isEmpty()){
			stopic.timeArray = Configure.timeArray;	
			date_start = Configure.StartTime;
			date_end = Configure.EndTime;
		}
		
		else{
			stopic.timeArray = TimeUtil.generateTimeArray(date_start, date_end);
		}
		for(Topic topic:stopic.topicList){
			Map<String, Integer> map = dao.getTopicDaysCount(schoolProvince, schoolCity, schoolName, topic.id, date_start, date_end, gender);
			List<Integer> list = new ArrayList<Integer>();
			System.out.println(map.entrySet());

			
			System.out.println(map.containsKey("2014-01-01"));
			System.out.println(map.containsKey(stopic.timeArray.get(0)));
			System.out.println(stopic.timeArray.get(0));
			for(int i=0 ; i<stopic.timeArray.size();i++){
				String date = stopic.timeArray.get(i);
				//String FormatStrDate = TimeUtil.reformatToFormatStr(date);
				if(map.containsKey(date)){
					list.add(map.get(date));
				}
				else{
					list.add(0);
				}
//				System.out.println(list);
			}
			stopic.dataReList.add(list);
		}
		
		return SUCCESS;
	}
	
	/*
	 * 获取微话题列表数据
	 */
	public String FatchTopicList(){
		TopicDAO dao = new TopicDAO();
		topicList = dao.getTopicList(200);
		return SUCCESS;
	}
	
	
	
	public String getIdListStr() {
		return idListStr;
	}
	public SelectedTopic getStopic() {
		return stopic;
	}
	public List<Topic> getTopicList() {
		return topicList;
	}
	public BasicInfo getBinfo() {
		return binfo;
	}
	public SelectedFocus getSfocus() {
		return sfocus;
	}
	
	public int getFocusId() {
		return focusId;
	}
	public void setFocusId(int focusId) {
		this.focusId = focusId;
	}
	public List<Focus> getFocusList() {
		return focusList;
	}
	public List<KeyWord> getKeywordList() {
		return keywordList;
	}
	
	
}
