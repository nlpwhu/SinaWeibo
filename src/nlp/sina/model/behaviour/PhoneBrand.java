package nlp.sina.model.behaviour;

public class PhoneBrand {
	public String brand;
	public int number;
	
	public PhoneBrand(String brand,int number){
		this.brand = brand;
		this.number = number;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
