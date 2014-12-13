package nlp.sina.model.content;

import java.util.ArrayList;
import java.util.List;
import nlp.sina.model.Topic;

public class SelectedTopic{
	
	public  List<List<Integer>>  dataReList;	//微话题走势图对应的数据列表
	public  List<String> timeArray;				//展示微话题走势图对应的时间列表
	public List<Topic> topicList;				//微话题列表
	
	public SelectedTopic(List<Integer> idList,List<String> nameList){
		topicList = new ArrayList<Topic>();
		for(int i=0 ; i<idList.size() ;i++){
			Topic topic = new Topic();
			topic.setId(idList.get(i));
			topic.setName(nameList.get(i));
			topicList.add(topic);
		}
		dataReList =  new ArrayList<List<Integer>>();
		timeArray = new ArrayList<String>();
	}
	
	public SelectedTopic(List<Topic> topicList){
		this.topicList = topicList;
		dataReList =  new ArrayList<List<Integer>>();
		timeArray = new ArrayList<String>();
	}
	
	public boolean isEmpty(){
		if(dataReList.isEmpty() || topicList.isEmpty())
			return true;
		else
			return false;
		
	}
	
	public List<String> getTimeArray() {
		return timeArray;
	}

	public void setTimeArray(List<String> timeArray) {
		this.timeArray = timeArray;
	}

	public List<List<Integer>> getDataReList() {
		return dataReList;
	}

	public void setDataReList(List<List<Integer>> dataReList) {
		this.dataReList = dataReList;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

	

	
	
	
	
	
}
