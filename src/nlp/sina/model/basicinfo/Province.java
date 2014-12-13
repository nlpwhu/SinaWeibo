package nlp.sina.model.basicinfo;

public class Province {
	public String provinceName;
	public int number;
	
	public Province(String provinceName,int number){
		this.provinceName = provinceName;
		this.number = number;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
