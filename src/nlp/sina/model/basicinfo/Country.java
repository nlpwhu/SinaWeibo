package nlp.sina.model.basicinfo;

public class Country {
	public String country;
	public int number;
	
	public Country(String country,int number){
		this.country = country;
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
