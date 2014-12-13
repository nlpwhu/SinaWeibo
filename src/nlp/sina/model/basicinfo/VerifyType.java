package nlp.sina.model.basicinfo;

public class VerifyType {
	public String typeName;
	public int number;
	
	public VerifyType(String typeName,int number){
		this.typeName = typeName;
		this.number = number;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
