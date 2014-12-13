package nlp.sina.model.behaviour;

public class SourceName {
	public String sourceName;
	public int number;
	
	public SourceName(String sourceName,int number){
		this.sourceName = sourceName;
		this.number = number;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
