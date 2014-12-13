package nlp.sina.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 微话题类
 *
 */
public class Topic {
	public int id;
	public String name;
	public int totalFreq;
	public String date_start;					//微话题的开始日期
	public String date_end;						//微话题的结束日期
	public Topic(){
		
	}
	public Topic(int id,String name,int totalFreq,String date_start,String date_end ){
		this.id = id;
		this.name = name;
		this.totalFreq = totalFreq;
		this.date_start = date_start;
		this.date_end = date_end;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalFreq() {
		return totalFreq;
	}
	public void setTotalFreq(int totalFreq) {
		this.totalFreq = totalFreq;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	
	
}
