package nlp.sina.model.basicinfo;

public class WebAge {
	public int weiboAge;
	public int number;
	
	public WebAge(int weiboAge,int number){
		this.weiboAge = weiboAge;
		this.number = number;
	}

	public int getWeiboAge() {
		return weiboAge;
	}

	public void setWeiboAge(int weiboAge) {
		this.weiboAge = weiboAge;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString() {
		return weiboAge +":"+number;
	}
}
