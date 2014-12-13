package nlp.sina.model;

import java.util.ArrayList;
import java.util.List;

public class Noumenon{
	public List<String> timeArray;			//展示本体事件的走势图，用到的时间列表
	public List<Event> eventList;			//本体事件列表
	public List<List<Integer>>  dataReList; //本体事件的时间对应的数据列表
	
	public Noumenon(List<Event> eventList){
		this.eventList = eventList;
		dataReList = new ArrayList<List<Integer>>();
		timeArray = new ArrayList<String>();
	}
	
	public boolean isEmpty(){
		if(eventList.isEmpty() || dataReList.isEmpty() || timeArray.isEmpty())
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

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public List<List<Integer>> getDataReList() {
		return dataReList;
	}

	public void setDataReList(List<List<Integer>> dataReList) {
		this.dataReList = dataReList;
	}
}
