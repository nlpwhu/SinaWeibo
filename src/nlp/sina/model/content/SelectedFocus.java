package nlp.sina.model.content;

import java.util.ArrayList;
import java.util.List;
import nlp.sina.model.Focus;

public class SelectedFocus {
	
	public  List<List<Integer>>  dataReList;	//话题走势图对应的数据列表
	public List<Focus> focusList;				//话题列表
	public List<String> timeArray;				//展示话题走势图对应的时间列表
	
	public SelectedFocus(List<Integer> idList,List<String> nameList){
		focusList = new ArrayList<Focus>();
		for(int i=0 ; i<idList.size() ;i++){
			Focus focus = new Focus();
			focus.id=idList.get(i);
			focus.name=nameList.get(i);
			focusList.add(focus);
		}
		dataReList =  new ArrayList<List<Integer>>();
		timeArray = new ArrayList<String>();
	}
	
	public SelectedFocus(List<Focus> focusList){
		this.focusList = focusList;
		dataReList =  new ArrayList<List<Integer>>();
		timeArray = new ArrayList<String>();
	}
	
	public boolean isEmpty(){
		if(dataReList.isEmpty() || focusList.isEmpty())
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

	public List<Focus> getFocusList() {
		return focusList;
	}

	public void setFocusList(List<Focus> focusList) {
		this.focusList = focusList;
	}

	
}
