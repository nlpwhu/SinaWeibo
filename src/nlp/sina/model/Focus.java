package nlp.sina.model;
public class Focus {
	
	public int id;								//话题的ID号
	public String name;							//话题的显示名称
	public int totalFreq;						//话题包含的文档数量
	public String date_start;					//话题的开始日期
	public String date_end;						//话题的结束日期
	
	public Focus(){
		
	}
	
	public Focus(int id,String name,int totalFreq,String date_start,String date_end){
		this.id = id;
		this.name = name;
		this.totalFreq = totalFreq;
		this.date_start = date_start;
		this.date_end = date_end;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getTotalFreq() {
		return totalFreq;
	}
	public String getDate_start() {
		return date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public static void main(String[] args){
		
	}
	
	
}
