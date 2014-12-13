package nlp.sina.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class Event implements java.io.Serializable {
	public int id;
	public String name;
	public int statusCount;
	//public int type;		//事件的类型，0为内容本体事件、1为正能量本体事件、2为心理本体事件
	
	public Event(String name) {
		this.name = name;
	}
	public Event(String name,int id) {
		this.id = id;
		this.name = name;
	}
	public Event(String name,int id,int statusCount) {
		this.id = id;
		this.name = name;
		this.statusCount = statusCount;
	}
	/*public Event(String name,int id,int statusCount,int type) {
		this.id = id;
		this.name = name;
		this.statusCount = statusCount;
		this.type = type;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}*/
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int getStatusCount() {
		return statusCount;
	}
	public void setStatusCount(int statusCount) {
		this.statusCount = statusCount;
	}
	
}
